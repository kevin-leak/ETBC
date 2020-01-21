package club.crabglory.www.data.netkit.push.clink.core.stealing;

import java.io.IOException;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import club.crabglory.www.data.netkit.push.clink.core.IoProvider;
import club.crabglory.www.data.netkit.push.clink.core.IoTask;
import club.crabglory.www.data.netkit.push.clink.utils.CloseUtils;

/**
 * @author KevinLeak
 * @blog https://www.crabglory.club
 * @github https://github.com/kevin-leak
 * @time 2020-01-16 21:15
 * 可窃取任务的线程
 */
public abstract class StealingSelectorThread extends Thread {
    // 允许的操作
    private static final int VALID_OPS = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
    private final Selector selector;
    // 是否还处在运行中
    private volatile boolean isRunning = true;
    // 已经就绪队列，对外扫描的队列
    private LinkedBlockingQueue<IoTask> readyTaskQueue = new LinkedBlockingQueue<>();
    // 待注册的队列
    private LinkedBlockingQueue<IoTask> registerTaskQueue = new LinkedBlockingQueue<>();
    // 单次就绪的队列，随后一次性加入就绪队列中
    private final List<IoTask> onceReadyTaskCache = new ArrayList<>(200);
    /**
     * 任务饱和度度量
     */
    private AtomicLong saturatingCapacity = new AtomicLong();
    private StealingService stealingService;

    public LinkedBlockingQueue<IoTask> getReadyTaskQueue() {
        return readyTaskQueue;
    }

    protected StealingSelectorThread(Selector selector) {
        this.selector = selector;
    }

    public boolean register(SocketChannel channel, IoProvider.HandleProviderCallback providerCallback, int ops) {
        if (channel.isOpen()) {
            IoTask task = new IoTask(channel, providerCallback, ops);
            registerTaskQueue.offer(task);
            return true;
        }
        return false;
    }

    public boolean register(IoTask task) {
        if (task.channel.isOpen()) {
            registerTaskQueue.offer(task);
            return true;
        }
        return false;
    }

    /**
     * 取消注册，原理类似于注册操作在队列中宏添加一份取消注册的任务
     */
    public void unRegister(SocketChannel channel) {
        SelectionKey selectionKey = channel.keyFor(selector);
        if (selectionKey != null && selectionKey.attachment() != null) {
            // 关闭可使用的Attach简单判断是否处于队列中
            selectionKey.attach(null);
            // 添加取消任务，见consumeRegisterTodoTasks
            IoTask task = new IoTask(channel, null, 0);
            registerTaskQueue.offer(task);
        }
    }

    /**
     * 消费当前待注册的通道任务
     *
     * @param registerTaskQueue 待注册的通道
     */
    private void consumeRegisterTodoTasks(final LinkedBlockingQueue<IoTask> registerTaskQueue) {
        final Selector selector = this.selector;
        IoTask registerTask = registerTaskQueue.poll();
        while (null != registerTask) {
            try {
                SocketChannel channel = registerTask.channel;
                int ops = registerTask.ops;
                if (ops == 0) {
                    // unRegister中构建的取消帧，在消费的时候进行检测，取消消费
                    SelectionKey selectionKey = channel.keyFor(selector);
                    if (selectionKey != null) selectionKey.cancel();
                } else if ((ops & ~VALID_OPS) == 0) {
                    SelectionKey selectionKey = channel.keyFor(selector);
                    if (selectionKey == null) {
                        selectionKey = channel.register(selector, ops, new KeyAttachment());
                    } else {
                        selectionKey.interestOps(selectionKey.interestOps() | ops);
                    }
                    Object attachment = selectionKey.attachment();
                    if (attachment instanceof KeyAttachment) {
                        ((KeyAttachment) attachment).attach(ops, registerTask);
                    } else {
                        selectionKey.cancel();
                    }
                }
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            } finally {
                registerTask = registerTaskQueue.poll();
            }
        }
    }

    /**
     * 将单次就绪的任务缓存加入到总队列中
     * 可以在这做一些算法的调度
     * 将任务进行排序加入
     *
     * @param readyTaskQueue     总任务队列
     * @param onceReadyTaskCache 单次执行的任务
     */
    private void joinTaskQueue(final LinkedBlockingQueue<IoTask> readyTaskQueue, final List<IoTask> onceReadyTaskCache) {
        readyTaskQueue.addAll(onceReadyTaskCache);
    }

    @Override

