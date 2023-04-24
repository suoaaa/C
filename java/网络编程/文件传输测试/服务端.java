package 网络编程.文件传输测试;

import java.io.*;
import java.net.*;

public class 服务端 {

    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(5000);
        Socket s=ss.accept();
        InputStream o=s.getInputStream();
        byte[] b=o.readAllBytes();
        System.out.println(new String(b));

        ss.close();
    }
}