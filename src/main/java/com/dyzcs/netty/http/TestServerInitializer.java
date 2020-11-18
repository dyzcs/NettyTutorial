package com.dyzcs.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by Administrator on 2020/11/18.
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 向管道加入处理器
        // 得到管道
        ChannelPipeline pipeline = ch.pipeline();

        // 1.加入一个netty提供的httpServerCodec
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        // 2.增加一个自定义的Handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
    }
}
