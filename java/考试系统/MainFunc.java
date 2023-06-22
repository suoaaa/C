package 考试系统;

import java.io.File;

public class MainFunc {
    public static void main(String[] args) {
        File file=new File("");
        String rootpath ;
        rootpath=file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("\\"));
        if(!file.exists()) file.mkdirs();

//        System.out.println(path.getAbsolutePath());
        
        new MyWindow(new Questions(rootpath));
        //使用时请按文件路径更改实参，例如笔者题库路径为java\\阅卷测试\\题库.txt
        //实参应为题库.txt文件所在文件夹
        //也可以利用题库1进行测试
    }
}