    public void run() {
        super.run();
        // 减少扫描成员变量，所以都设置为局部变量
        final Selector selector = this.selector;
        final LinkedBlockingQueue<IoTask> readyTaskQueue = this.readyTaskQueue;
        // 待注册的队列
        final LinkedBlockingQueue<IoTask> registerTaskQueue = this.registerTaskQueue;
        final List<IoTask> onceReadyTaskCache = this.onceReadyTaskCache;
        try {
            while (isRunning) {
                // 加入到待注册的通道
                consumeRegisterTodoTasks(registerTaskQueue);
                // 检查一次
                if ((selector.selectNow()) == 0) {
                    Thread.yield();
                    continue;
                }
                // 已经就绪的通道
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                // 迭代更新已经就绪的任务
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    Object attachmentOJ = selectionKey.attachment();
                    if (selectionKey.isValid() && attachmentOJ instanceof KeyAttachment) {
                        KeyAttachment attachment = (KeyAttachment) attachmentOJ;
                        try {
                            final int readyOps = selectionKey.readyOps();
                            int interestOps = selectionKey.interestOps();

                            // 是否可读
                            if ((readyOps & SelectionKey.OP_READ) != 0) {
                                onceReadyTaskCache.add(attachment.taskForReadable);
                                interestOps = interestOps & ~SelectionKey.OP_READ;
                            }

                            // 是否可写
                            if ((readyOps & SelectionKey.OP_WRITE) != 0) {
                                onceReadyTaskCache.add(attachment.taskForWritable);
                                interestOps = interestOps & ~SelectionKey.OP_WRITE;
                            }
                            // 取消已经就绪的关注
                            selectionKey.interestOps(interestOps);
                        } catch (CancelledKeyException e) {
                            onceReadyTaskCache.remove(attachment.taskForReadable);
                            onceReadyTaskCache.remove(attachment.taskForWritable);
                        }
                    }
                    iterator.remove();
                }
                // 判断本次是否待执行的任务
                if (!onceReadyTaskCache.isEmpty()) {
                    // 加入到总队列中去
                    joinTaskQueue(readyTaskQueue, onceReadyTaskCache);
                    onceReadyTaskCache.clear();
                }

                // 消费队列任务
                consumeTodoTasks(readyTaskQueue, registerTaskQueue);

            }
        } catch (ClosedSelectorException e) {
            CloseUtils.close(selector);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            readyTaskQueue.clear();
            registerTaskQueue.clear();
            onceReadyTaskCache.clear();
        }
    }

    protected void consumeTodoTasks(LinkedBlockingQueue<IoTask> readyTaskQueue, LinkedBlockingQueue<IoTask> registerTaskQueue) {
        final AtomicLong saturatingCapacity = this.saturatingCapacity;
        // 循环把所有任务都做完
        IoTask doTask = readyTaskQueue.poll();
        while (doTask != null) {
            // 增加饱和度
            saturatingCapacity.incrementAndGet();
            // 做任务
            if (processTask(doTask)) {
                // 做完任务后添加到注册列表中
                registerTaskQueue.offer(doTask);
            }
            doTask = readyTaskQueue.poll();
        }

        // 窃取任务
        final StealingService stealingService = this.stealingService;
        if (stealingService != null) {
            doTask = stealingService.steal(readyTaskQueue);
            while (doTask != null) {
                saturatingCapacity.incrementAndGet();
                if (processTask(doTask)) {
                    registerTaskQueue.offer(doTask);
                }
                doTask = stealingService.steal(readyTaskQueue);
            }
        }
    }


    public void exit() {
        isRunning = false;
        CloseUtils.close(selector);
        interrupt();
    }

    protected abstract boolean processTask(IoTask ioTask);

    /**
     * 获取饱和程度
     * 暂时的饱和度量是使用任务执行的次数来定
     *
     * @return -1 已经失效，饱和度
     */
    public long getSaturatingCapacity() {
        if (selector.isOpen()) {
            return saturatingCapacity.get();
        } else {
            return -1;
        }
    }

    public void setStealingService(StealingService stealingService) {
        this.stealingService = stealingService;
    }

    /**
     * 用于注册添加的附件]
     */
    static class KeyAttachment {

        IoTask taskForWritable;
        IoTask taskForReadable;

        /**
         * @param ops  任务关注的事件类型
         * @param task
         */
        void attach(int ops, IoTask task) {
            if (ops == SelectionKey.OP_READ) {
                taskForReadable = task;
            } else {
                taskForWritable = task;
            }
        }
    }
}
