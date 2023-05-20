package allCode;

import javax.swing.*;
import java.io.File;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class MyActionMethod {
    //这个类创建目的是减少客户端与服务端中ActionListener的actionPerformed方法中的代码量
    //其中大部分为客户端需要
    //当监听到用户按下按钮时，actionPerformed方法调用本工具类中对应的成员方法
    //基本本类中的成员方法都要用到客户端的相关数据，需要参数传递

    private MyActionMethod(){}

    public static void show  (Socket s, JTextArea jTextArea,JTextField jTextField) throws InterruptedException {
        //客户端调用，向服务端申请遍历文件夹
        //接收服务端发送的文件数量，并循环打印遍历文件夹
        String name;
        name=jTextField.getText();
        jTextField.setText("");
        byte []b=("0"+name).getBytes();
        MyStreamMethod.send(b,s);
        //接收反馈指令，获得文件数量
        b= MyStreamMethod.receive(new byte[128],s);
        if(b[0]=='0') {
            MyStreamMethod.print(jTextArea, "当前文件夹为空");
            return ;
        }
        int n=Integer.parseInt(new String(b));
        b= MyStreamMethod.receive(new byte[128],s);
        MyStreamMethod.print(jTextArea,"即将遍历当前文件夹："+new String(b));
        for(int i=0;i<n;i++){
            //循环打印文件名
            b= MyStreamMethod.receive(new byte[128],s);
            MyStreamMethod.print(jTextArea,new String(b));
        }
    }

    public static void upload  (Socket s,String rootpath){
        //客户端调用，上传本地文件到服务端
        //获取需要上传的文件，调用MyMethod工具类中的send（file）方法
        JFileChooser jfc = MyStreamMethod.fileWindow(1,rootpath);
        try{
            File[] file=jfc.getSelectedFiles();
            for(File f:file){
                MyStreamMethod.send(f,s);
            }
        }catch (Exception ex){ex.printStackTrace();}
    }

    public static void download  (Socket s,JTextArea jTextArea,JTextField jTextField,String rootpath){
        //客户端调用，下载服务端文件到本地，先调取JTextField中输入的文件名，传向服务器
        //如果存在，循环接收服务器发出的file文件并保存在本地
        byte []b;
        String name;
        name=jTextField.getText();
        jTextField.setText("");
        if(name.isEmpty()){
            MyStreamMethod.print(jTextArea,"请输入文件夹名称后重试");
            return;
        }else{
            MyStreamMethod.send(("21"+name).getBytes(),s);
            b=MyStreamMethod.receive(new byte[128],s);
            if (b[0] == '0') {
                MyStreamMethod.print(jTextArea, "服务器中当前目录无此文件或文件夹，请重试");
                return;
            }
        }
        JFileChooser jfc = MyStreamMethod.fileWindow(2,rootpath);
        try{//获取客户端需要文件的下载保存地址
            //循环下载文件
            File file=jfc.getSelectedFile();
            if(file==null)  file=new File(rootpath);
            MyStreamMethod.print(jTextArea,"当前文件下载地址：");
            MyStreamMethod.print(jTextArea,file.getPath());
            MyStreamMethod.send(("22"+(char)b[1]).getBytes(),s);
            MyStreamMethod.receive(MyStreamMethod.receive(new byte[128],s),s,file.getPath());
            List<String> allFileList = new ArrayList<>();
            MyStreamMethod.getAllFile(file,allFileList,0);
            MyStreamMethod.print(jTextArea,"即将遍历下载地址文件夹：");
            for(String string:allFileList){
                MyStreamMethod.print(jTextArea,string);
            }
            MyStreamMethod.print(jTextArea,"遍历结束");
        }catch (Exception ex){ex.printStackTrace();}
    }

    public static void last  (Socket s,JTextArea jTextArea,JTextField jTextField){
        //客户端调用，使服务端对应的所在地址向上一级
        //如果已在根目录，不会向上
        jTextField.setText("");
        MyStreamMethod.send(("3").getBytes(),s);
        byte[] b;
        b= MyStreamMethod.receive(new byte[128],s);
        switch (b[0]) {
            case '0' -> MyStreamMethod.print(jTextArea, "返回失败，已是根目录");
            case '1' -> MyStreamMethod.print(jTextArea, "返回成功，当前目录：根目录" + new String(b, 1, b.length - 1));
        }
    }

    public static void clear(JTextArea jTextArea){
        //双方都可调用，清空TTextArea中的内容
        jTextArea.setText("消息成功清空\r\n");
    }


    public static void create  (Socket s,JTextArea jTextArea,JTextField jTextField){
        //客户端调用，向服务端发送申请，再服务端创建以JTextField中内容为名的文件夹
        String name;
        name=jTextField.getText();
        jTextField.setText("");
        if(name.getBytes().length>0) {           //这部分写新建指令发送给服务区的比特流
            byte []b=("4"+name).getBytes();
            MyStreamMethod.send(b,s);
            //接收反馈指令
            b=new byte[128];
            b= MyStreamMethod.receive(b,s);
            switch (b[0]) {
                case '0' -> MyStreamMethod.print(jTextArea, "存在同名文件夹");
                case '1' -> MyStreamMethod.print(jTextArea, "创建成功");
                case '2' -> MyStreamMethod.print(jTextArea, "创建失败");
            }
        }
        else {
            MyStreamMethod.print(jTextArea,"请输入文件名并重新点击");
        }
    }

    public static void del  (Socket s,JTextArea jTextArea,JTextField jTextField){
        //客户端调用，向服务端发出申请，删除以JTextField内容为名，在服务端储存的文件或者文件夹
        String name;
        name=jTextField.getText();
        if(name.isEmpty()) MyStreamMethod.print(jTextArea,"请输入文件名并重新点击");
        else {
            jTextField.setText("");
            byte []b=("5"+name).getBytes();
            MyStreamMethod.send(b,s);
            //接收反馈指令
            b= MyStreamMethod.receive(new byte[128],s);
            switch (b[0]) {
                case '0' -> MyStreamMethod.print(jTextArea, "删除文件：" + name + "成功");
                case '1' -> MyStreamMethod.print(jTextArea, "删除文件夹：" + name + "成功");
                case '2' -> MyStreamMethod.print(jTextArea, "删除：" + name + "失败，目录中存在文件被占用");
                case '3' -> MyStreamMethod.print(jTextArea, "删除失败：此目录下" + name + "不存在");
            }
        }
    }

    public static void cd  (Socket s,JTextArea jTextArea,JTextField jTextField){
        //客户端调用，申请进入服务端以JTextField内容为名的文件夹或者预览文件内容
        String name;
        name=jTextField.getText();
        jTextField.setText("");
        if(name.getBytes().length>0) {           //这部分写新建指令发送给服务区的比特流
            byte []b=("6"+name).getBytes();
            MyStreamMethod.send(b,s);
            //接收反馈指令
            b= MyStreamMethod.receive(new byte[128],s);
            switch (b[0]) {
                case '0' -> MyStreamMethod.print(jTextArea, "无此文件夹");
                case '1' -> MyStreamMethod.print(jTextArea, "进入文件夹:根目录" + new String(b, 1, b.length - 1));
                case '2' -> {
                    MyStreamMethod.print(jTextArea, "无法确定该文件编码格式，以下两段其中一段为正确预览内容：");
                    MyStreamMethod.print(jTextArea,"------------开始预览，以下为GBK格式：----------------");
                    MyStreamMethod.print(jTextArea, new String(b, 1, b.length - 1, Charset.forName("GBK")));
                    MyStreamMethod.print(jTextArea,"-------------------以下为utf-8格式：----------------");
                    MyStreamMethod.print(jTextArea, new String(b, 1, b.length - 1, StandardCharsets.UTF_8));
                }
                case '3' -> MyStreamMethod.print(jTextArea, "文件类型不支持预览");
            }
        }
        else {
            MyStreamMethod.print(jTextArea,"请输入文件名并重新点击");
        }
    }

    public static void position(JTextArea jTextArea,String rootpath){
        //服务端调用，更改服务端根目录的位置，已连接服务器的客户端不受影响
        JFileChooser jfc = MyStreamMethod.fileWindow(2,rootpath);
        File file= jfc.getSelectedFile();
        if(file!=null&&file.exists()) {
            MyStreamMethod.print(jTextArea,"更新服务器储存目录成功，当前文件夹根目录为：");
            rootpath = file.getPath();
        }else   MyStreamMethod.print(jTextArea,"更新服务器储存目录失败，当前文件夹根目录为：");
        MyStreamMethod.print(jTextArea,rootpath);
    }

}
