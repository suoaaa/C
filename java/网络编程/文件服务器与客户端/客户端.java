package ������.�ļ���������ͻ���;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class �ͻ��� {

    public static void main(String[] args) throws IOException {
        InetAddress i=InetAddress.getLocalHost();
        System.out.println(i);
        Socket s=new Socket(InetAddress.getLocalHost(), 5000);
        System.out.println("������������");
        OutputStream o=s.getOutputStream();
        o.write("null".getBytes());
        s.close();
    }
}