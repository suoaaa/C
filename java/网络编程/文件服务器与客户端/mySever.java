package 网络编程.文件服务器与客户端;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import javax.swing.*;

public class mySever {

    String rootpath="E:\\个人编程\\代码-全\\java";
    ExecutorService pool;
    ServerSocket s;
    Socket socket;

    mySever() throws IOException{
        new severWindow();
        s = new ServerSocket(5000);
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
        myRunnable(){};
        public void run() {
            try (InputStream o = s.getInputStream()) {
                byte[] b = new byte[1024];
                o.read(b);
                System.out.println(new String(b));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class severWindow {
        JFrame win;
        severWindow(){
            // fileWindow();
            

        }

        public void fileWindow(){
            JFileChooser jfc=new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    jfc.showDialog(new JLabel(), "选择文件储存根目录");
            jfc.setVisible(true);
		    rootpath=jfc.getSelectedFile().getAbsolutePath();
        }

        public void mainWindow(){
            win=new JFrame("文件传输系统客户端");
            win.setVisible(true);
            win.setLocale(null);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel p1=new JPanel();         win.add(p1);
            JPanel p2=new JPanel();         win.add(p2);
            JPanel p3=new JPanel();         win.add(p3);
            JPanel p4=new JPanel();         win.add(p4);
        }
    }
    public static void main(String[] args) {
        try {
            new mySever();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
