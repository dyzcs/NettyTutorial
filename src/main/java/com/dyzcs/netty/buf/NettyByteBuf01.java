package com.dyzcs.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by Administrator on 2020/11/19.
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {
        // 1.创建一个对象，该对象包含一个数组arr = byte[10]
        // 2.Netty Buffer中，不需要使用flip进行反转，底层维护了readerIndex和writeIndex
        // 3.通过readIndex、writeIndex和capacity，将buffer分为三段
        // 0 -- readIndex 已经读取的区域
        // readIndex -- writeIndex 可读的区域
        // writeIndex -- capacity 可写的区域
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println(buffer.capacity());

        for (int i = 0; i < buffer.capacity(); i++) {
//            System.out.println(buffer.getByte(i));
            System.out.println(buffer.readByte());
        }

//        for (int i : buffer.array()) {
//            System.out.println(i);
//        }
    }
}
