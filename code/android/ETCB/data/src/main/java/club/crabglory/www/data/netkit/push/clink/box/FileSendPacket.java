package club.crabglory.www.data.netkit.push.clink.box;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import club.crabglory.www.data.netkit.push.clink.core.SendPacket;

import static club.crabglory.www.data.netkit.push.clink.core.Packet.TYPE_STREAM_FILE;

/**
 * 文件发送包
 */
public class FileSendPacket extends SendPacket<FileInputStream> {
    private final File file;

    public FileSendPacket(File file) {
        this.file = file;
        this.length = file.length();
    }

    @Override
    public byte type() {
        return TYPE_STREAM_FILE;
    }

    /**
     * 使用File构建文件读取流，用以读取本地的文件数据进行发送
     *
     * @return 文件读取流
     */
    @Override
    protected FileInputStream createStream() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
