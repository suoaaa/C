package ������;

import java.io.*;

public class ����2 {
    public static void main(String[] args) throws IOException{
        long start = System.currentTimeMillis();
        ����2.copy();
        long end = System.currentTimeMillis();
        //��ӡ����ʱ��
        System.out.println("time :"+(end-start));

    }
    //�ֽ�������
    public static void copy() {
        //�˴���try�ⴴ�����ã���try�ڽ��г�ʼ�����ܱ���finally���ж������ò��˵����⣻
        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try{
            fis = new FileInputStream("E:\\���˱��\\����-ȫ\\java\\curriculumDesign\\severSave\\3\\3.png");
            fos = new FileOutputStream("E:\\���˱��\\����-ȫ\\java\\curriculumDesign\\severSave\\3\\4.png");
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
                //�˴�close()Ҫ���ж�����Ϊ����ʼ�����ܲ��ɹ������Զ�fw����������쳣����finally�б����ÿ���������Ƚ����ж��ٹرա�
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

