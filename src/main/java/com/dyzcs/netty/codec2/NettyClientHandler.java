package com.dyzcs.netty.codec2;

import com.dyzcs.netty.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by Administrator on 2020/11/18.
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    // 当通道就绪就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 发送一个Student对象到服务器
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("宋江").build();
        ctx.writeAndFlush(student);
    }

    // 当通道有读取事件时，会触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
