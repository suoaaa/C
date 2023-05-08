package ������.�ļ��������;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import javax.swing.*;

public class ����� {

    String rootpath="E:\\���˱��\\����-ȫ\\java\\������\\�ļ�����Ŀ¼";
    static severWindow severwindow;
    ExecutorService pool;
    ServerSocket s;
    Socket socket;

    �����() throws Exception {
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
        myRunnable(Socket s){this.s=s;path=rootpath;}
        public void run() {
            try (InputStream o = s.getInputStream()) {
                severwindow.print("IPΪ"+s.getInetAddress().getHostAddress()+"���û������ӷ�����");
                byte[] b = new byte[1024];
                int n=0,i=0;
                do{
                    n=o.read();
                    b[i]= (byte) n;
                    i++;
                }while(n!=0);
                System.out.println();
                System.out.println(new String(b,0,i-1));
                severwindow.print(new String(b));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class severWindow {
        JFrame win=new JFrame("�ļ�����ϵͳ������");
        JPanel p=new JPanel();         //p��Ϊ��ͷ��ť������
        JTextArea jTextArea=new JTextArea(10,10);//��ʾ��¼��Ϣ�Լ��ļ��������
        JScrollPane jscrollpane;//���ı�����ӹ�����

        JButton position=new JButton("����λ��");
        JButton clear=new JButton("�����Ϣ");
        JButton reopen=new JButton("����������");
        severWindow(){
            win.setSize(400,500);
            win.setLocationRelativeTo(null);
            win.setVisible(false);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);
            print("���������������ȴ�����");

            p.add(position);
            p.add(clear);
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

            clear.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jTextArea.setText("��Ϣ�ɹ����");
                }
            });

            reopen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try{
                        if(s.isClosed()){
                            s = new ServerSocket(5050,5);       //�޶����ͬʱ��¼5���û�
                        }else{
                            severwindow.print("����������������,��������");
                        }
                    }catch (Exception ie){
                        ie.printStackTrace();
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
            JFileChooser jfc=new JFileChooser(rootpath);
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.showDialog(new JLabel(), "ѡ���ļ�����Ŀ¼");
            jfc.setVisible(true);
            try{
                rootpath=jfc.getSelectedFile().getAbsolutePath();
                File file=new File(rootpath);
                if(file.isDirectory()&&file.exists()){}
                else file.mkdir();
            }catch (Exception e){}
            severwindow.print("��ǰ�ļ�����Ŀ¼Ϊ:"+rootpath);

        }
    }

    public static void main(String[] args) {
        try {
            new �����();
        } catch (SocketTimeoutException e1) {
            �����.severwindow.print("��ʱ��δ���û����ӷ����������������Զ��ر�");
        }catch(SocketException e3){
            System.out.println("�û��Ͽ�����");
        }
        catch (Exception e2){
            e2.printStackTrace();
        }
    }
}
