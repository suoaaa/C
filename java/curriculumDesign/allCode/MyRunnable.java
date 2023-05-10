package curriculumDesign.allCode;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MyRunnable implements Runnable {
    //����̳�Runnable�࣬�ڷ���˵��ã�ÿ��һ���µĿͻ���������ã����½���������̳߳�
    Socket s;       //��ͻ��˽�������
    String nowPath; //��¼�ͻ���Ŀǰ��Ŀ¼��λ��
    String downName;//��¼�ͻ����������ص��ļ�����
    String rootpath;//��¼�������Ա��������ṩ���ļ������Ŀ¼�����������ĺ�Ӱ�������ӵĿͻ���
    JTextArea jTextArea;//���������ڵĶ�ӦJTextField
    MyRunnable(Socket s,String rootpath,JTextArea jTextArea) {
        this.s = s;
        this.rootpath=rootpath;
        nowPath = rootpath;
        this.jTextArea=jTextArea;
    }

    public void run() {
        try {
            MyStreamMethod.print(jTextArea,"IPΪ" + s.getInetAddress().getHostAddress() + "���û������ӷ�����");
            byte []b;
            while (true) {
                b= MyStreamMethod.receive(new byte[128],s);
                if(b==null) {
                    s.close();
                    MyStreamMethod.print(jTextArea,"IPΪ" + s.getInetAddress().getHostAddress() + "���û��ѶϿ�����");
                    return;
                }
                switch (b[0]) {                     //���ݽ��յ��ı�������0��������Ͽͻ���ʵ�ֲ�ͬ�Ĺ���
                    case '0' -> showDir();          //չʾĿ¼���ļ�
                    case '1' -> upload(b);          //�ϴ��ļ�
                    case '2' -> download(b);        //�����ļ�
                    case '3' -> last();             //������һ��
                    case '4' -> create(b);          //�������ļ����ڵ�ǰ�ļ���
                    case '5' -> del(b);             //ɾ���ļ�/�ļ���
                    case '6' -> cd(b);              //�����ض��ļ�/Ԥ���ļ�
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyStreamMethod.print(jTextArea,"IPΪ" + s.getInetAddress().getHostAddress() + "���û��ѶϿ�����");
        }
    }

    void showDir () {
        //չʾ�ļ������ȷ����ļ���
        //��ѭ�������ļ�����
        File dir = new File(nowPath);
        if(!dir.exists()) if(!dir.mkdirs()) {
            MyStreamMethod.send("0".getBytes(), s);
            return;
        }
        List<String> allFileList = new ArrayList<>();
        MyStreamMethod.getAllFile(dir, allFileList, 0);
        MyStreamMethod.send((""+allFileList.size()).getBytes(),s);
        MyStreamMethod.send(dir.getName().getBytes(),s);
        for (String value : allFileList) {
            MyStreamMethod.send(value.getBytes(),s);
        }
        MyStreamMethod.print(jTextArea,"IPΪ"+s.getInetAddress()+"���û��������ļ���");
    }

    void upload (byte[] b){
        //�����ļ�
        MyStreamMethod.receive(b,s, nowPath);
        MyStreamMethod.print(jTextArea,"IPΪ"+s.getInetAddress()+"���û��ϴ����µ��ļ�");
    }

    void download (byte[] b){
        //Ϊ�ͻ��˷����ض����Ƶ��ļ������ļ���
        //��һ�ν��պ��ж�b[1]���жϵ�һ�λ�ڶ�������
        //��һ�������ж��Ƿ��и��ļ��л����ļ�
        //�ڶ����������ļ�
        switch (b[1]) {
            case '1' -> {
                downName = new String(b, 2, b.length - 2);
                File file = new File(nowPath);
                String[] strings = file.list();
                if (strings!=null&&strings.length > 0) for (String all : strings) {
                    if (all.equals(downName)) {
                        MyStreamMethod.send("11".getBytes(), s);
                        return;
                    }
                }
                if (downName.equals(file.getName())) MyStreamMethod.send("12".getBytes(), s);
                MyStreamMethod.send("0".getBytes(), s);
            }
            case '2' -> {
                switch (b[2]) {
                    case '1' -> MyStreamMethod.send(new File(nowPath + "\\" + downName), s);
                    case '2' -> MyStreamMethod.send(new File(nowPath), s);
                }
            }
        }
        MyStreamMethod.print(jTextArea,"IPΪ"+s.getInetAddress()+"���û����������ļ�");
    }

    void last(){
        //������һ��
        //������һ���ļ������Ƹ��ͻ���
        File file=new File(nowPath);
        if(nowPath.contains(rootpath)){
            if(rootpath.equals(nowPath)){
                MyStreamMethod.send("0".getBytes(),s);
            }else{
                nowPath = file.getParent();
                String string= nowPath.substring(rootpath.length());
                MyStreamMethod.send(("1"+string).getBytes(),s);
                MyStreamMethod.print(jTextArea,"IPΪ"+s.getInetAddress()+"���û������ļ��У���Ŀ¼\\"+string);
            }
        }
    }

    void create (byte[] b){
        //����b�������½��ļ�
        String thisPath = new String(b,1,b.length-1);
        int n = b.length - 1;
        try {
            if (n < 1) {
                MyStreamMethod.send("2".getBytes(),s);
            } else {
                File file = new File(nowPath + "\\" + thisPath);
                if (file.exists()) {
                    MyStreamMethod.send("0".getBytes(),s);
                } else {
                    if (file.mkdirs()) {
                        MyStreamMethod.send("1".getBytes(),s);
                    } else {
                        MyStreamMethod.send("2".getBytes(),s);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        MyStreamMethod.print(jTextArea,"IPΪ"+s.getInetAddress()+"���û������½��ļ���");
    }

    void del(byte[] b){
        //ɾ���ض��ļ�/�ļ��У������ͷ���
        String string=new String(b,1,b.length-1);
        File file=new File(nowPath +"\\"+string);
        if(file.exists()){
            if(file.isDirectory()){
                if(delAllFile(file)) string="1";
                else string="2";
                MyStreamMethod.send(string.getBytes(),s);
            }else {
                if(file.delete()){
                    MyStreamMethod.send("0".getBytes(),s);
                }else {
                    MyStreamMethod.send("2".getBytes(),s);
                }
            }
        }else{
            file=new File(nowPath);
            if(string.equals(file.getName())){
                if(delAllFile(file)) string="1";
                else string="2";
                MyStreamMethod.send(string.getBytes(),s);
            }else{
                MyStreamMethod.send("4".getBytes(),s);
            }
        }
        MyStreamMethod.print(jTextArea,"IPΪ"+s.getInetAddress()+"���û�����ɾ���ļ�");
    }

    void cd(byte[] b){
        //������һ�����ͷ���
        String name=new String(b,1,b.length-1);
        name= nowPath +"\\"+name;
        File file=new File(name);
        String string=name.substring(rootpath.length());
        if(!new File(nowPath).exists()){
            MyStreamMethod.send("3".getBytes(),s);
            nowPath =rootpath;
            return;
        }
        if(!file.exists()){
            MyStreamMethod.send("0".getBytes(),s);
        }else{
            nowPath =name;
            if(file.isDirectory()){
                MyStreamMethod.send(("1"+string).getBytes(),s);
                MyStreamMethod.print(jTextArea,"IPΪ"+s.getInetAddress()+"���û������ļ��У�"+string);
            }else{
                BufferedInputStream i;
                try{
                    i=new BufferedInputStream(new FileInputStream(file));
                    int n;
                    if(file.length()>=124) n=124;
                    else n=(int) file.length();
                    byte []by = new byte[n];
                    i.read(by,1,n-2);
                    i.close();
                    by[0]='2';
                    MyStreamMethod.send(by,s);
                    MyStreamMethod.print(jTextArea,"IPΪ"+s.getInetAddress()+"���û�Ԥ���ļ���"+string);
                }catch (Exception ie){ie.printStackTrace();}
            }
        }
    }

    boolean delAllFile(File file){
        //ɾ���ļ��з��������ӷ���
        boolean ret = true;
        File[] arr;
        arr=file.listFiles();
        for(int i = 0; i< Objects.requireNonNull(arr).length; i++){
            if(arr[i].isDirectory()) ret=ret& delAllFile(arr[i]);
            else ret=ret&arr[i].delete();
        }
        if(!file.delete()){
            String path= file.getPath().substring(rootpath.length());
            MyStreamMethod.print(jTextArea,"�ļ�"+path+"ɾ��ʧ��");
        }
        return ret;
    }

}