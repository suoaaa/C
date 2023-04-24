package 网络编程.发送文件;

import java.io.*;
import java.net.*;


public class 发送文件服务器 {

    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(5000);
        Socket s=ss.accept();
        InputStream o=s.getInputStream();
        // BufferedInputStream o=new BufferedInputStream(s.getInputStream());
        byte[] b=new byte[1024];
        // o.flush();
        o.read(b);
        System.out.println(new String(b));
        ss.close();
        
    }
}