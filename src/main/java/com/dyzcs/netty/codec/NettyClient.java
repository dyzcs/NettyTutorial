package com.dyzcs.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import java.net.InetAddress;

/**
 * Created by Administrator on 2020/11/18.
 */
public class NettyClient {
    public static void main(String[] args) throws Exception {
        // 客户端需要一个事件循环
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            // 创建客户端启动对象
            Bootstrap bootstrap = new Bootstrap();
            // 设置相关参数
            bootstrap.group(eventExecutors) // 设置线程组
                    .channel(NioSocketChannel.class)    // 设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("encoder", new ProtobufEncoder());
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端 ok");

            // 启动客户端去连接服务端
            // 关于ChannelFuture涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect(InetAddress.getLocalHost(), 9999).sync();
            // 给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
