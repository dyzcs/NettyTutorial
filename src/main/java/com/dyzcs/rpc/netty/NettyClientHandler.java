package com.dyzcs.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2020/11/22.
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable<String> {
    // 上下文
    private ChannelHandlerContext context;
    // 调用方法时传入参数
    private String para;
    // 返回结果
    private String result;

    // (1)与服务器连接创建后，会被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        context = ctx;
    }

    // (4)收到数据后，调用方法
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) {
        result = (String) msg;
        // 唤醒等待的线程
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    // (3)(5)被代理对象调用，发送数据给服务器 --> wait --> 等待被唤醒 --> 返回结果
    @Override
    public synchronized String call() throws Exception {
        context.writeAndFlush(para);
        // 进行wait
        // 等到channelRead方法获取到服务器的结果，唤醒
        wait();
        return result;
    }

    // (2)
    public void setPara(String para) {
        this.para = para;
    }
}
