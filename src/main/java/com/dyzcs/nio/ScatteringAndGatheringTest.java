package com.dyzcs.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Created by Administrator on 2020/11/15.
 * <p>
 * Scattering: 将数据写入到buffer时，可以采用buffer数组，依次写(分散)
 * Gathering: 将数据读出到buffer时，可以采用buffer数组，依次读
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

            // 绑定端口到socket并启动
            serverSocketChannel.socket().bind(inetSocketAddress);

            // 创建buffer数组
            ByteBuffer[] byteBuffers = new ByteBuffer[2];
            byteBuffers[0] = ByteBuffer.allocate(5);
            byteBuffers[1] = ByteBuffer.allocate(3);

            // 等待客户端连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            // 假定从客户端接收8个字节
            int messageLength = 8;
            // 循环读取
            while (true) {
                int byteRead = 0;
                while (byteRead < messageLength) {
                    long l = socketChannel.read(byteBuffers);
                    byteRead += l;
                    System.out.println("byteRead=" + byteRead);
                    // 使用流答应，当前buffer的position和limit
                    Arrays.stream(byteBuffers).map(buffer -> "position=" + buffer.position() + ", limit=" + buffer.limit()).forEach(System.out::println);
                }

                // 将所有的buffer进行翻转
                Arrays.stream(byteBuffers).forEach(Buffer::flip);

                // 将数据读出显示到客户端
                long byteWrite = 0;
                while (byteWrite < messageLength) {
                    long l = socketChannel.write(byteBuffers);
                    byteWrite += l;
                }

                // 将所有的buffer进行复位操作
                Arrays.stream(byteBuffers).forEach(Buffer::clear);

                System.out.println("byteRead=" + byteRead + " byteWrite=" + byteWrite + " messageLength=" + messageLength);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
