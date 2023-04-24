package 网络编程.文件传输测试;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class 客户端 {

    public static void main(String[] args) throws IOException {
        Socket s;
        try{
            InetAddress i=InetAddress.getLocalHost();
            s=new Socket(i, 5000);
            System.out.println("服务器已链接");
            OutputStream o=s.getOutputStream();
            o.write("null".getBytes());
            o.write("null".getBytes());
            s.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}