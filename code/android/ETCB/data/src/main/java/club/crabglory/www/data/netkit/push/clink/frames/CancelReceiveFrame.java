package club.crabglory.www.data.netkit.push.clink.frames;

import java.io.IOException;

import club.crabglory.www.data.netkit.push.clink.core.IoArgs;

/**
 * 取消传输帧，接收实现
 */
public class CancelReceiveFrame extends AbsReceiveFrame {

    CancelReceiveFrame(byte[] header) {
        super(header);
    }

    @Override
    protected int consumeBody(IoArgs args) throws IOException {
        return 0;
    }
}
