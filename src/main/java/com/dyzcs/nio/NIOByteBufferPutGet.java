package com.dyzcs.nio;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2020/11/15.
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        // 类型化方式放入数据
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('邓');
        buffer.putShort((short) 4);

        // 取出数据
        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
    }
}
