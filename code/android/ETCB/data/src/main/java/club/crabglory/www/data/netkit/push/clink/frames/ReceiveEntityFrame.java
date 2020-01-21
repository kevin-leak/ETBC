package club.crabglory.www.data.netkit.push.clink.frames;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;

import club.crabglory.www.data.netkit.push.clink.core.IoArgs;

public class ReceiveEntityFrame extends AbsReceiveFrame {
    private WritableByteChannel channel;

    ReceiveEntityFrame(byte[] header) {
        super(header);
    }

    public void bindPacketChannel(WritableByteChannel channel) {
        this.channel = channel;
    }

    @Override
    protected int consumeBody(IoArgs args) throws IOException {
        return channel == null ? args.setEmpty(bodyRemaining) : args.writeTo(channel);
    }
}
