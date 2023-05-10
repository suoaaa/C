package 测试用;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class severtest {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket=new ServerSocket(5500);
        Socket s=serverSocket.accept();
        BufferedInputStream i;
        // i=new BufferedInputStream(new FileInputStream("E:\\个人编程\\代码-全\\java\\curriculumDesign\\severSave\\1.txt"));
        // byte[] b=new byte[1024];
        // i.read(b);
        // System.out.println(new String(b));
        BufferedOutputStream o=new BufferedOutputStream(s.getOutputStream());
        o.write("adadadada".getBytes());
        // o.write(b);
        o.flush();
        // i.close();
        o.close();
    }
}
