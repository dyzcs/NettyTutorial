package com.dyzcs.nio.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * Created by Administrator on 2020/11/17.
 */
public class OldIOServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(7001);
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                byte[] buffer = new byte[1024];
                while (true) {
                    int readCount = dis.read(buffer, 0, buffer.length);

                    if (-1 == readCount) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(serverSocket).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
