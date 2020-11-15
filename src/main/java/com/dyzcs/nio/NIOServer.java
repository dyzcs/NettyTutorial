package com.dyzcs.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2020/11/15.
 */
public class NIOServer {
    public static void main(String[] args) {
        try {
            // 创建serverSocketChannel
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 得到一个Selector对象
            Selector selector = Selector.open();
            // 绑定端口6666，在服务器端监听
            serverSocketChannel.socket().bind(new InetSocketAddress(6666));
            // 设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            // 把serverSocket注册到selector，关心事件为OP_ACCEPT
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            // 循环等到客户端连接
            while (true) {
                // 等待一面，没有事件发生，继续
                if (selector.select(1000) == 0) {
                    System.out.println("服务器等待了1秒，无连接");
                    continue;
                }
                // 如果返回 > 0，获取到相关的selectionKey集合
                // 1.如果返回>0，表示已经获取到关注的时间
                // 2.selector.selectedKeys()返回关注的事件集合
                // 3.通过selectionKeys可以反向获取通道
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                // 遍历Set<SelectionKey>
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                while (keyIterator.hasNext()) {
                    // 获取到selectionKey
                    SelectionKey key = keyIterator.next();
                    // 根据key对应的通道发生的事件做相应的处理
                    if (key.isAcceptable()) {
                        // 如果有新的连接事件，给该客户端生成一个SocketChannel
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        // 将当前socketChannel注册到selector，关注事件为OP_READ
                        // 同时给socketChannel关联一个Buffer
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
