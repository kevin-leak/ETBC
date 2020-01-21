package club.crabglory.www.data.netkit.push.clink.core.stealing;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.IntFunction;

import club.crabglory.www.data.netkit.push.clink.core.IoTask;

/**
 * @author KevinLeak
 * @blog https://www.crabglory.club
 * @github https://github.com/kevin-leak
 * @time 2020-01-17 11:17
 * <p>
 * A,B两个线程，如果说B特别繁忙，而A很闲，A就去偷一些任务来执行
 */
public class StealingService {
    /**
     * 当任务数量低于安全值的时，不可窃取
     */
    private final int minSafetyThreshold;
    private volatile boolean isTerminated = false;
    /**
     * 整个用来执行读写操作的线程数组
     */
    private final StealingSelectorThread[] threads;
    private final LinkedBlockingQueue<IoTask>[] queues;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public StealingService(int minSafetyThreshold, StealingSelectorThread[] threads) {
        this.minSafetyThreshold = minSafetyThreshold;
        this.threads = threads;
        this.queues = Arrays.stream(threads).map(StealingSelectorThread::getReadyTaskQueue)
                .toArray((IntFunction<LinkedBlockingQueue<IoTask>[]>) LinkedBlockingQueue[]::new);
    }

    /**
     * 窃取别人的任务
     * @param excludeQueue 自己的队列
     * @return
     */
    IoTask steal(final LinkedBlockingQueue<IoTask> excludeQueue) {
        final int minSafetyThreshold = this.minSafetyThreshold;
        final LinkedBlockingQueue<IoTask>[] queues = this.queues;
        for (LinkedBlockingQueue<IoTask> queue : queues) {
            if (queue == excludeQueue) continue;
            int size = queue.size();
            if (size > minSafetyThreshold) {
                IoTask poll = queue.poll();
                if (poll != null) return poll;
            }
        }
        return null;
    }

    public StealingSelectorThread getNotBusyThread() {
        StealingSelectorThread targetThread = null;
        long targetKeyCount = Long.MAX_VALUE;
        for (StealingSelectorThread thread : threads) {
            long saturatingCapacity = thread.getSaturatingCapacity();
            if (saturatingCapacity != -1 && saturatingCapacity < targetKeyCount) {
                targetKeyCount = saturatingCapacity;
                targetThread = thread;
            }
        }
        return targetThread;
    }

    public void shutDown() {
        if (isTerminated()) {
            return;
        }
        isTerminated = true;
        for (StealingSelectorThread thread : threads) {
            thread.exit();
        }
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    /**
     * 执行一个任务
     */
    public void execute(IoTask task) {

    }
}
