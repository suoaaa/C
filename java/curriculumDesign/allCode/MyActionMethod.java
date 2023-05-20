package curriculumDesign.allCode;

import javax.swing.*;
import java.io.File;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class MyActionMethod {
    //����ഴ��Ŀ���Ǽ��ٿͻ�����������ActionListener��actionPerformed�����еĴ�����
    //���д󲿷�Ϊ�ͻ�����Ҫ
    //���������û����°�ťʱ��actionPerformed�������ñ��������ж�Ӧ�ĳ�Ա����
    //���������еĳ�Ա������Ҫ�õ��ͻ��˵�������ݣ���Ҫ��������

    private MyActionMethod(){}

    public static void show  (Socket s, JTextArea jTextArea,JTextField jTextField) throws InterruptedException {
        //�ͻ��˵��ã���������������ļ���
        //���շ���˷��͵��ļ���������ѭ����ӡ�����ļ���
        String name;
        name=jTextField.getText();
        jTextField.setText("");
        byte []b=("0"+name).getBytes();
        MyStreamMethod.send(b,s);
        //���շ���ָ�����ļ�����
        b= MyStreamMethod.receive(new byte[128],s);
        if(b[0]=='0') {
            MyStreamMethod.print(jTextArea, "��ǰ�ļ���Ϊ��");
            return ;
        }
        int n=Integer.parseInt(new String(b));
        b= MyStreamMethod.receive(new byte[128],s);
        MyStreamMethod.print(jTextArea,"����������ǰ�ļ��У�"+new String(b));
        for(int i=0;i<n;i++){
            //ѭ����ӡ�ļ���
            b= MyStreamMethod.receive(new byte[128],s);
            MyStreamMethod.print(jTextArea,new String(b));
        }
    }

    public static void upload  (Socket s,String rootpath){
        //�ͻ��˵��ã��ϴ������ļ��������
        //��ȡ��Ҫ�ϴ����ļ�������MyMethod�������е�send��file������
        JFileChooser jfc = MyStreamMethod.fileWindow(1,rootpath);
        try{
            File[] file=jfc.getSelectedFiles();
            for(File f:file){
                MyStreamMethod.send(f,s);
            }
        }catch (Exception ex){ex.printStackTrace();}
    }

    public static void download  (Socket s,JTextArea jTextArea,JTextField jTextField,String rootpath){
        //�ͻ��˵��ã����ط�����ļ������أ��ȵ�ȡJTextField��������ļ��������������
        //������ڣ�ѭ�����շ�����������file�ļ��������ڱ���
        byte []b;
        String name;
        name=jTextField.getText();
        jTextField.setText("");
        if(name.isEmpty()){
            MyStreamMethod.print(jTextArea,"�������ļ������ƺ�����");
            return;
        }else{
            MyStreamMethod.send(("21"+name).getBytes(),s);
            b=MyStreamMethod.receive(new byte[128],s);
            if (b[0] == '0') {
                MyStreamMethod.print(jTextArea, "�������е�ǰĿ¼�޴��ļ����ļ��У�������");
                return;
            }
        }
        JFileChooser jfc = MyStreamMethod.fileWindow(2,rootpath);
        try{//��ȡ�ͻ�����Ҫ�ļ������ر����ַ
            //ѭ�������ļ�
            File file=jfc.getSelectedFile();
            if(file==null)  file=new File(rootpath);
            MyStreamMethod.print(jTextArea,"��ǰ�ļ����ص�ַ��");
            MyStreamMethod.print(jTextArea,file.getPath());
            MyStreamMethod.send(("22"+(char)b[1]).getBytes(),s);
            MyStreamMethod.receive(MyStreamMethod.receive(new byte[128],s),s,file.getPath());
            List<String> allFileList = new ArrayList<>();
            MyStreamMethod.getAllFile(file,allFileList,0);
            MyStreamMethod.print(jTextArea,"�����������ص�ַ�ļ��У�");
            for(String string:allFileList){
                MyStreamMethod.print(jTextArea,string);
            }
            MyStreamMethod.print(jTextArea,"��������");
        }catch (Exception ex){ex.printStackTrace();}
    }

    public static void last  (Socket s,JTextArea jTextArea,JTextField jTextField){
        //�ͻ��˵��ã�ʹ����˶�Ӧ�����ڵ�ַ����һ��
        //������ڸ�Ŀ¼����������
        jTextField.setText("");
        MyStreamMethod.send(("3").getBytes(),s);
        byte[] b;
        b= MyStreamMethod.receive(new byte[128],s);
        switch (b[0]) {
            case '0' -> MyStreamMethod.print(jTextArea, "����ʧ�ܣ����Ǹ�Ŀ¼");
            case '1' -> MyStreamMethod.print(jTextArea, "���سɹ�����ǰĿ¼����Ŀ¼" + new String(b, 1, b.length - 1));
        }
    }

    public static void clear(JTextArea jTextArea){
        //˫�����ɵ��ã����TTextArea�е�����
        jTextArea.setText("��Ϣ�ɹ����\r\n");
    }


    public static void create  (Socket s,JTextArea jTextArea,JTextField jTextField){
        //�ͻ��˵��ã������˷������룬�ٷ���˴�����JTextField������Ϊ�����ļ���
        String name;
        name=jTextField.getText();
        jTextField.setText("");
        if(name.getBytes().length>0) {           //�ⲿ��д�½�ָ��͸��������ı�����
            byte []b=("4"+name).getBytes();
            MyStreamMethod.send(b,s);
            //���շ���ָ��
            b=new byte[128];
            b= MyStreamMethod.receive(b,s);
            switch (b[0]) {
                case '0' -> MyStreamMethod.print(jTextArea, "����ͬ���ļ���");
                case '1' -> MyStreamMethod.print(jTextArea, "�����ɹ�");
                case '2' -> MyStreamMethod.print(jTextArea, "����ʧ��");
            }
        }
        else {
            MyStreamMethod.print(jTextArea,"�������ļ��������µ��");
        }
    }

    public static void del  (Socket s,JTextArea jTextArea,JTextField jTextField){
        //�ͻ��˵��ã������˷������룬ɾ����JTextField����Ϊ�����ڷ���˴�����ļ������ļ���
        String name;
        name=jTextField.getText();
        if(name.isEmpty()) MyStreamMethod.print(jTextArea,"�������ļ��������µ��");
        else {
            jTextField.setText("");
            byte []b=("5"+name).getBytes();
            MyStreamMethod.send(b,s);
            //���շ���ָ��
            b= MyStreamMethod.receive(new byte[128],s);
            switch (b[0]) {
                case '0' -> MyStreamMethod.print(jTextArea, "ɾ���ļ���" + name + "�ɹ�");
                case '1' -> MyStreamMethod.print(jTextArea, "ɾ���ļ��У�" + name + "�ɹ�");
                case '2' -> MyStreamMethod.print(jTextArea, "ɾ����" + name + "ʧ�ܣ�Ŀ¼�д����ļ���ռ��");
                case '3' -> MyStreamMethod.print(jTextArea, "ɾ��ʧ�ܣ���Ŀ¼��" + name + "������");
            }
        }
    }

    public static void cd  (Socket s,JTextArea jTextArea,JTextField jTextField){
        //�ͻ��˵��ã��������������JTextField����Ϊ�����ļ��л���Ԥ���ļ�����
        String name;
        name=jTextField.getText();
        jTextField.setText("");
        if(name.getBytes().length>0) {           //�ⲿ��д�½�ָ��͸��������ı�����
            byte []b=("6"+name).getBytes();
            MyStreamMethod.send(b,s);
            //���շ���ָ��
            b= MyStreamMethod.receive(new byte[128],s);
            switch (b[0]) {
                case '0' -> MyStreamMethod.print(jTextArea, "�޴��ļ���");
                case '1' -> MyStreamMethod.print(jTextArea, "�����ļ���:��Ŀ¼" + new String(b, 1, b.length - 1));
                case '2' -> {
                    MyStreamMethod.print(jTextArea, "�޷�ȷ�����ļ������ʽ��������������һ��Ϊ��ȷԤ�����ݣ�");
                    MyStreamMethod.print(jTextArea,"------------��ʼԤ��������ΪGBK��ʽ��----------------");
                    MyStreamMethod.print(jTextArea, new String(b, 1, b.length - 1, Charset.forName("GBK")));
                    MyStreamMethod.print(jTextArea,"-------------------����Ϊutf-8��ʽ��----------------");
                    MyStreamMethod.print(jTextArea, new String(b, 1, b.length - 1, StandardCharsets.UTF_8));
                }
                case '3' -> MyStreamMethod.print(jTextArea, "�ļ����Ͳ�֧��Ԥ��");
            }
        }
        else {
            MyStreamMethod.print(jTextArea,"�������ļ��������µ��");
        }
    }

    public static void position(JTextArea jTextArea,String rootpath){
        //����˵��ã����ķ���˸�Ŀ¼��λ�ã������ӷ������Ŀͻ��˲���Ӱ��
        JFileChooser jfc = MyStreamMethod.fileWindow(2,rootpath);
        File file= jfc.getSelectedFile();
        if(file!=null&&file.exists()) {
            MyStreamMethod.print(jTextArea,"���·���������Ŀ¼�ɹ�����ǰ�ļ��и�Ŀ¼Ϊ��");
            rootpath = file.getPath();
        }else   MyStreamMethod.print(jTextArea,"���·���������Ŀ¼ʧ�ܣ���ǰ�ļ��и�Ŀ¼Ϊ��");
        MyStreamMethod.print(jTextArea,rootpath);
    }

}
