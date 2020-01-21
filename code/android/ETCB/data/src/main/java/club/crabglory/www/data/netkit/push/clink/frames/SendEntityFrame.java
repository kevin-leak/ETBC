package club.crabglory.www.data.netkit.push.clink.frames;

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;

import club.crabglory.www.data.netkit.push.clink.core.Frame;
import club.crabglory.www.data.netkit.push.clink.core.IoArgs;
import club.crabglory.www.data.netkit.push.clink.core.SendPacket;

public class SendEntityFrame extends AbsSendPacketFrame {
    private final ReadableByteChannel channel;
    private final long unConsumeEntityLength;

    SendEntityFrame(short identifier,
                    long entityLength,
                    ReadableByteChannel channel,
                    SendPacket packet) {
        super((int) Math.min(entityLength, Frame.MAX_CAPACITY),
                Frame.TYPE_PACKET_ENTITY,
                Frame.FLAG_NONE,
                identifier,
                packet);
        // 1234567890
        // 1234 5678 90
        // 10 4,6 4,2 2
        this.unConsumeEntityLength = entityLength - bodyRemaining;
        this.channel = channel;
    }

    @Override
    protected int consumeBody(IoArgs args) throws IOException {
        if (packet == null) {
            // 已终止当前帧，则填充假数据
            return args.fillEmpty(bodyRemaining);
        }
        return args.readFrom(channel);
    }

    @Override
    public Frame buildNextFrame() {
        if (unConsumeEntityLength == 0) {
            return null;
        }
        // 将未消费的长度用于构建下一帧
        return new SendEntityFrame(getBodyIdentifier(),
                unConsumeEntityLength, channel, packet);
    }
}
