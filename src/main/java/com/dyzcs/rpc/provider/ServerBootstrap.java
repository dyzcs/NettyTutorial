package com.dyzcs.rpc.provider;

import com.dyzcs.rpc.netty.NettyServer;

/**
 * Created by Administrator on 2020/11/22.
 * <p>
 * ServerBootstrap: 启动一个服务提供者
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 9999);
    }
}
