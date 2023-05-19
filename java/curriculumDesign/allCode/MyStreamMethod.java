package curriculumDesign.allCode;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.List;

//�����й�ʵ���˸�����
//��������send�������������͵������ݰ���128���أ����Լ������ļ�/�ļ��У��������send��ϣ�
//��������receive�������������մ��������ݰ���128���أ����Լ������ļ�/�ļ��У��������receive��ϣ�
//��ȡ�����ļ����µ������ļ���������ʵ���е��ַ���list��

public final class MyStreamMethod {
    private MyStreamMethod(){}

    public static void send(byte[] b, Socket s){
        //����b�����ݣ�����127�������м��@�������λ�����Чλ��λ��
        //λ127���������һλΪ��@����֮ǰȫΪ��Чλ

        try{
            BufferedOutputStream o = new BufferedOutputStream(s.getOutputStream());
            if(b.length>=127) {
                o.write(b,0,127);
                o.write("@".getBytes());
                o.flush();
                return;
            }else o.write(b);
            int m = 128 - b.length;
            int i=1;
            if(m>9&&m<100) i=2;
            else if (m>100) i=3;
            for (; i < m; i++) {
                o.write("@".getBytes());
            }
            o.write(("" + m).getBytes());
            o.flush();
        }catch (Exception ignored){}
    }

    public static void send(File file, Socket s){
        //�ֽ�ͷ1λΪ��1:ȷ�����ϴ��ļ�
        //�ֽ�ͷ2λΪ��ȷ�����ļ����ļ��У�0���ļ���1���ļ���
        //��Ϊ�ļ��У���λȷ��size-�ļ������ļ�����
        //��Ϊ�ļ���
        //֮������ɱ����������ļ����ݣ�����ͷ�����⺬��
        //���ļ�����ĩβ�������ļ����ͽ������ڷ���һ��ֻ��һλ��@���ı�������Ϊ������־

        int flag=3;
        if(file.exists()&& file.isFile()) flag=0;
        if(file.exists()&& file.isDirectory()) flag=1;
        StringBuilder string = new StringBuilder();
        string.append(1).append(flag);
        switch (flag) {
            case 0 -> {
                byte[] b = new byte[127];
                string.append(file.getName());
                send(string.toString().getBytes(), s);
                try {
                    InputStream in;
                    in = new FileInputStream(file);
                    int j=0,i=0;
                    while (j!=-1){
                        for(i=0;i<127&&(j=in.read())!=-1;i++){
                            b[i]=(byte) j;
                        }
                        if(i!=127){
                            byte [] c=b;
                            b=new byte[i];
                            System.arraycopy(c, 0, b, 0, i);
                        }
                        send(b, s);
                    }
                    send("@".getBytes(),s);
                    in.close();
                } catch (Exception ignore) {
                }
            }
            case 1 -> {
                File[] list = file.listFiles();
                int num=0;
                if(list!=null)num = list.length;
                if (num <= 999 && num > 99) string.append(num);
                else if (num < 100 && num > 9) string.append(0).append(num);
                else if (num < 10) string.append(0).append(0).append(num);
                send(string.append(file.getName()).toString().getBytes(), s);
                if(list!=null&&num!=0) for (File f : list) {
                    send(f, s);
                }
            }
        }
    }

    public static byte[] receive(byte[] b, Socket s){
        //����128���ص����鲢��������Ҫ������
        //�����������һλΪ��@��ʱ����ʾǰ127λ������Ҫ������
        //�����������һλΪ����ʱ���м�Ϊ��Ч���ݣ�ֻ��ǰһ����
        //������Ч������ɵı��ش�

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

    public static void receive(byte[] b, Socket s, String nowPath){
        //�����ļ���
        //Ϊ�ļ�ֱ������
        //�ļ����½��ļ��н����ļ��м�������
        //ÿ�ν�����һ����Ҫ���ݵ�ǰ��ַȷ���ļ����غ�ṹ��ͬ

        try{
            switch (b[1]) {
                case '0' -> {
                    File file = new File(nowPath + "\\" + new String(b, 2, b.length - 2));
                    if (file.exists()) {
                        if (!file.delete() && !file.createNewFile()) return;
                    }
                    FileOutputStream o;
                    o=new FileOutputStream(file);
                    while((b = receive(new byte[128], s)).length!=1) {
                        o.write(b);
                        o.flush();
                    }
                    if(b[0]!='@')   o.write(b);
                    o.flush();
                    o.close();
                }
                case '1' -> {
                    String string = nowPath + "\\" + new String(b, 5, b.length - 5);
                    File f = new File(string);
                    if (!f.exists()) f.mkdir();
                    int num = 0;
                    for (int i = 2; i < 5; i++) {
                        num = num * 10 + b[i] - '0';
                    }
                    for (int i = 0; i < num; i++) {
                        b = receive(new byte[128], s);
                        receive(b, s, string);
                    }
                }
            }
        }catch (Exception ignored){}
    }

    public static void getAllFile (File fileInput, List< String > allFileList, int n){
        //�������ܣ���ȡ�ļ����������ļ����ļ�������������㷨
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

    public static void print(JTextArea jTextArea,String string){
        //��JTexArea�д�ӡstring�����ݲ�����
        jTextArea.append(string + "\r\n");
    }


    public static JFileChooser fileWindow(int model,String rootpath){
        //�½�����ѡ���ļ������ַ
        JFileChooser jfc=new JFileChooser(rootpath);
        switch (model) {
            case 1 -> {
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.setMultiSelectionEnabled(true);
                jfc.showDialog(new JLabel(), "ѡ���ϴ��Ķ���ļ�/�ļ���");
            }
            case 2 -> {
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.showDialog(new JLabel(), "ѡ�񱾵��ļ���");
            }
        }
        jfc.setVisible(true);
        return jfc;
    }

}
