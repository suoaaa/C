package 网络编程.发送文件;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class 接受反馈客户端 {

    public static void main(String[] args) throws IOException {
        InetAddress i=InetAddress.getLocalHost();
        System.out.println(i);
        Socket s=new Socket(InetAddress.getLocalHost(), 5000);
        System.out.println("服务器已链接");
        OutputStream o=s.getOutputStream();
        for(int j=0;j<10;j++)   o.write("null".getBytes());
        s.close();
    }
}