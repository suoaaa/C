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
        JButton position = new JButton("����λ��");
        JButton upload = new JButton("�ϴ��ļ�");
        JButton download = new JButton("�����ļ�");
        JButton clear = new JButton("��նԻ�");
        JButton deldir = new JButton("ɾ���ļ���");
        JButton delfile = new JButton("ɾ���ļ�");
        JButton create = new JButton("�����ļ���");
//        JButton yes = new JButton("ȷ��");
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
            p1.add(clear);
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
                        if(name.getBytes().length>0) {           //�ⲿ��д�½�ָ��͸��������ı�����
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
                                case '0' : print("����ͬ���ļ���");    break;
                                case '1' : print("�����ɹ�");         break;
                                case '2' : print("����ʧ��");         break;
                            }System.out.println("bb");
                        }

                        else {
                            print("�������ļ��������µ��");
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