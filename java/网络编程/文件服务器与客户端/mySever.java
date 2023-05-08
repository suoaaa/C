package 网络编程.文件服务器与客户端;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import javax.swing.*;

public class mySever {

    static severWindow severwindow;
    String rootpath="E:\\个人编程\\代码-全\\java\\网络编程\\文件储存目录";
    ExecutorService pool;
    ServerSocket s;
    Socket socket;

    mySever() throws Exception {
        severwindow = new severWindow();
        s = new ServerSocket(5050,5);       //限定最多同时登录5名用户
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
            try {
                InputStream i = s.getInputStream();
                String path=rootpath;
                severwindow.print("IP为"+s.getInetAddress().getHostAddress()+"的用户已连接服务器");
                byte[] b = new byte[128];
                boolean flag=true;
                while(flag){
                    i.read(b,0,128);
                    String string=new String(b);
                    switch (string.charAt(0)){
                        case '1':   upload(string);
                            break;
                        case '2':   download(string);
                            break;
                        case '3':   create(string);
                            break;
                        case '4':   deldir(string);
                            break;
                        case '5':   delfile(string);
                            break;
                    }
                }
            } catch (Exception e) {
                severwindow.print("IP为"+s.getInetAddress().getHostAddress()+"的用户已断开连接");
            }
        }
        void upload(String string){}

        void download(String string){}

        void create(String string){
            String thispath="";
            int n=string.length()-1;
            String  num="";
            while(string.charAt(n)!='0'){
                num=string.charAt(n)+num;
                n--;
            }
            n=string.length()-Integer.parseInt(num);
            try {
                BufferedOutputStream o = new BufferedOutputStream(s.getOutputStream());
                if(n<=1) {
                    o.write("2".getBytes());
                } else{
                    thispath = string.substring(1, n);
                    File file = new File(rootpath + "\\" + thispath);
                    if (file.exists()) {
                        o.write("0".getBytes());
                    } else {
                        if (file.mkdir()) {
                            o.write("1".getBytes());
                        } else {
                            o.write("2".getBytes());
                        }
                    }
                }
                o.flush();
            } catch (Exception ignored) {}
        }
    }

    List<String> showDir(){
        File dir = new File(rootpath);
        List<String> allFileList = new ArrayList<>();

        // 判断文件夹是否存在
        if (!dir.exists()) {
            System.out.println("目录不存在");
            dir.mkdir();
        }
        getAllFile(dir, allFileList,0);
        for(String string:allFileList){
            severwindow.print(string);
        }
        return allFileList;
    }
    public static void getAllFile(File fileInput, List<String> allFileList,int n) {
        // 获取文件列表
        File[] fileList = fileInput.listFiles();
        assert fileList != null;
        String string="";
        for(int  i=0;i<n;i++){
            string+="  ";
        }
        for (File file : fileList) {
            if (file.isDirectory()) {
                // 递归处理文件夹
                // 如果不想统计子文件夹则可以将下一行注释掉
                allFileList.add(string+file.getName());
                getAllFile(file, allFileList,n+1);
            } else {
                // 如果是文件则将其加入到文件数组中
                allFileList.add(string+file.getName());
            }
        }
    }



    void deldir(String string){}

    void delfile(String string){}

    class severWindow {
        JFrame win=new JFrame("文件传输系统服务器");
        JPanel p=new JPanel();         //p作为开头按钮的容器
        JTextArea jTextArea=new JTextArea(10,10);//显示登录信息以及文件传输情况
        JScrollPane jscrollpane;//给文本框添加滚动条

        JButton position=new JButton("储存位置");
        JButton clear=new JButton("清空消息");
        severWindow(){
            win.setSize(400,500);               //这一段是初始化窗口
            win.setLocationRelativeTo(null);
            win.setVisible(false);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);
            print("服务器已启动，等待链接");

            p.add(position);
            p.add(clear);
            jscrollpane =new JScrollPane(jTextArea);
            jTextArea.setEditable(false);

            win.add(p, BorderLayout.NORTH);
            win.add(jscrollpane,BorderLayout.CENTER);
            win.setVisible(true);
            position.addActionListener(new ActionListener() {       //给储存位置按钮增加监听
                public void actionPerformed(ActionEvent e) {
                    fileWindow();
                }
            });

            clear.addActionListener(new ActionListener() {      //给清空消息按钮添加监听
                public void actionPerformed(ActionEvent e) {
                    jTextArea.setText("消息成功清空\r\n");
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
            new mySever();
        }catch(SocketException e3){
            mySever.severwindow.print("用户断开链接");
        }
        catch (Exception e2){
//            mySever.severwindow.print("用户断开链接");
            e2.printStackTrace();
        }
    }
}
