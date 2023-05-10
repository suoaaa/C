package curriculumDesign.allCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class MyClient {

    InetAddress i;
    Socket s;
    String rootpath ;
    clientWindow clientwindow=new clientWindow();
    MyClient(){
        rootpath = new File("").getAbsolutePath()+"\\java\\curriculumDesign\\clientSave";
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
        JFrame clientWindow ;
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JTextArea jTextArea = new JTextArea(1, 1);//显示登录信息以及文件传输情况
        JTextField jTextField=new JTextField();//用户可以输入指令
        JScrollPane jscrollpane;        //给文本框添加滚动条
        JButton showdir = new JButton("遍历文件");
        JButton upload = new JButton("上传文件");
        JButton download = new JButton("下载文件");
        JButton last = new JButton("上一级");
        JButton clear = new JButton("清空对话");
        JButton create = new JButton("创建文件夹");
        JButton delfile = new JButton("删除文件");

        JButton cd = new JButton("进入");

        clientWindow() {
            clientWindow = new JFrame("文件传输客户端");
            clientWindow.setSize(400, 500);
            clientWindow.setLocationRelativeTo(null);
            clientWindow.setVisible(false);
            clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            clientWindow.setResizable(false);

            p1.setLayout(new GridLayout(2,4));
            p1.add(showdir);        //头比特:'0'
            p1.add(upload);         //头比特:‘1’
            p1.add(download);       //头比特:‘2’
            p1.add(last);           //头比特:‘3’
            p1.add(clear);          
            p1.add(create);         //头比特:‘4’
            p1.add(delfile);        //头比特:‘5’
            p1.add(cd);             //头比特:‘6’
            p2.setLayout(new GridLayout(2,1));
            p2.add(new Label("输入地址/指令："));
            p2.add(jTextField);

            jscrollpane = new JScrollPane(jTextArea);
            jTextArea.setLineWrap(true);
            jTextArea.setEditable(false);

            clientWindow.add(p1, BorderLayout.NORTH);
            clientWindow.add(p2, BorderLayout.SOUTH);
            clientWindow.add(jscrollpane, BorderLayout.CENTER);
            clientWindow.setVisible(true);

            showdir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String name;
                    name=jTextField.getText();
                    jTextField.setText("");
                    byte []b=("0"+name).getBytes();
                    MyMethod.send(b,s);
                    //接收反馈指令
                    b= MyMethod.receive(new byte[128],s);
                    int n=Integer.parseInt(new String(b));
                    if(n==0)print("当前文件夹为空");
                    else{
                        b= MyMethod.receive(new byte[128],s);
                        print("即将遍历当前文件夹："+new String(b));
                        for(int i=0;i<n;i++){
                            b= MyMethod.receive(new byte[128],s);
                            print(new String(b));
                        }
                    }
                }
            });

            upload.addActionListener(new ActionListener(){              //1
                public void actionPerformed(ActionEvent e) {
                    JFileChooser jfc = fileWindow(1);
                    try{
                        File[] file=jfc.getSelectedFiles();
                        for(File f:file){
                            MyMethod.send(f,s);
                        }
                    }catch (Exception ex){ex.printStackTrace();}
            }});

            download.addActionListener(new ActionListener(){         //2
                public void actionPerformed(ActionEvent e) {
                    byte []b;
                    String name;
                    name=jTextField.getText();
                    jTextField.setText("");
                    if(name.isEmpty()){
                        print("请输入文件夹名称后重试");
                        return;
                    }else{
                        MyMethod.send(("21"+name).getBytes(),s);
                        b=MyMethod.receive(new byte[128],s);
                        if (b[0]=='0'){
                            print("服务器中当前目录无此文件或文件夹，请重试");
                            return;
                        }
                    }
                    JFileChooser jfc = fileWindow(2);
                    try{
                        File file=jfc.getSelectedFile();
                        if(file==null)  file=new File(rootpath);
                        print("当前文件下载地址：");
                        print(file.getPath());
                        MyMethod.send(("22"+(char)b[1]).getBytes(),s);
                        MyMethod.receive(MyMethod.receive(new byte[128],s),s,file.getPath());
                        List<String> allFileList = new ArrayList<>();
                        MyMethod.getAllFile(file,allFileList,0);
                        print("即将遍历下载地址文件夹：");
                        int n=allFileList.size();
                        for(String string:allFileList){
                            print(string);
                        }
                    }catch (Exception ex){ex.printStackTrace();}
            }});

            create.addActionListener(new ActionListener() {          //4
                public void actionPerformed(ActionEvent e) {
                    String name;
                    name=jTextField.getText();
                    jTextField.setText("");
                    if(name.getBytes().length>0) {           //这部分写新建指令发送给服务区的比特流
                        byte []b=("4"+name).getBytes();
                        MyMethod.send(b,s);
                        //接收反馈指令
                        b=new byte[128];
                        b= MyMethod.receive(b,s);
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

            delfile.addActionListener(new ActionListener(){              //5
                public void actionPerformed(ActionEvent e) {
                    String name;
                    name=jTextField.getText();
                    if(name.isEmpty()) print("请输入文件名并重新点击");
                    else {
                        jTextField.setText("");
                        byte []b=("5"+name).getBytes();
                        MyMethod.send(b,s);
                        //接收反馈指令
                        b= MyMethod.receive(new byte[128],s);
                        switch (b[0]){
                            case '0' : print("删除文件：" + name + "成功"); break;
                            case '1' : print("删除文件夹：" + name + "成功"); break;
                            case '2' : print("删除：" + name + "失败，存在文件被占用");break;
                            case '4' : print("删除失败：此目录下文件或文件夹" + name + "不存在");break;
                        }
                    }
            }});

            last.addActionListener(new ActionListener() {                //3
                public void actionPerformed(ActionEvent e) {
                    jTextField.setText("");
                    MyMethod.send(("3").getBytes(),s);
                    byte[] b=new byte[128];
                    b= MyMethod.receive(b,s);
                    switch (b[0]){
                        case '0' : print("返回失败，已是根目录");    break;
                        case '1' : {
                            print("返回成功，当前目录：根目录"+new String(b,1,b.length-1));
                            break;
                        }
                    }
                }});

            cd.addActionListener(new ActionListener(){                       //6
                public void actionPerformed(ActionEvent e) {
                    String name;
                    name=jTextField.getText();
                    jTextField.setText("");
                    if(name.getBytes().length>0) {           //这部分写新建指令发送给服务区的比特流
                        byte []b=("6"+name).getBytes();
                        MyMethod.send(b,s);
                        //接收反馈指令
                        b= MyMethod.receive(new byte[128],s);
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

            clear.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    jTextArea.setText("消息成功清空");
                    print("");
                    clientWindow.repaint();
                }});
        }

        void print(String string) {
            jTextArea.append(string + "\r\n");
            clientWindow.repaint();
        }

        JFileChooser fileWindow(int model){
            JFileChooser jfc=new JFileChooser(rootpath);
            switch (model){
                case 1:jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    jfc.setMultiSelectionEnabled(true);
                    jfc.showDialog(new JLabel(), "选择上传的多个文件/文件夹");
                    break;
                case 2:jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    jfc.showDialog(new JLabel(), "选择文件储存位置");
                    break;
            }
            jfc.setVisible(true);
            return jfc;
        }
    }

    public static void main(String[] args) {
        try{
            new MyClient();
        }catch (Exception em){
            em.printStackTrace();
        }
    }
}