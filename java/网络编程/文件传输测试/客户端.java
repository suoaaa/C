package 网络编程.文件传输测试;

import java.io.*;
import java.net.*;
import java.util.*;

public class 客户端 {

    public static void main(String[] args) throws IOException {
        Socket s;
        try{
            InetAddress i=InetAddress.getLocalHost();
            s=new Socket(i, 5000);
            System.out.println("服务器已链接");
            String path="E:\\个人编程\\代码-全\\java\\考试系统\\题库.txt ";
            byte [ ]b=new byte[1024];
            // Scanner sc=new Scanner(System.in);
            // path=new String(sc.nextLine().getBytes(),"GBK");
            // System.out.println(path);
            BufferedInputStream o=new BufferedInputStream(new FileInputStream(path));
            o.read(b);
            OutputStream o2=s.getOutputStream();
            // o2.write(new String (b).getBytes("GBK"));
            o2.write(b);
            s.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}