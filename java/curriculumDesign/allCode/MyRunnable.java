package curriculumDesign.allCode;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MyRunnable implements Runnable {
    //该类继承Runnable类，在服务端调用，每有一个新的客户端请求调用，便新建本类加入线程池
    Socket s;       //与客户端建立链接
    String nowPath; //记录客户端目前在目录的位置
    String downName;//记录客户端申请下载的文件名称
    String rootpath;//记录服务器对本次链接提供的文件储存根目录，服务器更改后不影响已连接的客户端
    JTextArea jTextArea;//服务器窗口的对应JTextField
    MyRunnable(Socket s,String rootpath,JTextArea jTextArea) {
        this.s = s;
        this.rootpath=rootpath;
        nowPath = rootpath;
        this.jTextArea=jTextArea;
    }

    public void run() {
        try {
            MyStreamMethod.print(jTextArea,"IP为" + s.getInetAddress().getHostAddress() + "的用户已连接服务器");
            byte []b;
            while (true) {
                b= MyStreamMethod.receive(new byte[128],s);
                if(b==null) {
                    s.close();
                    MyStreamMethod.print(jTextArea,"IP为" + s.getInetAddress().getHostAddress() + "的用户已断开连接");
                    return;
                }
                switch (b[0]) {                     //根据接收到的比特流的0索引，配合客户端实现不同的功能
                    case '0' -> showDir();          //展示目录中文件
                    case '1' -> upload(b);          //上传文件
                    case '2' -> download(b);        //下载文件
                    case '3' -> last();             //进入上一级
                    case '4' -> create(b);          //创建新文件夹在当前文件夹
                    case '5' -> del(b);             //删除文件/文件夹
                    case '6' -> cd(b);              //进入特定文件/预览文件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyStreamMethod.print(jTextArea,"IP为" + s.getInetAddress().getHostAddress() + "的用户已断开连接");
        }
    }

    void showDir () {
        //展示文件，首先发送文件数
        //再循环发送文件名称
        File dir = new File(nowPath);
        if(!dir.exists()) if(!dir.mkdirs()) {
            MyStreamMethod.send("0".getBytes(), s);
            return;
        }
        List<String> allFileList = new ArrayList<>();
        MyStreamMethod.getAllFile(dir, allFileList, 0);
        MyStreamMethod.send((""+allFileList.size()).getBytes(),s);
        MyStreamMethod.send(dir.getName().getBytes(),s);
        for (String value : allFileList) {
            MyStreamMethod.send(value.getBytes(),s);
        }
        MyStreamMethod.print(jTextArea,"IP为"+s.getInetAddress()+"的用户遍历了文件夹");
    }

    void upload (byte[] b){
        //接收文件
        MyStreamMethod.receive(b,s, nowPath);
        MyStreamMethod.print(jTextArea,"IP为"+s.getInetAddress()+"的用户上传了新的文件");
    }

    void download (byte[] b){
        //为客户端发送特定名称的文件或者文件夹
        //第一次接收后判断b[1]，判断第一次或第二次请求
        //第一次请求判断是否有该文件夹或者文件
        //第二次请求发送文件
        switch (b[1]) {
            case '1' -> {
                downName = new String(b, 2, b.length - 2);
                File file = new File(nowPath);
                String[] strings = file.list();
                if (strings!=null&&strings.length > 0) for (String all : strings) {
                    if (all.equals(downName)) {
                        MyStreamMethod.send("11".getBytes(), s);
                        return;
                    }
                }
                if (downName.equals(file.getName())) MyStreamMethod.send("12".getBytes(), s);
                MyStreamMethod.send("0".getBytes(), s);
            }
            case '2' -> {
                switch (b[2]) {
                    case '1' -> MyStreamMethod.send(new File(nowPath + "\\" + downName), s);
                    case '2' -> MyStreamMethod.send(new File(nowPath), s);
                }
            }
        }
        MyStreamMethod.print(jTextArea,"IP为"+s.getInetAddress()+"的用户正在下载文件");
    }

    void last(){
        //进入上一级
        //发送上一次文件夹名称给客户端
        File file=new File(nowPath);
        if(nowPath.contains(rootpath)){
            if(rootpath.equals(nowPath)){
                MyStreamMethod.send("0".getBytes(),s);
            }else{
                nowPath = file.getParent();
                String string= nowPath.substring(rootpath.length());
                MyStreamMethod.send(("1"+string).getBytes(),s);
                MyStreamMethod.print(jTextArea,"IP为"+s.getInetAddress()+"的用户返回文件夹：根目录\\"+string);
            }
        }
    }

    void create (byte[] b){
        //根据b中内容新建文件
        String thisPath = new String(b,1,b.length-1);
        int n = b.length - 1;
        try {
            if (n < 1) {
                MyStreamMethod.send("2".getBytes(),s);
            } else {
                File file = new File(nowPath + "\\" + thisPath);
                if (file.exists()) {
                    MyStreamMethod.send("0".getBytes(),s);
                } else {
                    if (file.mkdirs()) {
                        MyStreamMethod.send("1".getBytes(),s);
                    } else {
                        MyStreamMethod.send("2".getBytes(),s);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        MyStreamMethod.print(jTextArea,"IP为"+s.getInetAddress()+"的用户正在新建文件夹");
    }

    void del(byte[] b){
        //删除特定文件/文件夹，并发送反馈
        String string=new String(b,1,b.length-1);
        File file=new File(nowPath +"\\"+string);
        if(file.exists()){
            if(file.isDirectory()){
                if(delAllFile(file)) string="1";
                else string="2";
                MyStreamMethod.send(string.getBytes(),s);
            }else {
                if(file.delete()){
                    MyStreamMethod.send("0".getBytes(),s);
                }else {
                    MyStreamMethod.send("2".getBytes(),s);
                }
            }
        }else{
            file=new File(nowPath);
            if(string.equals(file.getName())){
                if(delAllFile(file)) string="1";
                else string="2";
                MyStreamMethod.send(string.getBytes(),s);
            }else{
                MyStreamMethod.send("4".getBytes(),s);
            }
        }
        MyStreamMethod.print(jTextArea,"IP为"+s.getInetAddress()+"的用户正在删除文件");
    }

    void cd(byte[] b){
        //进入下一级发送反馈
        String name=new String(b,1,b.length-1);
        name= nowPath +"\\"+name;
        File file=new File(name);
        String string=name.substring(rootpath.length());
        if(!new File(nowPath).exists()){
            MyStreamMethod.send("3".getBytes(),s);
            nowPath =rootpath;
            return;
        }
        if(!file.exists()){
            MyStreamMethod.send("0".getBytes(),s);
        }else{
            nowPath =name;
            if(file.isDirectory()){
                MyStreamMethod.send(("1"+string).getBytes(),s);
                MyStreamMethod.print(jTextArea,"IP为"+s.getInetAddress()+"的用户进入文件夹："+string);
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
                    MyStreamMethod.send(by,s);
                    MyStreamMethod.print(jTextArea,"IP为"+s.getInetAddress()+"的用户预览文件："+string);
                }catch (Exception ie){ie.printStackTrace();}
            }
        }
    }

    boolean delAllFile(File file){
        //删除文件夹方法调用子方法
        boolean ret = true;
        File[] arr;
        arr=file.listFiles();
        for(int i = 0; i< Objects.requireNonNull(arr).length; i++){
            if(arr[i].isDirectory()) ret=ret& delAllFile(arr[i]);
            else ret=ret&arr[i].delete();
        }
        if(!file.delete()){
            String path= file.getPath().substring(rootpath.length());
            MyStreamMethod.print(jTextArea,"文件"+path+"删除失败");
        }
        return ret;
    }

}