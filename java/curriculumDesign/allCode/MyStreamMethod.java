package curriculumDesign.allCode;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.List;

//本类中共实现了个方法
//包括两个send方法：基础发送单个数据包（128比特），以及发送文件/文件夹（多个基础send组合）
//包括两个receive方法：基础接收处理单个数据包（128比特），以及接收文件/文件夹（多个基础receive组合）
//获取给定文件夹下的所有文件，储存在实参中的字符串list中

public final class MyStreamMethod {
    private MyStreamMethod(){}

    public static void send(byte[] b, Socket s){
        //传输b中内容，不足127比特则中间填‘@’，最后几位标记无效位的位数
        //位127比特则最后一位为’@‘，之前全为有效位
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

    public static void send(File file, Socket s){
        //字节头1位为1:确定是上传文件
        //自己头2位：确定是文件或文件夹，0：文件，1：文件夹
        //若干位确定size（文件夹中文件数量/文件比特大小）
        //后为文件名
        //之后文件传输文件内容比特流，比特头无特殊含义
        int flag=3;
        if(file.exists()&& file.isFile()) flag=0;
        if(file.exists()&& file.isDirectory()) flag=1;
        StringBuilder string = new StringBuilder();
        string.append(1).append(flag);
        switch (flag) {
            case 0 -> {
                byte[] b = new byte[127];
                long size = file.length();    //最大值为9223372036854775807
                long m = size % 127;            //最大值为72624976668147841---17位
                long n = size / 127;
                if (n != 0) m++;
                long i = m;
                int j = 0;
                for (; i > 0; j++) i /= 10;
                string.append("0".repeat(17 - j)).append(m).append(file.getName());
                send(string.toString().getBytes(), s);
                BufferedInputStream in;
                try {
                    in = new BufferedInputStream(new FileInputStream(file));
                    for (i = 0; i < m - 1; i++) {
                        in.read(b, 0, 127);
                        send(b, s);
                    }
                    if (n == 0) {
                        in.read(b, 0, 127);
                        send(b, s);
                    } else {
                        b = new byte[(int) n];
                        in.read(b, 0, (int) n);
                        send(b, s);
                    }
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
        //接收128比特的数组并分析出需要的数据
        //当比特流最后一位为‘@’时，表示前127位都是需要的内容
        //当比特流最后一位为数字时，中间为无效内容，只需前一部分
        //返回有效数据组成的比特串
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
        //接收文件夹
        //为文件直接下载
        //文件夹新建文件夹进入文件夹继续接收
        //每次进入下一级都要传递当前地址确保文件下载后结构相同
        try{
            switch (b[1]) {
                case '0' -> {
                    BufferedOutputStream o;
                    File f = new File(nowPath + "\\" + new String(b, 19, b.length - 19));
                    if (f.exists()) {
                        if (!f.delete() && !f.createNewFile()) return;
                    }
                    o = new BufferedOutputStream(new FileOutputStream(f));
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
        // 获取文件列表
        File[] fileList = fileInput.listFiles();
        assert fileList != null;
        StringBuilder string = new StringBuilder();
        string.append("  ".repeat(Math.max(0, n)));
        for (File file : fileList) {
            if (file.isDirectory()) {
                // 递归处理文件夹
                allFileList.add(string + file.getName() + "\\");
                getAllFile(file, allFileList, n + 1);
            } else {
                // 如果是文件则将其加入到文件名数组中
                allFileList.add(string + file.getName());
            }
        }
    }

    public static JFileChooser fileWindow(int model,String rootpath){
        //新建窗口选择文件储存地址
        JFileChooser jfc=new JFileChooser(rootpath);
        switch (model) {
            case 1 -> {
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.setMultiSelectionEnabled(true);
                jfc.showDialog(new JLabel(), "选择上传的多个文件/文件夹");
            }
            case 2 -> {
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.showDialog(new JLabel(), "选择本地文件夹");
            }
        }
        jfc.setVisible(true);
        return jfc;
    }

    public static void print(JTextArea jTextArea,String string){
        //在JTexArea中打印string的内容并换行
        jTextArea.append(string + "\r\n");
    }


}
