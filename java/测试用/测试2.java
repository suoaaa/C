package 测试用;

import java.io.*;

public class 测试2 {
    public static void main(String[] args) throws IOException{
        long start = System.currentTimeMillis();
        测试2.copy();
        long end = System.currentTimeMillis();
        //打印运行时间
        System.out.println("time :"+(end-start));

    }
    //字节流复制
    public static void copy() {
        //此处在try外创建引用，在try内进行初始化，能避免finally块中对象引用不了的问题；
        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try{
            fis = new FileInputStream("E:\\个人编程\\代码-全\\java\\curriculumDesign\\severSave\\3\\3.png");
            fos = new FileOutputStream("E:\\个人编程\\代码-全\\java\\curriculumDesign\\severSave\\3\\4.png");
            bis =  new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);
            int by = 0;
            while ((by = bis.read()) != -1){
                bos.write(by);
            }
        }catch (IOException e){
            System.out.println(e);
        }finally {
            try{
                //此处close()要加判断是因为，初始化可能不成功，所以对fw操作会产生异常，在finally中必须对每个流对象先进行判断再关闭。
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }catch (IOException e){
                System.out.println(e);
            }
        }
    }
}

