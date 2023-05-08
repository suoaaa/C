package ������.�ļ���������ͻ���;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class myClient {

    InetAddress i;
    Socket s;
    String rootpath="E:\\����";
    myClient(){
        clientWindow clientwindow=new clientWindow();
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
        JFrame win = new JFrame("�ļ�����ϵͳ�ͻ���");
        JPanel p = new JPanel();         //p��Ϊ��ͷ��ť������
        JTextArea jTextArea = new JTextArea(10, 10);//��ʾ��¼��Ϣ�Լ��ļ��������
        JScrollPane jscrollpane;//���ı�����ӹ�����
        JButton position = new JButton("����λ��");
        JButton clear = new JButton("�����Ϣ");

        clientWindow() {
            win = new JFrame("�ļ�����ͻ���");
            win.setSize(400, 500);
            win.setLocationRelativeTo(null);
            win.setVisible(false);
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setResizable(false);

            p.add(position);
            p.add(clear);
            jscrollpane = new JScrollPane(jTextArea);

            jTextArea.setEditable(false);

            win.add(p, BorderLayout.NORTH);
            win.add(jscrollpane, BorderLayout.CENTER);
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
        }


        void print(String string) {
            jTextArea.append(string + "\r\n");
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
            print("��ǰ�ļ�����Ŀ¼Ϊ:"+rootpath);

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