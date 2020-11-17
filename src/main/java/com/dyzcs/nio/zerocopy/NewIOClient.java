package com.dyzcs.nio.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2020/11/17.
 */
public class NewIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));
        FileChannel fileChannel = new FileInputStream("data/1.txt").getChannel();
        long start = System.currentTimeMillis();
        // linux下调用transferTo可以完成零拷贝
        // windows下调用transferTo一次只能传输8MB
        // transferTo底层使用零拷贝
        fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总字节数");
        System.out.println(System.currentTimeMillis() - start);
        fileChannel.close();
    }
}
