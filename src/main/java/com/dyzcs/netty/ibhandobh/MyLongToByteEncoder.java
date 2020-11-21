package com.dyzcs.netty.ibhandobh;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Administrator on 2020/11/21.
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    // 编码方法
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder.encode 被调用");
        System.out.println("msg = " + msg);
        out.writeLong(msg);
    }
}
