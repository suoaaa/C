package curriculumDesign.allCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class MyClient {

    InetAddress i;
    Socket s;
    String rootpath ;
    clientWindow clientwindow=new clientWindow();
    MyClient(){
        rootpath = new File("").getAbsolutePath()+"\\java\\curriculumDesign\\clientSave";
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
        JFrame clientWindow ;
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JTextArea jTextArea = new JTextArea(1, 1);//��ʾ��¼��Ϣ�Լ��ļ��������
        JTextField jTextField=new JTextField();//�û���������ָ��
        JScrollPane jscrollpane;        //���ı�����ӹ�����
        JButton showdir = new JButton("�����ļ�");
        JButton upload = new JButton("�ϴ��ļ�");
        JButton download = new JButton("�����ļ�");
        JButton last = new JButton("��һ��");
        JButton clear = new JButton("��նԻ�");
        JButton create = new JButton("�����ļ���");
        JButton delfile = new JButton("ɾ���ļ�");

        JButton cd = new JButton("����");

        clientWindow() {
            clientWindow = new JFrame("�ļ�����ͻ���");
            clientWindow.setSize(400, 500);
            clientWindow.setLocationRelativeTo(null);
            clientWindow.setVisible(false);
            clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            clientWindow.setResizable(false);

            p1.setLayout(new GridLayout(2,4));
            p1.add(showdir);        //ͷ����:'0'
            p1.add(upload);         //ͷ����:��1��
            p1.add(download);       //ͷ����:��2��
            p1.add(last);           //ͷ����:��3��
            p1.add(clear);          
            p1.add(create);         //ͷ����:��4��
            p1.add(delfile);        //ͷ����:��5��
            p1.add(cd);             //ͷ����:��6��
            p2.setLayout(new GridLayout(2,1));
            p2.add(new Label("�����ַ/ָ�"));
            p2.add(jTextField);

            jscrollpane = new JScrollPane(jTextArea);
            jTextArea.setLineWrap(true);
            jTextArea.setEditable(false);

            clientWindow.add(p1, BorderLayout.NORTH);
            clientWindow.add(p2, BorderLayout.SOUTH);
            clientWindow.add(jscrollpane, BorderLayout.CENTER);
            clientWindow.setVisible(true);

            showdir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String name;
                    name=jTextField.getText();
                    jTextField.setText("");
                    byte []b=("0"+name).getBytes();
                    MyMethod.send(b,s);
                    //���շ���ָ��
                    b= MyMethod.receive(new byte[128],s);
                    if(b[0]=='0') print("��⵽�����������ַ�仯���ѻص���ǰ�����������ַ��Ŀ¼");
                    if(b.length==1)print("��ǰ�ļ���Ϊ��");
                    else{
                        int n=Integer.parseInt(new String(b,1,b.length-1));
                        b= MyMethod.receive(new byte[128],s);
                        print("����������ǰ�ļ��У�"+new String(b));
                        for(int i=0;i<n;i++){
                            b= MyMethod.receive(new byte[128],s);
                            print(new String(b));
                        }
                    }
                }
            });

            upload.addActionListener(new ActionListener(){              //1
                public void actionPerformed(ActionEvent e) {
                    JFileChooser jfc = MyMethod.fileWindow(1,rootpath);
                    try{
                        File[] file=jfc.getSelectedFiles();
                        for(File f:file){
                            MyMethod.send(f,s);
                        }
                        if(MyMethod.receive(new byte[128],s)[0]!='0'){
                            print("��⵽�����������ַ�仯���ѽ��ļ��ϴ����������µĴ����ַ��Ŀ¼");
                        }
                    }catch (Exception ex){ex.printStackTrace();}
            }});

            download.addActionListener(new ActionListener(){         //2
                public void actionPerformed(ActionEvent e) {
                    byte []b;
                    String name;
                    name=jTextField.getText();
                    jTextField.setText("");
                    if(name.isEmpty()){
                        print("�������ļ������ƺ�����");
                        return;
                    }else{
                        MyMethod.send(("21"+name).getBytes(),s);
                        b=MyMethod.receive(new byte[128],s);
                        switch (b[0]){
                            case '0' :  print("�������е�ǰĿ¼�޴��ļ����ļ��У�������");
                                return;
                            case '2' : print("��⵽�����������ַ�仯������������������е��ļ�����ѡ��");
                            default : break;
                        }
                    }
                    JFileChooser jfc = MyMethod.fileWindow(2,rootpath);
                    try{
                        File file=jfc.getSelectedFile();
                        if(file==null)  file=new File(rootpath);
                        print("��ǰ�ļ����ص�ַ��");
                        print(file.getPath());
                        MyMethod.send(("22"+(char)b[1]).getBytes(),s);
                        MyMethod.receive(MyMethod.receive(new byte[128],s),s,file.getPath());
                        List<String> allFileList = new ArrayList<>();
                        MyMethod.getAllFile(file,allFileList,0);
                        print("�����������ص�ַ�ļ��У�");
                        int n=allFileList.size();
                        for(String string:allFileList){
                            print(string);
                        }
                    }catch (Exception ex){ex.printStackTrace();}
            }});

            create.addActionListener(new ActionListener() {          //4
                public void actionPerformed(ActionEvent e) {
                    String name;
                    name=jTextField.getText();
                    jTextField.setText("");
                    if(name.getBytes().length>0) {           //�ⲿ��д�½�ָ��͸��������ı�����
                        byte []b=("4"+name).getBytes();
                        MyMethod.send(b,s);
                        //���շ���ָ��
                        b=new byte[128];
                        b= MyMethod.receive(b,s);
                        switch (b[0]){
                            case '0' : print("����ͬ���ļ���");    break;
                            case '1' : print("�����ɹ�");         break;
                            case '2' : print("����ʧ��");         break;
                            case '3' : print("��⵽�����������ַ�仯������������������е��ļ����²���");
                                    break;
                            default:break;
                        }
                    }
                    else {
                        print("�������ļ��������µ��");
                    }
                }
            });

            delfile.addActionListener(new ActionListener(){              //5
                public void actionPerformed(ActionEvent e) {
                    String name;
                    name=jTextField.getText();
                    if(name.isEmpty()) print("�������ļ��������µ��");
                    else {
                        jTextField.setText("");
                        byte []b=("5"+name).getBytes();
                        MyMethod.send(b,s);
                        //���շ���ָ��
                        b= MyMethod.receive(new byte[128],s);
                        switch (b[0]){
                            case '0' : print("ɾ���ļ���" + name + "�ɹ�"); break;
                            case '1' : print("ɾ���ļ��У�" + name + "�ɹ�"); break;
                            case '2' : print("ɾ����" + name + "ʧ�ܣ������ļ���ռ��");break;
                            case '4' : print("ɾ��ʧ�ܣ���Ŀ¼���ļ����ļ���" + name + "������");break;
                            case '3' : print("��⵽�����������ַ�仯������������������е��ļ����²���");
                                break;
                            default:break;
                        }
                    }
            }});

            last.addActionListener(new ActionListener() {                //3
                public void actionPerformed(ActionEvent e) {
                    jTextField.setText("");
                    MyMethod.send(("3").getBytes(),s);
                    byte[] b;
                    b= MyMethod.receive(new byte[128],s);
                    switch (b[0]){
                        case '0' : print("����ʧ�ܣ����Ǹ�Ŀ¼");    break;
                        case '1' : {
                            print("���سɹ�����ǰĿ¼����Ŀ¼"+new String(b,1,b.length-1));
                            break;
                        }
                        case '3' : print("��⵽�����������ַ�仯���ѻص������������Ŀ¼");
                            break;
                        default:break;
                    }
                }});

            cd.addActionListener(new ActionListener(){                       //6
                public void actionPerformed(ActionEvent e) {
                    String name;
                    name=jTextField.getText();
                    jTextField.setText("");
                    if(name.getBytes().length>0) {           //�ⲿ��д�½�ָ��͸��������ı�����
                        byte []b=("6"+name).getBytes();
                        MyMethod.send(b,s);
                        //���շ���ָ��
                        b= MyMethod.receive(new byte[128],s);
                        switch (b[0]){
                            case '0' : print("�޴��ļ���");    break;
                            case '1' : print("�����ļ���:��Ŀ¼"+new String(b,1,b.length-1));         break;
                            case '2' : {
                                print("�����ļ�������ΪԤ�����ݣ�");
                                print(new String(b,1,b.length-1));
                                break;
                            }
                            case '3' : print("��⵽�����������ַ�仯������������������е��ļ����²���");
                                break;
                            default:break;
                        }
                    }
                    else {
                        print("�������ļ��������µ��");
                    }
            }});

            clear.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    jTextArea.setText("��Ϣ�ɹ����");
                    print("");
                    clientWindow.repaint();
                }});
        }

        void print(String string) {
            jTextArea.append(string + "\r\n");
            clientWindow.repaint();
        }


    }

    public static void main(String[] args) {
        try{
            new MyClient();
        }catch (Exception em){
            em.printStackTrace();
        }
    }
}