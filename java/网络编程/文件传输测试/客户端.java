package 网络编程.文件传输测试;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class 客户端 {

    客户端(){
        new clientWindow();
    }
    class clientWindow{
        JFrame win=new JFrame("文件传输系统客户端");
        JPanel p=new JPanel();         //p作为开头按钮的容器
        JTextArea jTextArea=new JTextArea(10,10);//显示登录信息以及文件传输情况
        JScrollPane jscrollpane;//给文本框添加滚动条
        JButton position=new JButton("储存位置");
        JButton clear=new JButton("清空消息");
        clientWindow(){
            win=new JFrame("文件传输客户端");
            win.setSize(400,500);
            win.setLocationRelativeTo(null);
            win.setVisible(false);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);
            print("服务器已启动，等待链接");

            p.add(position);
            p.add(clear);
            jscrollpane =new JScrollPane(jTextArea);

            jTextArea.setEditable(false);

            win.add(p,BorderLayout.NORTH);
            win.add(jscrollpane,BorderLayout.CENTER);
            win.setVisible(true);
        }


        void print(String string){
            jTextArea.append(string+"\r\n");
            p.updateUI();
            win.repaint();
        }
    }
    public static void main(String[] args) throws IOException {
        Socket s;
        try{
            new 客户端();
            InetAddress i=InetAddress.getLocalHost();
            s=new Socket(i, 5050);
            System.out.println("服务器已链接");
            String path="E:\\桌面\\a.txt";
            byte [ ]b=new byte[1024];
            BufferedInputStream o=new BufferedInputStream(new FileInputStream(path));
            o.read(b);
            OutputStream o2=s.getOutputStream();
            // o2.write(new String (b).getBytes("GBK"));
            o2.write(b);
            s.close();
            o.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}