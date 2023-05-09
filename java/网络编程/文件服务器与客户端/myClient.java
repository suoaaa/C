package 网络编程.文件服务器与客户端;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class myClient {

    InetAddress i;
    Socket s;
    String rootpath = "E:\\桌面";
    String downpath = rootpath;
    String uppath = rootpath;
    clientWindow clientwindow=new clientWindow();
    myClient(){
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
        JFrame clientWindow = new JFrame("文件传输系统客户端");
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JTextArea jTextArea = new JTextArea(10, 10);//显示登录信息以及文件传输情况
        JTextField jTextField=new JTextField();//用户可以输入指令
        JScrollPane jscrollpane;//给文本框添加滚动条
        JButton position = new JButton("遍历文件");
        JButton upload = new JButton("上传文件");
        JButton download = new JButton("下载文件");
        JButton last = new JButton("上一级");
        JButton deldir = new JButton("删除文件夹");
        JButton delfile = new JButton("删除文件");
        JButton create = new JButton("创建文件夹");
        JButton cd = new JButton("进入");

        clientWindow() {
            clientWindow = new JFrame("文件传输客户端");
            clientWindow.setSize(400, 500);
            clientWindow.setLocationRelativeTo(null);
            clientWindow.setVisible(false);
            clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            clientWindow.setResizable(false);

            p1.setLayout(new GridLayout(2,4));
            p1.add(position);
            p1.add(upload);
            p1.add(download);
            p1.add(last);
            p1.add(create);
            p1.add(deldir);
            p1.add(delfile);
            p1.add(cd);
            p2.setLayout(new GridLayout(2,1));
            p2.add(new Label("输入地址/指令："));
            p2.add(jTextField);

            jscrollpane = new JScrollPane(jTextArea);
            jTextArea.setEditable(false);

            clientWindow.add(p1, BorderLayout.NORTH);
            clientWindow.add(p2, BorderLayout.SOUTH);
            clientWindow.add(jscrollpane, BorderLayout.CENTER);
            clientWindow.setVisible(true);

            position.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String name;
                    name=jTextField.getText();
                    jTextField.setText("");
                    byte []b=("0"+name).getBytes();
                    send(b);
                    //接收反馈指令
                    b=receive(new byte[128]);
                    int n=Integer.parseInt(new String(b));
                    if(n==0)print("当前文件夹为空");
                    else{
                        print("即将遍历当前文件夹：");
                        for(int i=0;i<n;i++){
                            b=receive(new byte[128]);
                            print(new String(b));
                        }
                    }
                }
            });

            upload.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    print("1");
            }});

            download.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                print("1");
            }});

            create.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String name;
                    name=jTextField.getText();
                    jTextField.setText("");
                    if(name.getBytes().length>0) {           //这部分写新建指令发送给服务区的比特流
                        byte []b=("3"+name).getBytes();
                        send(b);
                        //接收反馈指令
                        b=new byte[128];
                        b=receive(b);
                        switch (b[0]){
                            case '0' : print("存在同名文件夹");    break;
                            case '1' : print("创建成功");         break;
                            case '2' : print("创建失败");         break;
                        }
                    }
                    else {
                        print("请输入文件名并重新点击");
                    }
                }
            });

            deldir.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                print("1");
            }});

            delfile.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                print("1");
            }});

            last.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jTextField.setText("");
                    send(("6").getBytes());
                    //接收反馈指令
                    byte[] b=new byte[128];
                    b=receive(b);
                    switch (b[0]){
                        case '0' : print("返回失败，已是根目录");    break;
                        case '1' : {
                            print("返回成功，当前目录：根目录"+new String(b,1,b.length-1));
                            break;
                        }
                    }
                }});

            cd.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    String name;
                    name=jTextField.getText();
                    jTextField.setText("");
                    if(name.getBytes().length>0) {           //这部分写新建指令发送给服务区的比特流
                        byte []b=("7"+name).getBytes();
                        send(b);
                        //接收反馈指令
                        b=receive(new byte[128]);
                        switch (b[0]){
                            case '0' : print("无此文件夹");    break;
                            case '1' : print("进入文件夹:根目录"+new String(b,1,b.length-1));         break;
                            case '2' : {
                                print("进入文件，以下为预览内容：");
                                System.out.println(new String(b));
                                print(new String(b,1,b.length-1));
                                break;
                            }
                        }
                    }
                    else {
                        print("请输入文件名并重新点击");
                    }
            }});

        }

        void print(String string) {
            jTextArea.append(string + "\r\n");
            clientWindow.repaint();
        }

        void send(byte[] b){
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
                String string = new String(b);
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

        String fileWindow(int n){
            JFileChooser jfc=new JFileChooser(rootpath);
            switch (n){
                case 1:jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);break;
                case 2:jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);break;
                default:jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            }
            jfc.showDialog(new JLabel(), "选择文件");
            jfc.setVisible(true);
            String path=rootpath;
            try{
                path=jfc.getSelectedFile().getAbsolutePath();
                File file=new File(rootpath);
                if(!file.isDirectory()||!file.exists())file.mkdir();
            }catch (Exception ignored){}
            print("已选择文件/文件夹:"+rootpath);
            return path;
        }

        String newTextWindow(String s){
            String instruct="";
            JFrame jFrame=new JFrame(s);
            return instruct;
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