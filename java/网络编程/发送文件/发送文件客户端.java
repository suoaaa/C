package 网络编程.发送文件;

import java.io.*;
import java.net.*;

public class 发送文件客户端 {

    public static void main(String[] args) throws IOException {
        InetAddress i=InetAddress.getLocalHost();
        System.out.println(i);
        Socket s=new Socket(InetAddress.getLocalHost(), 5000);
        System.out.println("服务器已链接");
        OutputStream o=s.getOutputStream();
        //BufferedOutputStream o=new BufferedOutputStream(s.getOutputStream());
        o.write("null".getBytes());
        o.flush();
        s.shutdownOutput();
    }
}