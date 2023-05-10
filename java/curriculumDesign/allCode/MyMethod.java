package curriculumDesign.allCode;

import javax.swing.*;
import java.io.BufferedOutputStream;
import java.io.*;
import java.net.*;
import java.util.List;


public final class MyMethod {
    private MyMethod(){};
    public static void send(byte[] b, Socket s){
        try{
            BufferedOutputStream o = new BufferedOutputStream(s.getOutputStream());
            if(b.length>=127) {
                o.write(b,0,127);
                o.write("@".getBytes());
                o.flush();
                return;
            }else o.write(b);
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

    public static void send(File[] file,Socket s){}

    public static byte[] receive(byte[] b, Socket s){   //����128���ص����鲢��������Ҫ������
        byte[] ret=null;
        try{
            InputStream in = s.getInputStream();
            in.read(b);
            int i=b.length-1;
            StringBuilder num= new StringBuilder();
            while(b[i]!='@'){
                num.insert(0, (char) b[i] + "");
                i--;
            }
            int n;
            if(i==b.length-1) n=1;
            else n=Integer.parseInt(num.toString());
            ret=new byte[128-n];
            for(i=0;i<128-n;i++){
                ret[i]=b[i];
            }
        }catch (Exception ignored){}
        return ret;
    }

    public static void send(File file, Socket s){
        //�ֽ�ͷ1λΪ1:ȷ�����ϴ��ļ�
        //�Լ�ͷ2λ��ȷ�����ļ����ļ��У�0���ļ���1���ļ���
        //����λȷ��size���ļ������ļ�����/�ļ����ش�С��
        //��Ϊ�ļ���
        //֮���ļ������ļ����ݱ�����������ͷ�����⺬��
        int flag=3;
        if(file.exists()&& file.isFile()) flag=0;
        if(file.exists()&& file.isDirectory()) flag=1;
        StringBuilder string = new StringBuilder();
        string.append(1).append(flag);
        switch (flag){
            case 0:{
                byte[] b=new byte[127];
                long size=file.length();    //���ֵΪ9223372036854775807
                long m=size%127;            //���ֵΪ72624976668147841---17λ
                long n=size/127;
                if(n!=0) m++;
                long i=m;
                int j=0;
                for(;i>0;j++) i/=10;
                string.append("0".repeat(17-j)).append(m).append(file.getName());
                send(string.toString().getBytes(),s);
                BufferedInputStream in;
                try {
                    in=new BufferedInputStream(new FileInputStream(file));
                    for(i=0;i<m-1;i++){
                        in.read(b,0,127);
                        send(b,s);
                    }
                    if(n==0) {
                        in.read(b,0,127);
                        send(b,s);
                    }else{
                        b=new byte[(int)n];
                        in.read(b,0,(int)n);
                        send(b,s);
                    }
                    in.close();
                }catch (Exception ignore){};
                break;
            }
            case 1 : {
                File []list=file.listFiles();
                int num=list.length;
                if ( num <= 999 && num>99 )   string.append(num);
                else if( num < 100 && num > 9 )  string.append(0).append(num);
                else if(num <10)  string.append(0).append(0).append(num);
                send(string.append(file.getName()).toString().getBytes(),s);
                for(File f:list){
                    send(f,s);
                }break;
            }
        }
    }
    public static void receive(byte[] b, Socket s, String nowpath){
        try{
            switch (b[1]) {
                case '0': {
                    BufferedOutputStream o;
                    File f=new File(nowpath+"\\"+new String(b,19,b.length-19));
                    if(f.exists()) {
                        if(!f.delete()&&!f.createNewFile()) return;
                    }
                    o=new BufferedOutputStream(new FileOutputStream(f));
                    long num = 0;
                    for (int i = 2; i < 19; i++) {
                        num = num * 10 + b[i] - '0';
                    }
                    for (long i = 0; i < num; i++) {
                        b = receive(new byte[128], s);
                        o.write(b);
                    }
                    o.flush();
                    o.close();
                    break;
                }
                case '1' : {
                    String string = nowpath+"\\"+new String(b, 5, b.length-5);
                    File f=new File(string);
                    if(!f.exists())     f.mkdir();
                    int num=0;
                    for (int i = 2; i < 5; i++) {
                        num = num * 10 + b[i] - '0';
                    }
                    for(int i=0;i<num;i++){
                        b=receive(new byte[128],s);
                        receive(b,s,string);
                    }

                }
            }
        }catch (Exception ignored){}
    }
    public static void getAllFile (File fileInput, List< String > allFileList, int n){
        // ��ȡ�ļ��б�
        File[] fileList = fileInput.listFiles();
        assert fileList != null;
        StringBuilder string = new StringBuilder();
        string.append("  ".repeat(Math.max(0, n)));
        for (File file : fileList) {
            if (file.isDirectory()) {
                // �ݹ鴦���ļ���
                allFileList.add(string + file.getName() + "\\");
                getAllFile(file, allFileList, n + 1);
            } else {
                // ������ļ�������뵽�ļ���������
                allFileList.add(string + file.getName());
            }
        }
    }
    public static JFileChooser fileWindow(int model,String rootpath){
        JFileChooser jfc=new JFileChooser(rootpath);
        switch (model){
            case 1:jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.setMultiSelectionEnabled(true);
                jfc.showDialog(new JLabel(), "ѡ���ϴ��Ķ���ļ�/�ļ���");
                break;
            case 2:jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.showDialog(new JLabel(), "ѡ�񱾵��ļ���");
                break;
        }
        jfc.setVisible(true);
        return jfc;
    }
}
