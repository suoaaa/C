package ������.�ļ��������;

import java.io.*;
import java.net.*;
import java.util.*;

public class �ͻ��� {

    public static void main(String[] args) throws IOException {
        Socket s;
        try{
            InetAddress i=InetAddress.getLocalHost();
            s=new Socket(i, 5000);
            System.out.println("������������");
            String path="E:\\���˱��\\����-ȫ\\java\\����ϵͳ\\���.txt ";
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