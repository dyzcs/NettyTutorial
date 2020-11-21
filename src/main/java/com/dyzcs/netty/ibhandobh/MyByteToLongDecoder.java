package com.dyzcs.netty.ibhandobh;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Administrator on 2020/11/21.
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * @param ctx 上下文
     * @param in  入站的ByteBuf
     * @param out List集合，将解码的数据传给下一个handler处理
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 因为long是8个字节，需要判断有8个字节才能读取一个long
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
