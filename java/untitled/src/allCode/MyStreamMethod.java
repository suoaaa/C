package allCode;

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
            BufferedOutputStream o ;
            o= new BufferedOutputStream(s.getOutputStream());
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
        //字节头1位为：1:确定是上传文件
        //字节头2位为：确定是文件或文件夹，1：文件，2：文件夹
        //若为文件夹，三位确定size-文件夹中文件数量,后为文件名

        //若为文件，则后若干位为文件名称
        //之后的若干比特流传输文件内容，比特头无特殊含义
        //当文件到达末尾，则在文件发送结束后在发送一个只有一位‘@’的比特流作为结束标志

        int flag=0;
        if(file.exists()) {
            if(file.isFile())           flag = 1;
            if (file.isDirectory())     flag = 2;
        }
        //判断文件是否存在
        StringBuilder string = new StringBuilder();
        string.append(1).append(flag);
        switch (flag) {
            case 1 -> {
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
                } catch (Exception ignore) {}
            }
            case 2 -> {
                File[] list = file.listFiles();
                int num=0;
                if(list!=null)num = list.length;
                if (num <= 999 && num > 99) string.append(num);
                else if (num < 100 && num > 9) string.append(0).append(num);
                else if (num < 10) string.append(0).append(0).append(num);
                string.append(file.getName());
                send(string.toString().getBytes(), s);
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
                i--;}
            int n;
            if(i==b.length-1) n=1;
            else n=Integer.parseInt(num.toString());
            ret=new byte[128-n];
            for(i=0;i<128-n;i++){
                ret[i]=b[i];}
        }catch (Exception ignored){}
        return ret;
    }

    public static void receive(byte[] b, Socket s, String nowPath){
        //接收文件夹
        //为文件直接下载
        //文件夹新建文件夹进入文件夹继续接收
        //每次进入下一级都要传递当前地址确保文件下载后结构相同

        try{
            if(b[0]=='0') return;
            switch (b[1]) {
                case '1' -> {
                    String string = nowPath + "\\" + new String(b, 2, b.length - 2);
                    File file = new File(string);
                    if (file.exists()) {
                        int i=1;
                        String last=string.substring(string.lastIndexOf("."));
                        string = string.substring(0,string.lastIndexOf("."))+"-副本";
                        while((file=new File(string+i+last)).exists()){
                            i++;
                        }
                        file.createNewFile();
                    }
                    FileOutputStream o;
                    o=new FileOutputStream(file);
                    while((b = receive(new byte[128], s)).length==127) {
                        o.write(b);
                        o.flush();
                    }
                    if(b[b.length-2]!='@')   o.write(b);
                    o.flush();
                    o.close();
                    receive(new byte[128],s);
                }
                case '2' -> {
                    String string = nowPath + "\\" + new String(b, 5, b.length - 5);
                    File f = new File(string);
                    if (!f.exists()) f.mkdirs();
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
        //方法功能：获取文件夹内所有文件的文件名，广度优先算法
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

    public static void print(JTextArea jTextArea,String string){
        //在JTexArea中打印string的内容并换行
        jTextArea.append(string + "\r\n");
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

}
