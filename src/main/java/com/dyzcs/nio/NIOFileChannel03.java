package com.dyzcs.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * Created by Administrator on 2020/11/15.
 */
public class NIOFileChannel03 {
    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream("data/1.txt");
            FileChannel fisChannel = fis.getChannel();

            fos = new FileOutputStream("data/2.txt");
            FileChannel fosChannel = fos.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 读取数据
            while (fisChannel.read(buffer) != -1) {
                // 翻转buffer
                buffer.flip();
                // 写入数据 -> 2.txt
                fosChannel.write(buffer);
                // 重置buffer
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(fis).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(fos).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
