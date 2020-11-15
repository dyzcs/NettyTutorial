package com.dyzcs.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;

/**
 * Created by Administrator on 2020/11/15.
 */
public class NIOClient {
    public static void main(String[] args) {
        SocketChannel socketChannel = null;
        try {
            // 得到一个网络通道
            socketChannel = SocketChannel.open();
            // 设置非阻塞
            socketChannel.configureBlocking(false);
            // 提供服务器端ip和port
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 6666);
            // 连接服务器
            if (!socketChannel.connect(inetSocketAddress)) {
                while (!socketChannel.finishConnect()) {
                    System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作");
                }
            }
            // 连接成功发送数据
            String str = "hello world";
            ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
            // 发送数据，将buffer数据写入channel
            socketChannel.write(buffer);
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(socketChannel).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
