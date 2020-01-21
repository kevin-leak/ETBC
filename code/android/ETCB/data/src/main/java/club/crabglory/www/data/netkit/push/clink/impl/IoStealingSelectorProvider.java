package club.crabglory.www.data.netkit.push.clink.impl;


import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import club.crabglory.www.data.netkit.push.clink.core.IoProvider;
import club.crabglory.www.data.netkit.push.clink.core.IoTask;
import club.crabglory.www.data.netkit.push.clink.core.stealing.StealingSelectorThread;

/**
 * @author KevinLeak
 * @blog https://www.crabglory.club
 * @github https://github.com/kevin-leak
 * @time 2020-01-17 15:20
 * 没有加入服务的stealing provider
 */
public class IoStealingSelectorProvider implements IoProvider {

    private final StealingSelectorThread thread;

    public IoStealingSelectorProvider() throws IOException {
        Selector selector = Selector.open();
        thread = new StealingSelectorThread(selector) {
            @Override
            protected boolean processTask(IoTask ioTask) {
                ioTask.providerCallback.run();
                return false;
            }
        };
        thread.setName("IoStealingSelectorProvider");
        thread.start();
    }

    @Override
    public boolean registerInput(SocketChannel channel, IoProvider.HandleProviderCallback callback) {
        return thread.register(channel, callback, SelectionKey.OP_READ);
    }

    @Override
    public boolean registerOutput(SocketChannel channel, HandleProviderCallback callback) {
        return thread.register(channel, callback, SelectionKey.OP_WRITE);
    }

    @Override
    public void unRegisterInput(SocketChannel channel) {
        thread.unRegister(channel);
    }

    @Override
    public void unRegisterOutput(SocketChannel channel) {
        thread.unRegister(channel);
    }

    @Override
    public void close() throws IOException {
        thread.exit();
    }
}

