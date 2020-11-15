package com.dyzcs.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * Created by Administrator on 2020/11/15.
 * <p>
 * MappedByteBuffer可以让文件直接在内存(堆外内存)修改，操作系统不需要拷贝一次
 */
public class MappedByteBufferTest {
    public static void main(String[] args) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile("data/1.txt", "rw");
            FileChannel channel = raf.getChannel();
            // 参数1 FileChannel.MapMode.READ_WRITE 使用读写模式
            // 参数2 0 可以直接修改的其实位置
            // 参数3 5 映射到内存的大小，即将1.txt的多少个字节映射到内存
            // 即可以直接修改的范围就是[0, 5)
            MappedByteBuffer mbb = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
            mbb.put(0, (byte) 'H');
            mbb.put(3, (byte) '9');
            System.out.println("修改成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(raf).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
