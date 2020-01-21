package club.crabglory.www.data.netkit.push.clink.core;


import java.nio.channels.SocketChannel;

/**
 * @author KevinLeak
 * @blog https://www.crabglory.club
 * @github https://github.com/kevin-leak
 * @time 2020-01-16 21:06
 *
 * 可以进行任务的任务封装
 * 任务执行的回调providerCallback、当前任务类型ops、任务对应的通道channel
 */
public class IoTask {
    public final SocketChannel channel;
    public final IoProvider.HandleProviderCallback providerCallback;
    public final int ops;
    public IoTask(SocketChannel channel, IoProvider.HandleProviderCallback providerCallback, int ops) {
        this.channel = channel;
        this.providerCallback = providerCallback;
        this.ops = ops;
    }
}
