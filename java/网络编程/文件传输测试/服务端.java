package ������.�ļ��������;

import java.io.*;
import java.net.*;

public class �����{

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