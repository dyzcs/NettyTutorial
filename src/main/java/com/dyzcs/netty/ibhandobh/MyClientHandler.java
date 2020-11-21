package com.dyzcs.netty.ibhandobh;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Administrator on 2020/11/21.
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器 ip = " + ctx.channel().remoteAddress());
        System.out.println("收到服务器的消息 = " + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive发送数据");
        ctx.writeAndFlush(123456L);
    }
}
