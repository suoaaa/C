package ������.�ļ���������ͻ���;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import javax.swing.*;

public class mySever {

    static severWindow severwindow;
    String rootpath="E:\\���˱��\\����-ȫ\\java\\������\\�ļ�����Ŀ¼";
    ExecutorService pool;
    ServerSocket s;
    Socket socket;

    mySever() throws Exception {
        severwindow = new severWindow();
        s = new ServerSocket(5050,5);       //�޶����ͬʱ��¼5���û�
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
            try {
                InputStream i = s.getInputStream();
                String path=rootpath;
                severwindow.print("IPΪ"+s.getInetAddress().getHostAddress()+"���û������ӷ�����");
                byte[] b = new byte[128];
                boolean flag=true;
                while(flag){
                    i.read(b,0,128);
                    String string=new String(b);
                    switch (string.charAt(0)){
                        case '1':   upload(string);
                            break;
                        case '2':   download(string);
                            break;
                        case '3':   create(string);
                            break;
                        case '4':   deldir(string);
                            break;
                        case '5':   delfile(string);
                            break;
                    }
                }
            } catch (Exception e) {
                severwindow.print("IPΪ"+s.getInetAddress().getHostAddress()+"���û��ѶϿ�����");
            }
        }
        void upload(String string){}

        void download(String string){}

        void create(String string){
            String thispath="";
            int n=string.length()-1;
            String  num="";
            while(string.charAt(n)!='0'){
                num=string.charAt(n)+num;
                n--;
            }
            n=string.length()-Integer.parseInt(num);
            try {
                BufferedOutputStream o = new BufferedOutputStream(s.getOutputStream());
                if(n<=1) {
                    o.write("2".getBytes());
                } else{
                    thispath = string.substring(1, n);
                    File file = new File(rootpath + "\\" + thispath);
                    if (file.exists()) {
                        o.write("0".getBytes());
                    } else {
                        if (file.mkdir()) {
                            o.write("1".getBytes());
                        } else {
                            o.write("2".getBytes());
                        }
                    }
                }
                o.flush();
            } catch (Exception ignored) {}
        }
    }

    List<String> showDir(){
        File dir = new File(rootpath);
        List<String> allFileList = new ArrayList<>();

        // �ж��ļ����Ƿ����
        if (!dir.exists()) {
            System.out.println("Ŀ¼������");
            dir.mkdir();
        }
        getAllFile(dir, allFileList,0);
        for(String string:allFileList){
            severwindow.print(string);
        }
        return allFileList;
    }
    public static void getAllFile(File fileInput, List<String> allFileList,int n) {
        // ��ȡ�ļ��б�
        File[] fileList = fileInput.listFiles();
        assert fileList != null;
        String string="";
        for(int  i=0;i<n;i++){
            string+="  ";
        }
        for (File file : fileList) {
            if (file.isDirectory()) {
                // �ݹ鴦���ļ���
                // �������ͳ�����ļ�������Խ���һ��ע�͵�
                allFileList.add(string+file.getName());
                getAllFile(file, allFileList,n+1);
            } else {
                // ������ļ�������뵽�ļ�������
                allFileList.add(string+file.getName());
            }
        }
    }



    void deldir(String string){}

    void delfile(String string){}

    class severWindow {
        JFrame win=new JFrame("�ļ�����ϵͳ������");
        JPanel p=new JPanel();         //p��Ϊ��ͷ��ť������
        JTextArea jTextArea=new JTextArea(10,10);//��ʾ��¼��Ϣ�Լ��ļ��������
        JScrollPane jscrollpane;//���ı�����ӹ�����

        JButton position=new JButton("����λ��");
        JButton clear=new JButton("�����Ϣ");
        severWindow(){
            win.setSize(400,500);               //��һ���ǳ�ʼ������
            win.setLocationRelativeTo(null);
            win.setVisible(false);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);
            print("���������������ȴ�����");

            p.add(position);
            p.add(clear);
            jscrollpane =new JScrollPane(jTextArea);
            jTextArea.setEditable(false);

            win.add(p, BorderLayout.NORTH);
            win.add(jscrollpane,BorderLayout.CENTER);
            win.setVisible(true);
            position.addActionListener(new ActionListener() {       //������λ�ð�ť���Ӽ���
                public void actionPerformed(ActionEvent e) {
                    fileWindow();
                }
            });

            clear.addActionListener(new ActionListener() {      //�������Ϣ��ť��Ӽ���
                public void actionPerformed(ActionEvent e) {
                    jTextArea.setText("��Ϣ�ɹ����\r\n");
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
            new mySever();
        }catch(SocketException e3){
            mySever.severwindow.print("�û��Ͽ�����");
        }
        catch (Exception e2){
//            mySever.severwindow.print("�û��Ͽ�����");
            e2.printStackTrace();
        }
    }
}
