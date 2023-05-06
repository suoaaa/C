package 网络编程.文件传输测试;

import java.io.*;
import java.net.*;

import javax.swing.*;

public class 服务端{

    public static void main(String[] args) throws IOException {
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showDialog(new JLabel(), "选择");
        jfc.setVisible(true);
		String rootpath=jfc.getSelectedFile().getAbsolutePath();
        ServerSocket ss=new ServerSocket(5000);
        Socket s=ss.accept();
        InputStream o=s.getInputStream();
        byte[] b=new byte[1024];
        o.read(b);
        // System.out.println(path);
        BufferedOutputStream i=new BufferedOutputStream(new FileOutputStream("E:\\bot\\2\\a.txt"));
        i.write(b);
        // i.write(new String(b).getBytes("GBK"));
        i.flush();
        i.close();
        // System.out.println(new String(b));
        ss.close();
    }
}