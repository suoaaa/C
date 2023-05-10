package 测试用;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class 测试3{




    public static void main(String[] args) throws  Exception{
        JFileChooser jfc=new JFileChooser("E:\\个人编程\\代码-全\\java\\curriculumDesign\\clientSave");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.setMultiSelectionEnabled(true);
        jfc.showDialog(new JLabel(), "选择上传的多个文件/文件夹");jfc.setVisible(true);
        File[] file=jfc.getSelectedFiles();
        for(File f:file){
            System.out.println(f.isFile());
            System.out.println(f.getPath());
        }
    }
}