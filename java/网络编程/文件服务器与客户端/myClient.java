package 网络编程.文件服务器与客户端;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class myClient {

    InetAddress i;
    Socket s;
    String rootpath="E:\\桌面";
    myClient(){
        clientWindow clientwindow=new clientWindow();
        try{
            i=InetAddress.getLocalHost();
            s=new Socket(i, 5050);
            clientwindow.print("服务器"+i.getHostAddress()+"已链接");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    class clientWindow {
        JFrame win = new JFrame("文件传输系统客户端");
        JPanel p = new JPanel();         //p作为开头按钮的容器
        JTextArea jTextArea = new JTextArea(10, 10);//显示登录信息以及文件传输情况
        JScrollPane jscrollpane;//给文本框添加滚动条
        JButton position = new JButton("储存位置");
        JButton clear = new JButton("清空消息");

        clientWindow() {
            win = new JFrame("文件传输客户端");
            win.setSize(400, 500);
            win.setLocationRelativeTo(null);
            win.setVisible(false);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);

            p.add(position);
            p.add(clear);
            jscrollpane = new JScrollPane(jTextArea);

            jTextArea.setEditable(false);

            win.add(p, BorderLayout.NORTH);
            win.add(jscrollpane, BorderLayout.CENTER);
            win.setVisible(true);

            position.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fileWindow();
                }
            });

            clear.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jTextArea.setText("消息成功清空");
                }
            });
        }


        void print(String string) {
            jTextArea.append(string + "\r\n");
            p.updateUI();
            win.repaint();
        }
        void fileWindow(){
            JFileChooser jfc=new JFileChooser(rootpath);
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.showDialog(new JLabel(), "选择文件储存目录");
            jfc.setVisible(true);
            try{
                rootpath=jfc.getSelectedFile().getAbsolutePath();
                File file=new File(rootpath);
                if(file.isDirectory()&&file.exists()){}
                else file.mkdir();
            }catch (Exception e){}
            print("当前文件储存目录为:"+rootpath);

        }
    }
    public static void main(String[] args) {
        try{
            new myClient();
        }catch (Exception em){
            em.printStackTrace();
        }
    }
}