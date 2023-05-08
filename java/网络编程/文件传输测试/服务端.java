package 网络编程.文件传输测试;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import javax.swing.*;

public class 服务端 {

    String rootpath="E:\\个人编程\\代码-全\\java\\网络编程\\文件储存目录";
    static severWindow severwindow;
    ExecutorService pool;
    ServerSocket s;
    Socket socket;

    服务端() throws Exception {
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
        myRunnable(Socket s){this.s=s;path=rootpath;}
        public void run() {
            try (InputStream o = s.getInputStream()) {
                severwindow.print("IP为"+s.getInetAddress().getHostAddress()+"的用户已连接服务器");
                byte[] b = new byte[1024];
                int n=0,i=0;
                do{
                    n=o.read();
                    b[i]= (byte) n;
                    i++;
                }while(n!=0);
                System.out.println();
                System.out.println(new String(b,0,i-1));
                severwindow.print(new String(b));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class severWindow {
        JFrame win=new JFrame("文件传输系统服务器");
        JPanel p=new JPanel();         //p作为开头按钮的容器
        JTextArea jTextArea=new JTextArea(10,10);//显示登录信息以及文件传输情况
        JScrollPane jscrollpane;//给文本框添加滚动条

        JButton position=new JButton("储存位置");
        JButton clear=new JButton("清空消息");
        JButton reopen=new JButton("重启服务器");
        severWindow(){
            win.setSize(400,500);
            win.setLocationRelativeTo(null);
            win.setVisible(false);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);
            print("服务器已启动，等待链接");

            p.add(position);
            p.add(clear);
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

            clear.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jTextArea.setText("消息成功清空");
                }
            });

            reopen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try{
                        if(s.isClosed()){
                            s = new ServerSocket(5050,5);       //限定最多同时登录5名用户
                        }else{
                            severwindow.print("服务器正在运行中,无需重启");
                        }
                    }catch (Exception ie){
                        ie.printStackTrace();
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
            severwindow.print("当前文件储存目录为:"+rootpath);

        }
    }

    public static void main(String[] args) {
        try {
            new 服务端();
        } catch (SocketTimeoutException e1) {
            服务端.severwindow.print("长时间未有用户链接服务器，服务器已自动关闭");
        }catch(SocketException e3){
            System.out.println("用户断开链接");
        }
        catch (Exception e2){
            e2.printStackTrace();
        }
    }
}
