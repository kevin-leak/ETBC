package club.crabglory.www.data.netkit.push.clink.impl;


import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import club.crabglory.www.data.netkit.push.clink.core.IoProvider;
import club.crabglory.www.data.netkit.push.clink.core.IoTask;
import club.crabglory.www.data.netkit.push.clink.core.stealing.StealingSelectorThread;
import club.crabglory.www.data.netkit.push.clink.core.stealing.StealingService;

/**
 * @author KevinLeak
 * @blog https://www.crabglory.club
 * @github https://github.com/kevin-leak
 * @time 2020-01-16 21:11
 */
public class IoServiceSelectorProvider implements IoProvider {

    private final IoStealingThread[] threads;
    private final StealingService stealingService;

    public IoServiceSelectorProvider(int poolSize) throws IOException {
        IoStealingThread[] threads = new IoStealingThread[poolSize];
        for (int i = 0; i < poolSize; i++) {
            Selector selector = Selector.open();
            threads[i] = new IoStealingThread("IoProvider-Thread-" + (i + 1), selector);
        }
        StealingService service = new StealingService(10, threads);
        for (IoStealingThread thread : threads) {
            thread.setStealingService(service);
            thread.start();
        }
        this.stealingService = service;
        this.threads = threads;
    }

    @Override
    public boolean registerInput(SocketChannel channel, HandleProviderCallback callback) {
        StealingSelectorThread notBusyThread = stealingService.getNotBusyThread();
        if (notBusyThread != null)
            return notBusyThread.register(channel, callback, SelectionKey.OP_READ);
        return false;
    }

    @Override
    public boolean registerOutput(SocketChannel channel, HandleProviderCallback callback) {
        StealingSelectorThread notBusyThread = stealingService.getNotBusyThread();
        if (notBusyThread != null)
            return notBusyThread.register(channel, callback, SelectionKey.OP_WRITE);
        return false;
    }

    @Override
    public void unRegisterInput(SocketChannel channel) {
        for (IoStealingThread thread : threads) {
            thread.unRegister(channel);
        }
    }

    @Override
    public void unRegisterOutput(SocketChannel channel) {
        for (IoStealingThread thread : threads) {
            thread.unRegister(channel);
        }
    }

    @Override
    public void close() throws IOException {
        stealingService.shutDown();
    }

    static class IoStealingThread extends StealingSelectorThread {

        protected IoStealingThread(String name, Selector selector) {
            super(selector);
            setName(name);
        }

        @Override
        protected boolean processTask(IoTask ioTask) {
            ioTask.providerCallback.run();
            return false;
        }
    }
}
