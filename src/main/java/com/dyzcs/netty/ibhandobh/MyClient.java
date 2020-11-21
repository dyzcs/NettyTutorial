package com.dyzcs.netty.ibhandobh;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;

/**
 * Created by Administrator on 2020/11/21.
 */
public class MyClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect(InetAddress.getLocalHost(), 9999).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }
    }
}
