package ������.�ļ��������;

import java.io.*;
import java.net.*;

public class ����� {

    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(5000);
        Socket s=ss.accept();
        InputStream o=s.getInputStream();
        byte[] b=o.readAllBytes();
        System.out.println(new String(b));

        ss.close();
    }
}