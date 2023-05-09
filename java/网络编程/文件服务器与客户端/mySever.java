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
    String rootpath;
    String nowpath;
    ExecutorService pool;
    ServerSocket s;
    Socket socket;

    mySever() throws Exception {
        rootpath="E:\\个人编程\\代码-全\\java\\网络编程\\文件储存目录";
        nowpath=rootpath;
        severwindow = new severWindow();
        s = new ServerSocket(5050,5);       //限定最多同时登录5名用户
        pool = Executors.newCachedThreadPool();
        while(true){
            socket = s.accept();
            pool.execute(new myRunnable(socket));
        }
    }

    class myRunnable implements Runnable {
        Socket s;
        String path;

        myRunnable(Socket s) {
            this.s = s;
            path = rootpath;
        }

        public void run() {
            try {
                severwindow.print("IP为" + s.getInetAddress().getHostAddress() + "的用户已连接服务器");
                while (true) {
                    byte[] b= new byte[128];
                    b=receive(b);
                    switch (b[0]) {
                        case '0':
                            showDir(nowpath);      //已实现
                            break;
                        case '1':
                            upload(b);     //未实现
                            break;
                        case '2':
                            download(b);   //未实现
                            break;
                        case '3':
                            create(b);     //已实现
                            break;
                        case '4':
                            deldir(b);     //未实现
                            break;
                        case '5':
                            delfile(b);    //未实现
                            break;
                        case '6':
                            last();    //已实现
                            break;
                        case '7':
                            cd(b);    //已实现
                            break;
                    }
                }
            } catch (Exception e) {
                    e.printStackTrace();
                severwindow.print("IP为" + s.getInetAddress().getHostAddress() + "的用户已断开连接");
            }

        }

        void showDir (String path) {
            File dir = new File(path);
            List<String> allFileList = new ArrayList<>();
            getAllFile(dir, allFileList, 0);
            int n = allFileList.size();
            byte[] b = ("" + n).getBytes();
            System.out.println(new String(b));
            send(b);
            for(int i=0;i<n;i++){
                send(allFileList.get(i).getBytes());
            }
        }
        void upload (byte[] b){
        }

        void download (byte[] b){
        }

        void create (byte[] b){
            String thispath = new String(b,1,b.length-1);
            int n = b.length - 1;
            try {
                if (n < 1) {
                    send("2".getBytes());
                } else {
                    File file = new File(nowpath + "\\" + thispath);
                    if (file.exists()) {
                        send("0".getBytes());
                    } else {
                        if (file.mkdir()) {
                            send("1".getBytes());
                        } else {
                            send("2".getBytes());
                        }
                    }
                }
            } catch (Exception ex) {ex.printStackTrace();
            }
        }

        void deldir (byte[] b){
        }

        void delfile (byte[] b){
        }

        void last(){
            if(rootpath.length()>=nowpath.length()){
                send("0".getBytes());
            }else {
                File file=new File(nowpath);
                nowpath = file.getParent();
                String string=nowpath.substring(rootpath.length());
                send(("1"+string).getBytes());
                severwindow.print(file.getParent());
            }

        }
        void cd(byte[] b){
            String name=new String(b,1,b.length-1);
            name=nowpath+"\\"+name;
            File file=new File(name);
            if(!file.exists()){
                send("0".getBytes());
            }else{
                if(file.isDirectory()){
                    nowpath=name;
                    send(("1"+(nowpath.substring(rootpath.length()))).getBytes());
                }else{
                    BufferedInputStream i;
                    try{
                        i=new BufferedInputStream(new FileInputStream(file));
                        int n;
                        if(file.length()>2147483647) n=124;
                        else n=(int) file.length();
                        byte []by = new byte[n];
                        i.read(by,1,n-2);
                        by[0]='2';
                        send(by);
                    }catch (Exception ie){ie.printStackTrace();}
                }
            }
        }

        public static void getAllFile (File fileInput, List < String > allFileList,int n){
            // 获取文件列表
            File[] fileList = fileInput.listFiles();
            assert fileList != null;
            String string = "";
            for (int i = 0; i < n; i++) {
                string += "  ";
            }
            for (File file : fileList) {
                if (file.isDirectory()) {
                    // 递归处理文件夹
                    allFileList.add(string + file.getName() + "\\");
                    getAllFile(file, allFileList, n + 1);
                } else {
                    // 如果是文件则将其加入到文件名数组中
                    allFileList.add(string + file.getName());
                }
            }
        }

        void send(byte[] b){        //把未足128比特的数组拓展发送给接收方
            try{
                BufferedOutputStream o = new BufferedOutputStream(s.getOutputStream());
                o.write(b);
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
        byte[] receive(byte[] b){   //接收128比特的数组并分析出需要的数据
            byte[] ret=new byte[128];
            try{
                InputStream in = s.getInputStream();
                in.read(b);
                int i=b.length-1;
                String num="";
                while(b[i]!='@'){
                    num=(char)b[i]+""+num;
                    i--;
                }
                int n=Integer.parseInt(num);
                ret=new byte[128-n];
                for(i=0;i<128-n;i++){
                    ret[i]=b[i];
                }

            }catch (Exception ignored){}
            return ret;
        }
    }


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
            rootpath=jfc.getSelectedFile().getPath();
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
