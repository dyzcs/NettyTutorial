package com.dyzcs.netty.codec2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by Administrator on 2020/11/17.
 * <p>
 * 1.自定义一个handler需要继承netty规定好的某个HandlerAdapter
 * 2.这时自定义的Handler，才能成为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MyDataInfo.MyMessage message = (MyDataInfo.MyMessage) msg;
        MyDataInfo.MyMessage.DataType dataType = message.getDataType();
        if (dataType == MyDataInfo.MyMessage.DataType.TeacherType) {

            MyDataInfo.Teacher teacher = message.getTeacher();
            System.out.println("id = " + teacher.getId() + " name = " + teacher.getName());

        } else if (dataType == MyDataInfo.MyMessage.DataType.WorkerType) {
            MyDataInfo.Worker worker = message.getWorker();
            System.out.println("id = " + worker.getName() + " age = " + worker.getAge());
        } else {
            System.out.println("传输的类型不正确");
        }
    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // writeAndFlush: 将数据写入到缓冲并刷新
        // 一般来讲，要对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client", CharsetUtil.UTF_8));
    }

    // 处理异常，一般是要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
