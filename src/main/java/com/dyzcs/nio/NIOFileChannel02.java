package com.dyzcs.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * Created by Administrator on 2020/11/15.
 */
public class NIOFileChannel02 {
    public static void main(String[] args) {
        FileInputStream fis = null;
        try {
            File file = new File("data/file01.txt");
            fis = new FileInputStream(file);
            FileChannel fileChannel = fis.getChannel();

            // 创建缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
            // 将通道的数据读入到Buffer
            fileChannel.read(byteBuffer);

            // 将byteBuffer中的字节转成字符串
            System.out.println(new String(byteBuffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(fis).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
