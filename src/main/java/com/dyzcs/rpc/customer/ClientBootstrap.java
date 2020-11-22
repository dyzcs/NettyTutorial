package com.dyzcs.rpc.customer;

import com.dyzcs.rpc.netty.NettyClient;
import com.dyzcs.rpc.publicinterface.HelloService;

/**
 * Created by Administrator on 2020/11/22.
 */
public class ClientBootstrap {
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) {
        // 创建一个消费者
        NettyClient customer = new NettyClient();
        // 创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);

        // 通过代理对象调用服务提供者提供的服务
        String res = service.hello("你好");
        System.out.println("调用的结果 res = " + res);
    }
}
