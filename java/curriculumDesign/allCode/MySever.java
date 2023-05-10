package curriculumDesign.allCode;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import javax.swing.*;

    public class MySever {
    static severWindow severwindow;
    String rootpath;
    ExecutorService pool;
    ServerSocket s;
    Socket socket;

    MySever() throws Exception {
        rootpath=new File("").getAbsolutePath()+"\\java\\curriculumDesign\\severSave";
        severwindow = new severWindow();
        s = new ServerSocket(5050,3);       //限定最多同时登录3个ip
        pool = Executors.newCachedThreadPool();
        while(true){
            socket = s.accept();
            pool.execute(new myRunnable(socket));
        }
    }

    class myRunnable implements Runnable {

        Socket s;
        String nowpath;
        String downname;

        myRunnable(Socket s) {
            this.s = s;
            nowpath = rootpath;
        }

        public void run() {
            try {
                severwindow.print("IP为" + s.getInetAddress().getHostAddress() + "的用户已连接服务器");
                byte []b;
                while (true) {
                    b= MyMethod.receive(new byte[128],s);
                    if(b==null) {
                        s.close();
                        severwindow.print("IP为" + s.getInetAddress().getHostAddress() + "的用户已断开连接");
                        return;
                    }
                    switch (b[0]) {
                        case '0':
                            showDir();      //已实现
                            break;
                        case '1':
                            upload(b);          //已实现
                            break;
                        case '2':
                            download(b);        //已实现
                            break;
                        case '3':
                            last();             //已实现
                            break;
                        case '4':
                            create(b);          //已实现
                            break;
                        case '5':
                            delfile(b);         //已实现
                            break;
                        case '6':
                            cd(b);              //已实现
                            break;
                    }
                }
            } catch (Exception e) {
                    e.printStackTrace();
                severwindow.print("IP为" + s.getInetAddress().getHostAddress() + "的用户已断开连接");
            }
        }

        void showDir () {
            StringBuilder string=new StringBuilder();
            if(!isInRootpath()){
                string.append(0);
            }else string.append(1);
            File dir = new File(nowpath);
            List<String> allFileList = new ArrayList<>();
            MyMethod.getAllFile(dir, allFileList, 0);
            string.append(allFileList.size());
            MyMethod.send(string.toString().getBytes(),s);
            MyMethod.send(dir.getName().getBytes(),s);
            for (String value : allFileList) {
                MyMethod.send(value.getBytes(),s);
            }
            severwindow.print("IP为"+s.getInetAddress()+"的用户遍历了文件夹");
        }

        void upload (byte[] b){
            if(isInRootpath()){
                MyMethod.send("0".getBytes(),s);
            }else MyMethod.send("1".getBytes(),s);
            MyMethod.receive(b,s,nowpath);
            severwindow.print("IP为"+s.getInetAddress()+"的用户上传了新的文件");
        }

        void download (byte[] b){
            if(!isInRootpath()){
                MyMethod.send("2".getBytes(),s);
                return;
            }
            switch (b[1]){
                case '1':{
                    downname=new String(b,2,b.length-2);
                    File file=new File(nowpath);
                    String []string=file.list();
                    if(string.length>0) for(String all:string){
                        if(all.equals(downname)) {
                            MyMethod.send("11".getBytes(), s);
                            return;
                        }
                    }
                    if(downname.equals(file.getName())) MyMethod.send("12".getBytes(),s);
                    MyMethod.send("0".getBytes(),s);
                   break;
                }
                case '2':{
                    switch (b[2]){
                        case '1':
                            MyMethod.send(new File(nowpath+"\\"+downname),s);
                            break;
                        case '2':MyMethod.send(new File(nowpath),s);break;
                    }
                    break;
                }
            }
            severwindow.print("IP为"+s.getInetAddress()+"的用户正在下载文件");
        }

        void create (byte[] b){
            if(!isInRootpath()){
                MyMethod.send("3".getBytes(),s);
                return;
            }
            String thispath = new String(b,1,b.length-1);
            int n = b.length - 1;
            try {
                if (n < 1) {
                    MyMethod.send("2".getBytes(),s);
                } else {
                    File file = new File(nowpath + "\\" + thispath);
                    if (file.exists()) {
                        MyMethod.send("0".getBytes(),s);
                    } else {
                        if (file.mkdirs()) {
                            MyMethod.send("1".getBytes(),s);
                        } else {
                            MyMethod.send("2".getBytes(),s);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            severwindow.print("IP为"+s.getInetAddress()+"的用户正在新建文件夹");
        }

        void delfile (byte[] b){
            if(!isInRootpath()){
                MyMethod.send("3".getBytes(),s);
                return;
            }
            String string=new String(b,1,b.length-1);
            File file=new File(nowpath+"\\"+string);
            if(file.exists()){
                if(file.isDirectory()){
                    if(delAllfile(file)) string="1";
                    else string="2";
                    MyMethod.send(string.getBytes(),s);
                }else {
                    if(file.delete()){
                        MyMethod.send("0".getBytes(),s);
                    }else {
                        MyMethod.send("2".getBytes(),s);
                    }
                }
            }else{
                file=new File(nowpath);
                if(string.equals(file.getName())){
                    if(delAllfile(file)) string="1";
                    else string="2";
                    MyMethod.send(string.getBytes(),s);
                }else{
                    MyMethod.send("4".getBytes(),s);
                }
            }
            severwindow.print("IP为"+s.getInetAddress()+"的用户正在删除文件");
        }

        void last(){
            if (!isInRootpath()){
                MyMethod.send("3".getBytes(),s);
                return;
            }
            File file=new File(nowpath);
            if(nowpath.contains(rootpath)){
                if(rootpath.equals(nowpath)){
                    MyMethod.send("0".getBytes(),s);
                }else{
                    nowpath = file.getParent();
                    String string=nowpath.substring(rootpath.length());
                    MyMethod.send(("1"+string).getBytes(),s);
                    severwindow.print("IP为"+s.getInetAddress()+"的用户返回文件夹：根目录\\"+string);
                }
            }
        }

        void cd(byte[] b){
            String name=new String(b,1,b.length-1);
            name=nowpath+"\\"+name;
            File file=new File(name);
            String string=name.substring(rootpath.length());
            if(!new File(nowpath).exists()){
                MyMethod.send("3".getBytes(),s);
                nowpath=rootpath;
                return;
            }
            if(!file.exists()){
                MyMethod.send("0".getBytes(),s);
            }else{
                nowpath=name;
                if(file.isDirectory()){
                    MyMethod.send(("1"+string).getBytes(),s);
                    severwindow.print("IP为"+s.getInetAddress()+"的用户进入文件夹："+string);
                }else{
                    BufferedInputStream i;
                    try{
                        i=new BufferedInputStream(new FileInputStream(file));
                        int n;
                        if(file.length()>=124) n=124;
                        else n=(int) file.length();
                        byte []by = new byte[n];
                        i.read(by,1,n-2);
                        i.close();
                        by[0]='2';
                        MyMethod.send(by,s);
                        severwindow.print("IP为"+s.getInetAddress()+"的用户预览文件："+string);
                    }catch (Exception ie){ie.printStackTrace();}
                }
            }
        }

        boolean delAllfile(File file){
            boolean ret = true;
            File[] arr;
            arr=file.listFiles();
            for(int i = 0; i< Objects.requireNonNull(arr).length; i++){
                if(arr[i].isDirectory()) ret=ret&delAllfile(arr[i]);
                else ret=ret&arr[i].delete();
            }
            if(!file.delete()){
                String path= file.getPath().substring(rootpath.length());
                severwindow.print("文件"+path+"删除失败");
            }
            return ret;
        }

        boolean isInRootpath(){
            if(nowpath.contains(rootpath)){
                return true;
            }else {
                nowpath=rootpath;
                severwindow.print("IP为"+s.getInetAddress()+"的用户返回根目录");
                return false;
            }
        }

    }


    class severWindow {
        JFrame win=new JFrame("文件传输系统服务器");
        JPanel p=new JPanel();         //p作为开头按钮的容器
        JTextArea jTextArea=new JTextArea(10,10);//显示登录信息以及文件传输情况
        JScrollPane jscrollpane =new JScrollPane(jTextArea); //给文本框添加滚动条
        JButton position=new JButton("储存位置");
        JButton clear=new JButton("清空消息");
        severWindow(){
            win.setSize(400,500);               //这一段是初始化窗口
            win.setLocationRelativeTo(null);
            win.setVisible(false);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);

            p.add(position);
            p.add(clear);

            jTextArea.setEditable(false);
            print("服务器已启动，等待链接");
            win.add(p, BorderLayout.NORTH);
            win.add(jscrollpane,BorderLayout.CENTER);
            win.setVisible(true);

            position.addActionListener(new ActionListener() {       //给储存位置按钮增加监听
                public void actionPerformed(ActionEvent e) {
                    JFileChooser jfc = MyMethod.fileWindow(2,rootpath);
                    File file= jfc.getSelectedFile();
                    if(file!=null&&file.exists()) {
                        print("更新服务器储存目录成功，当前文件夹根目录为：");
                        rootpath = file.getPath();
                    }else   print("更新服务器储存目录失败，当前文件夹根目录为：");
                    print(rootpath);
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
    }

    public static void main(String[] args) {
        try {
            new MySever();
        }catch(Exception e3){
            MySever.severwindow.print("服务器断开链接");
        }
    }
}
