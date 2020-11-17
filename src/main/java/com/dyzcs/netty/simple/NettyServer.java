package com.dyzcs.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Administrator on 2020/11/17.
 */
public class NettyServer {
    public static void main(String[] args) {
        // 创建BossGroup和WorkerGroup
        // 1.创建两个线程组bossGroup和workerGroup
        // 2.bossGroup只是处理连接请求，真正和client业务处理，交给workerGroup处理
        // 3.两个都是无限循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 创建服务器端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                .channel(NioServerSocketChannel.class)  // 使用NIOServerSocketChannel作为服务器的通道实现
                .option(ChannelOption.SO_BACKLOG, 128)  // 设置线程队列等待连接的个数
                .childOption(ChannelOption.SO_KEEPALIVE, true)  // 设置保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道初始化对象
                    // 向pipeline设置处理器
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(null);
                    }
                }); // 给WorkerGroup的EventLoop对应的管道设置处理器
    }
}
