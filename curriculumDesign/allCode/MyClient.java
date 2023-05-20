package curriculumDesign.allCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class MyClient extends JFrame implements ActionListener{

    StringBuffer ip=new StringBuffer();
    Socket s;
    String rootpath ;
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JTextArea jTextArea = new JTextArea(1, 1);  //显示链接信息以及文件传输情况
    JTextField jTextField=new JTextField();                 //用户可以在此输入文件名称
    JScrollPane jscrollpane;                                //给文本框添加滚动条
    JButton bShow = new JButton("遍历文件");                //按钮实现对应功能
    JButton bUpload = new JButton("上传文件");
    JButton bDownload = new JButton("下载文件");
    JButton bLast = new JButton("上一级");
    JButton bClear = new JButton("清空对话");
    JButton bCreate = new JButton("创建文件夹");
    JButton bDel = new JButton("删除文件");
    JButton bCd = new JButton("进入");
    public MyClient() {                                             //这一段是初始化窗口，新建对按钮的监听，获取文件储存目录，链接服务器
        super("文件传输客户端");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        p1.setLayout(new GridLayout(2,4));
        p1.add(bShow);              //头比特:'0'
        p1.add(bUpload);            //头比特:‘1’
        p1.add(bDownload);          //头比特:‘2’
        p1.add(bLast);              //头比特:‘3’
        p1.add(bClear);
        p1.add(bCreate);            //头比特:‘4’
        p1.add(bDel);               //头比特:‘5’
        p1.add(bCd);                //头比特:‘6’
        p2.setLayout(new GridLayout(2,1));
        p2.add(new Label("输入地址或文件名称："));
        p2.add(jTextField);

        jscrollpane = new JScrollPane(jTextArea);
        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);

        this.add(p1, BorderLayout.NORTH);
        this.add(p2, BorderLayout.SOUTH);
        this.add(jscrollpane, BorderLayout.CENTER);
        this.setVisible(true);

        bShow.addActionListener(this);
        bUpload.addActionListener(this);
        bDownload.addActionListener(this);
        bLast.addActionListener(this);
        bClear.addActionListener(this);
        bCreate.addActionListener(this);
        bDel.addActionListener(this);
        bCd.addActionListener(this);

        rootpath = new File("").getAbsolutePath()+"\\java\\curriculumDesign\\clientSave";

        try{
            InputWindow inputWindow=new InputWindow(ip);
            while (true){
                if(ip.isEmpty())  Thread.sleep(1000);               //未输入服务器地址时不进行下一步并睡眠1s后继续判断
                else {
                    MyStreamMethod.print(jTextArea,"服务器连接中..");
                    s=new Socket(String.valueOf(ip), 5050);
                    MyStreamMethod.print(jTextArea,"服务器"+ip+"已链接");
                    break;
                }
            }
        } catch (ConnectException e1){
            MyStreamMethod.print(jTextArea,"服务器连接超时，请关闭客户端并等待3-5s后重新链接");
        } catch(Exception e2){
            e2.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        try{
            if (e.getSource() == bShow) MyActionMethod.show(s, jTextArea, jTextField);
            else if (e.getSource() == bUpload) MyActionMethod.upload(s, rootpath);
            else if (e.getSource() == bDownload) MyActionMethod.download(s, jTextArea, jTextField, rootpath);
            else if (e.getSource() == bLast) MyActionMethod.last(s, jTextArea, jTextField);
            else if (e.getSource() == bClear) MyActionMethod.clear(jTextArea);
            else if (e.getSource() == bCreate) MyActionMethod.create(s, jTextArea, jTextField);
            else if (e.getSource() == bDel) MyActionMethod.del(s, jTextArea, jTextField);
            else if (e.getSource() == bCd) MyActionMethod.cd(s, jTextArea, jTextField);
        }catch (Exception ignored){}
    }

}