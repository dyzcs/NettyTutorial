package com.dyzcs.nio;

import java.nio.IntBuffer;

/**
 * Created by Administrator on 2020/11/14.
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // 创建一个Buffer，大小为5，即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向Buffer中存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // 从buffer读取数据
        // 将buffer转换，读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
