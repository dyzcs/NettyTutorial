package com.dyzcs.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2020/11/20.
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    // 1.IdleStateHandler是netty提供的处理空闲状态的处理器
                    // 2.参数
                    // long readerIdleTime: 表示多长时间没有读，会发送一个心跳检测包检测是否还是连接状态
                    // long writerIdleTime: 表示多长时间没有写，会发送一个心跳检测包检测是否还是连接状态
                    // long allIdleTime: 表示多长时间没有读写，会发送一个心跳检测包检测是否还是连接状态
                    // 3.文档说明
                    // Triggers an IdleStateEvent when a Channel has not performed read, write, or both operation for a while.
                    // 4.当IdleStateHandler事件触发后，就会传递给管道下一个handler去处理。通过调用(触发)下一个handler的userEventTriggered方法,在该方法中去处理IdleStateEvent(读空闲，写空闲，读写空闲)
                    pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                    // 加入一个对空闲检测进一步处理的handler
                    pipeline.addLast(new MyServerHandler());
                }
            });

            ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
