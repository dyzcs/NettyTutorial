package com.dyzcs.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * Created by Administrator on 2020/11/15.
 */
public class NIOFileChannel04 {
    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream("data/a.jpg");
            FileChannel fisChannel = fis.getChannel();

            fos = new FileOutputStream("data/a2.jpg");
            FileChannel fosChannel = fos.getChannel();

            // 使用transferFrom完成拷贝
            fosChannel.transferFrom(fisChannel, 0, fisChannel.size());
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
