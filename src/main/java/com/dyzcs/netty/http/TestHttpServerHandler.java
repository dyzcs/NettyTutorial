package com.dyzcs.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * Created by Administrator on 2020/11/18.
 * <p>
 * 1.SimpleChannelInboundHandler是ChannelInboundHandlerAdapter子类
 * 2.HttpObject客户端和服务器端相互通信的数据封装成HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // channelRead0读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 判断msg是不是HttpRequest请求
        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型 = " + msg.getClass());
            System.out.println("客户端地址" + ctx.channel().remoteAddress());

            // 回复信息给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("hello，我是服务器", CharsetUtil.UTF_8);

            // 构建一个http的响应 HTTPResponse
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 将构建好的response返回
            ctx.writeAndFlush(response);
        }
    }
}
