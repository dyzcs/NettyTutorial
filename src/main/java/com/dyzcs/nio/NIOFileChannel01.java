package com.dyzcs.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * Created by Administrator on 2020/11/15.
 */
public class NIOFileChannel01 {
    public static void main(String[] args) {
        String str = "hello, 中国";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("data/file01.txt");
            // 通过输出流获取对应的文件channel
            // fileChannel真是类型是FileChannelImpl
            FileChannel fileChannel = fos.getChannel();

            // 创建一个缓冲区byteBuffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            // 将str放入byteBuffer
            byteBuffer.put(str.getBytes());

            // 对byteBuffer翻转
            byteBuffer.flip();

            // 将byteBuffer数据写入到fileChannel
            fileChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(fos).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
