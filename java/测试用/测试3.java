package 测试用;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.*;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class 测试3{

    public static void main(String[] args) throws  Exception{
//        String fileName="E:\\个人编程\\代码-全\\java\\curriculumDesign\\severSave\\3\\3.png";
//        FileImageInputStream inputStream = new FileImageInputStream(new File(fileName));
        File file=new File("E:\\个人编程\\代码-全\\java\\curriculumDesign\\severSave\\3\\4.png");
        File file2=new File("E:\\个人编程\\代码-全\\java\\curriculumDesign\\severSave\\3\\3.png");

        file.createNewFile();

        OutputStream in2 = new FileOutputStream(file);
        InputStream inputStream=new FileInputStream(file2);
        int b=0;
        byte []bytes=new byte[1111111];
        int i=0;
        while((b = inputStream.read()) != -1 ){
//            inputStream.read(b,0,128);
//            in2.write(b,0,128);
//            in2.flush();
            bytes[i]=(byte)b;
            i++;
        }
        in2.write(bytes,0,i);
        in2.flush();
        System.out.println(file.length());
        System.out.println(file.getName().substring(0,file.getName().lastIndexOf(".")));

    }
}
