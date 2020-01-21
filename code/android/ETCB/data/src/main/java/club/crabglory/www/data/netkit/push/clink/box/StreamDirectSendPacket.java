package club.crabglory.www.data.netkit.push.clink.box;


import java.io.InputStream;

import club.crabglory.www.data.netkit.push.clink.core.Packet;
import club.crabglory.www.data.netkit.push.clink.core.SendPacket;


/**
 * 直流发送Packet
 */
public class StreamDirectSendPacket extends SendPacket<InputStream> {
    private InputStream inputStream;

    public StreamDirectSendPacket(InputStream inputStream) {
        // 用以读取数据进行输出的输入流
        this.inputStream = inputStream;
        // 长度不固定，所以为最大值
        this.length = MAX_PACKET_SIZE;
    }

    @Override
    public byte type() {
        return Packet.TYPE_STREAM_DIRECT;
    }

    @Override
    protected InputStream createStream() {
        return inputStream;
    }
}
