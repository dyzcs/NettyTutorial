package com.dyzcs.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * Created by Administrator on 2020/11/17.
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {
        // 创建BossGroup和WorkerGroup
        // 1.创建两个线程组bossGroup和workerGroup
        // 2.bossGroup只是处理连接请求，真正和client业务处理，交给workerGroup处理
        // 3.两个都是无限循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class)  // 使用NIOServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)  // 设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)  // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道初始化对象
                        // 向pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 指定对哪种对象进行解码
                            pipeline.addLast("decoder", new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    }); // 给WorkerGroup的EventLoop对应的管道设置处理器

            System.out.println("server is ready");

            // 绑定一个端口并且同步，生成了一个ChannelFuture对象
            // 启动服务器(并绑定端口)
            ChannelFuture cf = bootstrap.bind(9999).sync();

            // 给cf注册监听器，监控事件
            cf.addListener((ChannelFutureListener) future -> {
                if (cf.isSuccess()) {
                    System.out.println("监听端口 9999 成功");
                } else {
                    System.out.println("监听端口 9999 失败");
                }
            });

            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
