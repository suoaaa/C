package curriculumDesign.allCode;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import javax.swing.*;

public class MySever extends JFrame implements ActionListener {
    String rootpath;
    ExecutorService pool;
    ServerSocket s;
    Socket socket;
    JPanel p=new JPanel();                                  //p作为开头按钮的容器
    JTextArea jTextArea=new JTextArea(10,10);   //显示登录信息以及文件传输情况
    JScrollPane jscrollpane =new JScrollPane(jTextArea);    //给文本框添加滚动条
    JButton bPosition =new JButton("储存位置");
    JButton bClear =new JButton("清空消息");

    MySever() throws Exception {                //这一段是初始化窗口，新建对按钮的监听，获取文件储存目录，新建线程池，等待客户端链接申请
        super("文件传输系统服务器");
        this.setSize(400,500);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        p.add(bPosition);
        p.add(bClear);

        jTextArea.setEditable(false);
        MyStreamMethod.print(jTextArea,"服务器已启动，等待链接");
        this.add(p, BorderLayout.NORTH);
        this.add(jscrollpane,BorderLayout.CENTER);
        this.setVisible(true);
        rootpath=new File("").getAbsolutePath()+"\\java\\curriculumDesign\\severSave";
        s = new ServerSocket(5050,3);       //限定最多同时登录3个ip
        pool = Executors.newCachedThreadPool();

        bPosition.addActionListener(this);
        bClear.addActionListener(this);

        while(true){
            socket = s.accept();
            pool.execute(new MyRunnable(socket,rootpath,jTextArea));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource()==bPosition) MyActionMethod.position(jTextArea,rootpath);
       else if(e.getSource()==bClear) MyActionMethod.clear(jTextArea);
    }

    public static void main(String[] args) {
        try {
            new MySever();
        }catch(Exception ignored){}
    }
}
