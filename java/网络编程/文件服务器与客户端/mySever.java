package 网络编程.文件服务器与客户端;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import javax.swing.*;

public class mySever {

    String rootpath="E:\\个人编程\\代码-全\\java";
    severWindow severwindow;
    ExecutorService pool;
    ServerSocket s;
    Socket socket;

    mySever() throws Exception {
        severwindow = new severWindow();
        s = new ServerSocket(5050,5);       //限定最多同时登录5名用户
        s.setSoTimeout(60*1000*2);
        pool = Executors.newCachedThreadPool();
        while(true){
            socket = s.accept();
            pool.execute(new myRunnable(socket));
        }
    }

    class myRunnable implements Runnable{
        Socket s;
        String path;
        myRunnable(Socket s){this.s=s;}
        public void run() {
            try (InputStream o = s.getInputStream()) {
                severwindow.print("一个用户已连接");
                byte[] b = new byte[1024];
                o.read(b);
                System.out.println(new String(b));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class severWindow {
        JFrame win=new JFrame("文件传输服务器");
        JPanel p=new JPanel();         //p作为开头按钮的容器
        JTextArea jTextArea=new JTextArea(10,10);//显示登录信息以及文件传输情况
        JScrollPane jscrollpane;//给文本框添加滚动条

        JButton position=new JButton("储存位置");
        JButton close=new JButton("关闭服务器");
        JButton reopen=new JButton("重新开启服务器");
        severWindow(){
            win=new JFrame("文件传输系统客户端");
            win.setSize(400,500);
            win.setLocationRelativeTo(null);
            win.setVisible(false);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);
            print("服务器已启动，等待链接");

            p.add(position);
            p.add(close);
            p.add(reopen);
            jscrollpane =new JScrollPane(jTextArea);

            jTextArea.setEditable(false);

            win.add(p,BorderLayout.NORTH);
            win.add(jscrollpane,BorderLayout.CENTER);
            win.setVisible(true);
            position.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fileWindow();
                }
            });

            close.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(!s.isClosed()){
                        try{

                            print("已经成功关闭");
                        }catch (Exception ex){
                        }
                    }else{
                        print("请勿重复关闭");
                    }
                }
            });

            reopen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(s.isClosed()){
                        try{
                            s=new ServerSocket(5050);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }else{
                        print("服务器正在运行，请勿重复启动");
                    }
                }
            });
        }


        void print(String string){
            jTextArea.append(string+"\r\n");
            p.updateUI();
            win.repaint();
        }

        void fileWindow(){
            JFileChooser jfc=new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.showDialog(new JLabel(), "选择文件储存目录");
            jfc.setVisible(true);
            try{
                rootpath=jfc.getSelectedFile().getAbsolutePath();
            }catch (Exception e){}
            severwindow.print("当前文件储存目录为:"+rootpath);

        }
    }

    public static void main(String[] args) {
        try {
            new mySever();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
