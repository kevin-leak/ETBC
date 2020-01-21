package club.crabglory.www.data.netkit.push.clink.box;



import java.io.OutputStream;

import club.crabglory.www.data.netkit.push.clink.core.Packet;
import club.crabglory.www.data.netkit.push.clink.core.ReceivePacket;

/**
 * 直流接收Packet
 */
public class StreamDirectReceivePacket extends ReceivePacket<OutputStream, OutputStream> {
    private OutputStream outputStream;

    public StreamDirectReceivePacket(OutputStream outputStream, long length) {
        super(length);
        // 用以读取数据进行输出的输入流
        this.outputStream = outputStream;
    }

    @Override
    public byte type() {
        return Packet.TYPE_STREAM_DIRECT;
    }

    @Override
    protected OutputStream createStream() {
        return outputStream;
    }

    @Override
    protected OutputStream buildEntity(OutputStream stream) {
        return outputStream;
    }
}
