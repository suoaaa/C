package 文件服务器与客户端;

import java.io.*;
import java.net.*;

public class 服务端 {

    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(5000);
        Socket s=ss.accept();
        InputStream o=s.getInputStream();
        byte[] b = new byte[1024];
        o.read(b);
        o.read(b);o.read(b);o.read(b);

        System.out.println(new String(b,0,4));
    }
}