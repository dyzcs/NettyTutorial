package com.dyzcs.rpc.provider;

import com.dyzcs.rpc.publicinterface.HelloService;

/**
 * Created by Administrator on 2020/11/22.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息 = " + msg);
        // 根据msg返回不同的结果
        if (msg != null) {
            return "你好客户端，我已经收到你的消息 [" + msg + "]";
        }
        return "你好客户端，我已经收到你的消息";
    }
}
