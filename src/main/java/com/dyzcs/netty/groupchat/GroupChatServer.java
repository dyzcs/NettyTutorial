package com.dyzcs.netty.groupchat;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Created by Administrator on 2020/11/19.
 */
public class GroupChatServer {
    // 监听端口
    private int port;

    public GroupChatServer(int port) {
        this.port = port;
    }

    // 处理客户端请求
    public void run() {
        // 创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
    }

    public static void main(String[] args) {

    }
}
