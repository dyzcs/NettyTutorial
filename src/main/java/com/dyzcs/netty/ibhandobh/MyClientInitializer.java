package com.dyzcs.netty.ibhandobh;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by Administrator on 2020/11/21.
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 加入一个出站的handler对数据进行一个编码
        pipeline.addLast(new MyLongToByteEncoder());

        pipeline.addLast(new MyClientHandler());
    }
}
