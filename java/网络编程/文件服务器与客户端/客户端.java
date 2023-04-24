package 网络编程.文件服务器与客户端;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class 客户端 {

    public static void main(String[] args) throws IOException {
        InetAddress i=InetAddress.getLocalHost();
        System.out.println(i);
        Socket s=new Socket(InetAddress.getLocalHost(), 5000);
        System.out.println("服务器已链接");
        OutputStream o=s.getOutputStream();
        o.write("null".getBytes());
        s.close();
    }
}