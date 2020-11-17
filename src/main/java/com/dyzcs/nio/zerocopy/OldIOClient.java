package com.dyzcs.nio.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

/**
 * Created by Administrator on 2020/11/17.
 */
public class OldIOClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 7001);
            FileInputStream fis = new FileInputStream("data/1.txt");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            byte[] buffer = new byte[1024];
            int len;
            long total = 0;
            while ((len = fis.read(buffer)) != -1) {
                total += len;
                dos.write(buffer, 0, len);
            }
            System.out.println("发送总字节数" + total);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(socket).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
