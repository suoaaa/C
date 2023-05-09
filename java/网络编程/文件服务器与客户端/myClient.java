package ������.�ļ���������ͻ���;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class myClient {

    InetAddress i;
    Socket s;
    String rootpath = "E:\\����";
    String downpath = rootpath;
    String uppath = rootpath;
    clientWindow clientwindow=new clientWindow();
    myClient(){
        try{
            i=InetAddress.getLocalHost();
            s=new Socket(i, 5050);
            clientwindow.print("������"+i.getHostAddress()+"������");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    class clientWindow {
        JFrame clientWindow = new JFrame("�ļ�����ϵͳ�ͻ���");
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JTextArea jTextArea = new JTextArea(10, 10);//��ʾ��¼��Ϣ�Լ��ļ��������
        JTextField jTextField=new JTextField();//�û���������ָ��
        JScrollPane jscrollpane;//���ı�����ӹ�����
        JButton position = new JButton("�����ļ�");
        JButton upload = new JButton("�ϴ��ļ�");
        JButton download = new JButton("�����ļ�");
        JButton last = new JButton("��һ��");
        JButton deldir = new JButton("ɾ���ļ���");
        JButton delfile = new JButton("ɾ���ļ�");
        JButton create = new JButton("�����ļ���");
        JButton cd = new JButton("����");

        clientWindow() {
            clientWindow = new JFrame("�ļ�����ͻ���");
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
            p2.add(new Label("�����ַ/ָ�"));
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
                    //���շ���ָ��
                    b=receive(new byte[128]);
                    int n=Integer.parseInt(new String(b));
                    if(n==0)print("��ǰ�ļ���Ϊ��");
                    else{
                        print("����������ǰ�ļ��У�");
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
                    if(name.getBytes().length>0) {           //�ⲿ��д�½�ָ��͸��������ı�����
                        byte []b=("3"+name).getBytes();
                        send(b);
                        //���շ���ָ��
                        b=new byte[128];
                        b=receive(b);
                        switch (b[0]){
                            case '0' : print("����ͬ���ļ���");    break;
                            case '1' : print("�����ɹ�");         break;
                            case '2' : print("����ʧ��");         break;
                        }
                    }
                    else {
                        print("�������ļ��������µ��");
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
                    //���շ���ָ��
                    byte[] b=new byte[128];
                    b=receive(b);
                    switch (b[0]){
                        case '0' : print("����ʧ�ܣ����Ǹ�Ŀ¼");    break;
                        case '1' : {
                            print("���سɹ�����ǰĿ¼����Ŀ¼"+new String(b,1,b.length-1));
                            break;
                        }
                    }
                }});

            cd.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    String name;
                    name=jTextField.getText();
                    jTextField.setText("");
                    if(name.getBytes().length>0) {           //�ⲿ��д�½�ָ��͸��������ı�����
                        byte []b=("7"+name).getBytes();
                        send(b);
                        //���շ���ָ��
                        b=receive(new byte[128]);
                        switch (b[0]){
                            case '0' : print("�޴��ļ���");    break;
                            case '1' : print("�����ļ���:��Ŀ¼"+new String(b,1,b.length-1));         break;
                            case '2' : {
                                print("�����ļ�������ΪԤ�����ݣ�");
                                System.out.println(new String(b));
                                print(new String(b,1,b.length-1));
                                break;
                            }
                        }
                    }
                    else {
                        print("�������ļ��������µ��");
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

        byte[] receive(byte[] b){   //����128���ص����鲢��������Ҫ������
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
            jfc.showDialog(new JLabel(), "ѡ���ļ�");
            jfc.setVisible(true);
            String path=rootpath;
            try{
                path=jfc.getSelectedFile().getAbsolutePath();
                File file=new File(rootpath);
                if(!file.isDirectory()||!file.exists())file.mkdir();
            }catch (Exception ignored){}
            print("��ѡ���ļ�/�ļ���:"+rootpath);
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