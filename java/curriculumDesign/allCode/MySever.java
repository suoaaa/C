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
    JPanel p=new JPanel();                                  //p��Ϊ��ͷ��ť������
    JTextArea jTextArea=new JTextArea(10,10);   //��ʾ��¼��Ϣ�Լ��ļ��������
    JScrollPane jscrollpane =new JScrollPane(jTextArea);    //���ı�����ӹ�����
    JButton bPosition =new JButton("����λ��");
    JButton bClear =new JButton("�����Ϣ");

    MySever() throws Exception {                //��һ���ǳ�ʼ�����ڣ��½��԰�ť�ļ�������ȡ�ļ�����Ŀ¼���½��̳߳أ��ȴ��ͻ�����������
        super("�ļ�����ϵͳ������");
        this.setSize(400,500);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        p.add(bPosition);
        p.add(bClear);

        jTextArea.setEditable(false);
        MyStreamMethod.print(jTextArea,"���������������ȴ�����");
        this.add(p, BorderLayout.NORTH);
        this.add(jscrollpane,BorderLayout.CENTER);
        this.setVisible(true);
        rootpath=new File("").getAbsolutePath()+"\\java\\curriculumDesign\\severSave";
        s = new ServerSocket(5050,3);       //�޶����ͬʱ��¼3��ip
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
