package ������.�����ļ�;

import java.io.*;
import java.net.*;

public class �����ļ��ͻ��� {

    public static void main(String[] args) throws IOException {
        InetAddress i=InetAddress.getLocalHost();
        System.out.println(i);
        Socket s=new Socket(InetAddress.getLocalHost(), 5000);
        System.out.println("������������");
        OutputStream o=s.getOutputStream();
        //BufferedOutputStream o=new BufferedOutputStream(s.getOutputStream());
        o.write("null".getBytes());
        o.flush();
        s.shutdownOutput();
    }
}