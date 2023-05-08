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
        JButton position = new JButton("储存位置");
        JButton upload = new JButton("上传文件");
        JButton download = new JButton("下载文件");
        JButton clear = new JButton("清空对话");
        JButton deldir = new JButton("删除文件夹");
        JButton delfile = new JButton("删除文件");
        JButton create = new JButton("创建文件夹");
//        JButton yes = new JButton("确认");
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
            p1.add(clear);
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
                    downpath=fileWindow(1);
                }
            });

            upload.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    try {
//                        BufferedInputStream filebufferinput=new BufferedInputStream(new FileInputStream(path+name));
//                        BufferedOutputStream bufferoutput=new BufferedOutputStream(s.getOutputStream());
//                        byte[] b=new byte[1024*3];
//                        int n=0,i=0;
//                        do{
//                            n=filebufferinput.read();
//                            b[i]= (byte) n;
//                            i++;
//                        }while(n!=0);
//                        bufferoutput.write("upload".getBytes());
//                        bufferoutput.write(b);
                    } catch (Exception ignored ) {}
            }});

            download.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                print("1");
            }});

            clear.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                print("1");
            }});

            create.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try{
                        String name;
                        BufferedOutputStream o;
                        o= new BufferedOutputStream(s.getOutputStream());
                        name=jTextField.getText();
                        jTextField.setText("");
                        if(name.getBytes().length>0) {           //这部分写新建指令发送给服务区的比特流
                            o.write("3".getBytes());
                            o.write(name.getBytes());
                            int n=128-1-name.getBytes().length;
                            int i=0;
                            for(int j=n;j>0;i++) j/=10;
                            for(;i<n;i++){
                                o.write("0".getBytes());
                            }
                            o.write((""+n).getBytes());
                            System.out.println(n);
                            o.flush();

                            byte []b=new byte[1];
                            BufferedInputStream in=new BufferedInputStream(s.getInputStream());
                            in.read(b,0,1);
                            String string=new String(b);
                            System.out.println(string);
                            System.out.println("aaa");
                            switch (string.charAt(0)){
                                case '0' : print("存在同名文件夹");    break;
                                case '1' : print("创建成功");         break;
                                case '2' : print("创建失败");         break;
                            }System.out.println("bb");
                        }

                        else {
                            print("请输入文件名并重新点击");
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
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

            cd.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                print("1");
            }});

//            void showdir(){
//
//            }
        }

        void showDir(){}


        void print(String string) {
            jTextArea.append(string + "\r\n");
            clientWindow.repaint();
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