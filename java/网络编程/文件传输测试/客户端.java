package ������.�ļ��������;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class �ͻ��� {

    public static void main(String[] args) throws IOException {
        Socket s;
        try{
            InetAddress i=InetAddress.getLocalHost();
            s=new Socket(i, 5000);
            System.out.println("������������");
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