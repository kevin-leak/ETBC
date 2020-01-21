package club.crabglory.www.data.netkit.push.clink.box;

import java.io.ByteArrayInputStream;

import club.crabglory.www.data.netkit.push.clink.core.SendPacket;

/**
 * 纯Byte数组发送包
 */
public class BytesSendPacket extends SendPacket<ByteArrayInputStream> {
    private final byte[] bytes;

    public BytesSendPacket(byte[] bytes) {
        this.bytes = bytes;
        this.length = bytes.length;
    }

    @Override
    public byte type() {
        return TYPE_MEMORY_BYTES;
    }

    @Override
    protected ByteArrayInputStream createStream() {
        return new ByteArrayInputStream(bytes);
    }

}
