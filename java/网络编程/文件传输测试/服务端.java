package 网络编程.文件传输测试;

import java.io.*;
import java.net.*;

public class 服务端{

    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(5000);
        Socket s=ss.accept();
        InputStream o=s.getInputStream();
        byte[] b=new byte[1024];
        o.read(b);
        BufferedOutputStream i=new BufferedOutputStream(new FileOutputStream("a.txt"));
        i.write(b);
        // i.write(new String(b).getBytes("GBK"));
        i.flush();
        // System.out.println(new String(b));
        ss.close();
    }
}