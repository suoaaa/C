package ������.�ļ��������;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class �ͻ��� {

    �ͻ���(){
        new clientWindow();
    }
    class clientWindow{
        JFrame win=new JFrame("�ļ�����ϵͳ�ͻ���");
        JPanel p=new JPanel();         //p��Ϊ��ͷ��ť������
        JTextArea jTextArea=new JTextArea(10,10);//��ʾ��¼��Ϣ�Լ��ļ��������
        JScrollPane jscrollpane;//���ı�����ӹ�����
        JButton position=new JButton("����λ��");
        JButton clear=new JButton("�����Ϣ");
        clientWindow(){
            win=new JFrame("�ļ�����ͻ���");
            win.setSize(400,500);
            win.setLocationRelativeTo(null);
            win.setVisible(false);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);
            print("���������������ȴ�����");

            p.add(position);
            p.add(clear);
            jscrollpane =new JScrollPane(jTextArea);

            jTextArea.setEditable(false);

            win.add(p,BorderLayout.NORTH);
            win.add(jscrollpane,BorderLayout.CENTER);
            win.setVisible(true);
        }


        void print(String string){
            jTextArea.append(string+"\r\n");
            p.updateUI();
            win.repaint();
        }
    }
    public static void main(String[] args) throws IOException {
        Socket s;
        try{
            new �ͻ���();
            InetAddress i=InetAddress.getLocalHost();
            s=new Socket(i, 5050);
            System.out.println("������������");
            String path="E:\\����\\a.txt";
            byte [ ]b=new byte[1024];
            BufferedInputStream o=new BufferedInputStream(new FileInputStream(path));
            o.read(b);
            OutputStream o2=s.getOutputStream();
            // o2.write(new String (b).getBytes("GBK"));
            o2.write(b);
            s.close();
            o.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}