package com.dyzcs.netty.ibhandobh;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by Administrator on 2020/11/21.
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 入站的handler进行解码 MyByteToLongDecoder
        pipeline.addLast(new MyByteToLongDecoder());

        pipeline.addLast(new MyServerHandler());
    }
}
