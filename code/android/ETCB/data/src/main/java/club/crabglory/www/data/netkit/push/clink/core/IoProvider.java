package club.crabglory.www.data.netkit.push.clink.core;

import java.io.Closeable;
import java.nio.channels.SocketChannel;

/**
 * 提供输入输出环境，开启
 * 输入输出，需要在selector中注册channel
 * KEY(read)-runnable(read)进行存储
 * 开启输入输出的子线程，不断轮询是否需要读写操作。
 * 所有的输入输出中间处理的过程都在这个中体现
 * 而输入输出具体的实现都在SocketChannelAdapter中
 */
public interface IoProvider extends Closeable {
    /**
     * @param channel 客户端通道
     * @param callback 实际上是一个输入的子线程，当Sender调用send的时候真正触发这个线程
     * @return
     */
    boolean registerInput(SocketChannel channel, HandleProviderCallback callback);

    boolean registerOutput(SocketChannel channel, HandleProviderCallback callback);

    void unRegisterInput(SocketChannel channel);

    void unRegisterOutput(SocketChannel channel);


    abstract class HandleProviderCallback implements Runnable {
        /**
         * 附加本次未完全消费完成的IoArgs，然后进行自循环
         */
        protected volatile IoArgs attach;

        @Override
        public final void run() {
            onProviderIo(attach);
        }

        /**
         * 可以进行接收或者发送时的回调
         *
         * @param args 携带之前的附加值
         */
        protected abstract void onProviderIo(IoArgs args);

        /**
         * 检查当前的附加值是否未null，如果处于自循环时当前附加值不为null，
         * 此时如果外层有调度注册异步发送或者接收是错误的
         */
        public void checkAttachNull() {
            if (attach != null) {
                throw new IllegalStateException("Current attach is not empty!");
            }
        }
    }

}
