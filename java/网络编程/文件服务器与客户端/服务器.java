package ������.�ļ���������ͻ���;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class ������ {

    public static void main(String[] args) throws IOException {
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showDialog(new JLabel(), "ѡ��");
        jfc.setVisible(true);
		String rootpath=jfc.getSelectedFile().getAbsolutePath();
        ServerSocket ss=new ServerSocket(5000);
        Socket s=ss.accept();
        InputStream o=s.getInputStream();
        byte[] b = new byte[1024];
        o.read(b);
        // o.read(b);
        System.out.println(new String(b));
        ss.close();
    }
}