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
    JTextArea jTextArea = new JTextArea(1, 1);  //��ʾ������Ϣ�Լ��ļ��������
    JTextField jTextField=new JTextField();                 //�û������ڴ������ļ�����
    JScrollPane jscrollpane;                                //���ı�����ӹ�����
    JButton bShow = new JButton("�����ļ�");                //��ťʵ�ֶ�Ӧ����
    JButton bUpload = new JButton("�ϴ��ļ�");
    JButton bDownload = new JButton("�����ļ�");
    JButton bLast = new JButton("��һ��");
    JButton bClear = new JButton("��նԻ�");
    JButton bCreate = new JButton("�����ļ���");
    JButton bDel = new JButton("ɾ���ļ�");
    JButton bCd = new JButton("����");
    public MyClient() {                                             //��һ���ǳ�ʼ�����ڣ��½��԰�ť�ļ�������ȡ�ļ�����Ŀ¼�����ӷ�����
        super("�ļ�����ͻ���");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        p1.setLayout(new GridLayout(2,4));
        p1.add(bShow);              //ͷ����:'0'
        p1.add(bUpload);            //ͷ����:��1��
        p1.add(bDownload);          //ͷ����:��2��
        p1.add(bLast);              //ͷ����:��3��
        p1.add(bClear);
        p1.add(bCreate);            //ͷ����:��4��
        p1.add(bDel);               //ͷ����:��5��
        p1.add(bCd);                //ͷ����:��6��
        p2.setLayout(new GridLayout(2,1));
        p2.add(new Label("�����ַ���ļ����ƣ�"));
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
                if(ip.isEmpty())  Thread.sleep(1000);               //δ�����������ַʱ��������һ����˯��1s������ж�
                else {
                    MyStreamMethod.print(jTextArea,"������������..");
                    s=new Socket(String.valueOf(ip), 5050);
                    MyStreamMethod.print(jTextArea,"������"+ip+"������");
                    break;
                }
            }
        } catch (ConnectException e1){
            MyStreamMethod.print(jTextArea,"���������ӳ�ʱ����رտͻ��˲��ȴ�3-5s����������");
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