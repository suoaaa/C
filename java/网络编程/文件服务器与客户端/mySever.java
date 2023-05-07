package ������.�ļ���������ͻ���;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import javax.swing.*;

public class mySever {

    String rootpath="E:\\���˱��\\����-ȫ\\java";
    severWindow severwindow;
    ExecutorService pool;
    ServerSocket s;
    Socket socket;

    mySever() throws Exception {
        severwindow = new severWindow();
        s = new ServerSocket(5050,5);       //�޶����ͬʱ��¼5���û�
        s.setSoTimeout(60*1000*2);
        pool = Executors.newCachedThreadPool();
        while(true){
            socket = s.accept();
            pool.execute(new myRunnable(socket));
        }
    }

    class myRunnable implements Runnable{
        Socket s;
        String path;
        myRunnable(Socket s){this.s=s;}
        public void run() {
            try (InputStream o = s.getInputStream()) {
                severwindow.print("һ���û�������");
                byte[] b = new byte[1024];
                o.read(b);
                System.out.println(new String(b));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class severWindow {
        JFrame win=new JFrame("�ļ����������");
        JPanel p=new JPanel();         //p��Ϊ��ͷ��ť������
        JTextArea jTextArea=new JTextArea(10,10);//��ʾ��¼��Ϣ�Լ��ļ��������
        JScrollPane jscrollpane;//���ı�����ӹ�����

        JButton position=new JButton("����λ��");
        JButton close=new JButton("�رշ�����");
        JButton reopen=new JButton("���¿���������");
        severWindow(){
            win=new JFrame("�ļ�����ϵͳ�ͻ���");
            win.setSize(400,500);
            win.setLocationRelativeTo(null);
            win.setVisible(false);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);
            print("���������������ȴ�����");

            p.add(position);
            p.add(close);
            p.add(reopen);
            jscrollpane =new JScrollPane(jTextArea);

            jTextArea.setEditable(false);

            win.add(p,BorderLayout.NORTH);
            win.add(jscrollpane,BorderLayout.CENTER);
            win.setVisible(true);
            position.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fileWindow();
                }
            });

            close.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(!s.isClosed()){
                        try{

                            print("�Ѿ��ɹ��ر�");
                        }catch (Exception ex){
                        }
                    }else{
                        print("�����ظ��ر�");
                    }
                }
            });

            reopen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(s.isClosed()){
                        try{
                            s=new ServerSocket(5050);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }else{
                        print("�������������У������ظ�����");
                    }
                }
            });
        }


        void print(String string){
            jTextArea.append(string+"\r\n");
            p.updateUI();
            win.repaint();
        }

        void fileWindow(){
            JFileChooser jfc=new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.showDialog(new JLabel(), "ѡ���ļ�����Ŀ¼");
            jfc.setVisible(true);
            try{
                rootpath=jfc.getSelectedFile().getAbsolutePath();
            }catch (Exception e){}
            severwindow.print("��ǰ�ļ�����Ŀ¼Ϊ:"+rootpath);

        }
    }

    public static void main(String[] args) {
        try {
            new mySever();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
