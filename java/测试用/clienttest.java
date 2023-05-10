package ²âÊÔÓÃ;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class clienttest {
    public static void main(String[] args) throws Exception{
        Socket s=new Socket(InetAddress.getLocalHost(), 5500);
        try{
            byte[] b="123".getBytes();
            BufferedOutputStream o = new BufferedOutputStream(s.getOutputStream());
            if(b.length>=127) {
                o.write(b,0,127);
                o.write("@".getBytes());
                o.flush();
                return;
            }else o.write(b);
            int m = 128 - b.length;
            int i = 0;
            for (int j = m; j > 0; i++) j /= 10;
            for (; i < m; i++) {
                o.write("@".getBytes());
            }
            o.write(("" + m).getBytes());
            o.flush();
        }catch (Exception ignored){}

    }
}
