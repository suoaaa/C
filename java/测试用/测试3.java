package 测试用;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.*;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
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
        byte []bytes=new byte[111111];
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

//        inputStream.read(head);
//
//        Charset charset=Charset.
//
//        String charsetName = "U";//或GB2312，即ANSI
//        if (head[0] == -1 && head[1] == -2 ) //0xFFFE
//            charsetName = "UTF-16";
//        else if (head[0] == -2 && head[1] == -1 ) //0xFEFF
//            charsetName = "Unicode";//包含两种编码格式：UCS2-Big-Endian和UCS2-Little-Endian
//        else if(head[0]==-27 && head[1]==-101 && head[2] ==-98)
//            charsetName = "UTF-8"; //UTF-8(不含BOM)
//        else if(head[0]==-17 && head[1]==-69 && head[2] ==-65)
//            charsetName = "UTF-8"; //UTF-8-BOM
//        System.out.println(charsetName);
//        System.out.println(head[0]+""+head[1]+""+head[2]);
//        System.out.println(fileName.substring(fileName.lastIndexOf(".")));
    }
}
