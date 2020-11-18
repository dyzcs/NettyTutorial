package com.dyzcs.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by Administrator on 2020/11/17.
 * <p>
 * 1.自定义一个handler需要继承netty规定好的某个HandlerAdapter
 * 2.这时自定义的Handler，才能成为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    // 读取实际数据(这里我们可以读取客户端发送的消息)
    // 1.ChannelHandlerContext ctx: 上下文对象，含有管道pipeline，通道channel，地址
    // 2.Object msg: 就是客户端发送的数据，默认Object
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server ctx = " + ctx);
        // 将msg转成ByteBuf
        // ByteBuf是Netty提供的
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是: " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址: " + ctx.channel().remoteAddress());
    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush: 将数据写入到缓冲并刷新
        // 一般来讲，要对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client", CharsetUtil.UTF_8));
    }

    // 处理异常，一般是要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
