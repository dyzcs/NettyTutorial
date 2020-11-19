package com.dyzcs.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * Created by Administrator on 2020/11/19.
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        // 创建ByteBuf
        ByteBuf buf = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);

        // 使用相关的API
        if (buf.hasArray()) {
            byte[] arr = buf.array();
            System.out.println(new String(arr, CharsetUtil.UTF_8));
        }
    }
}
