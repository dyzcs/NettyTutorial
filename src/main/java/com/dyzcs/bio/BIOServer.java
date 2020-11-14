package com.dyzcs.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2020/11/14.
 */
public class BIOServer {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ServerSocket server;
        try {
            server = new ServerSocket(6666);
            System.out.println("服务器启动");
            while (true) {
                final Socket accept = server.accept();
                System.out.println("连接到一个客户端");
                threadPool.execute(() -> handler(accept));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handler(Socket socket) {
        InputStream is = null;
        try {
            System.out.println("开始" + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
            is = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                System.out.print(Thread.currentThread().getName());
                System.out.println(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getId() + " " + Thread.currentThread().getName() + "关闭");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(is).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
