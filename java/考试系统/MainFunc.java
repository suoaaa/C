package ����ϵͳ;

import java.io.File;

public class MainFunc {
    public static void main(String[] args) {
        File file=new File("");
        String rootpath ;
        rootpath=file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("\\"));
        if(!file.exists()) file.mkdirs();

//        System.out.println(path.getAbsolutePath());
        
        new MyWindow(new Questions(rootpath));
        //ʹ��ʱ�밴�ļ�·������ʵ�Σ�����������·��Ϊjava\\�ľ����\\���.txt
        //ʵ��ӦΪ���.txt�ļ������ļ���
        //Ҳ�����������1���в���
    }
}
