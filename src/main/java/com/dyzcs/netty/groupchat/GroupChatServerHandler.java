package com.dyzcs.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2020/11/20.
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    // 定义一个channel组，管理所有的channel
    // GlobalEventExecutor.INSTANCE是全局的事件执行器，是一个单例
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // handlerAdded: 表示建立连接，一旦连接，第一个执行
    // 将当前channel加入到channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        // 将该客户加入聊天的信息推送给其他所有在线的客户端
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 加入聊天" + sdf.format(new Date()) + "\n");
        channelGroup.add(channel);
    }

    // 断开连接，将该客户端下线的消息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开了\n");
        System.out.println("channelGroup size = " + channelGroup.size());
    }

    // 表示channel处于活动状态，提示xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " 上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " 离线了");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + " 发送了消息" + msg + "\n");
            } else {
                ch.writeAndFlush("[自己]发送了消息" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
