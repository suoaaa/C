package �ľ����;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

//questions�ഢ����Ŀ��Ϣ
//allString��Ա�������������Ŀ��ѡ���Լ���
//question,A,B,C,answer��Ա����ѡ���������Ŀ����Ŀ��ѡ���
//index��Ա�����ʼ��ʱ����µ���Ŀ��index��������������Ŀ�����(��С����)�������齨��ͬ���Ծ�
//index��Ա������ͨ��random�������

public class questions {
    static String[] allStrings;                 //ʹ�þ�̬��Ա������С�ڴ�ռ���Լ���������ʱ��ռ��
    String[] A, B, C;
    String[] question;
    String[] answer;
    int []index;
    
    public questions() {
        if(allStrings==null) {                  //���Ϊ��֤���ǵ�һ��������Ȼ�ȡ��⣬��ʼ����Ա�����������������
            getAllString();                     //֮���ÿ�������Ҫ���¶��峤�ȣ�ֻ��Ҫ�ı������ֵ
            A=new String[6];
            B=new String[6];
            C=new String[6];
            question=new String[8];
            answer=new String[8];
            index=new int[8];
        }
        getNewQuestions();
    };

    private void getAllString() {
        File f=new File("E:\\���˱��\\����-ȫ\\java\\�ľ����\\���.txt");
        FileReader fr;        
        char[] c;
        String s;
        try {
            fr = new FileReader(f);
            c = new char[13000];
            fr.read(c,1,c.length-1);
            s=new String(c);
            // s=s.replace("\n", "&&");
            allStrings=s.split("&");
            // for(int i=0;i<allStrings.length;i++){
            //     allStrings[i].replace("\n", "");
            // }
            fr.close();
        } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void getNewQuestions(){
        Random r=new Random();
        for(int i=0;i<8;i++){
            index[i]=r.nextInt(10)+i*10;    //��������Ŀ����������ȡ��ţ���Ӧ����Ŀ��Ϊ�µ��Ծ����Ŀ
        }
        for(int i=0;i<6;i++){
            question[i]=allStrings[index[i]*5];
            A[i]=allStrings[index[i]*5+1];
            B[i]=allStrings[index[i]*5+2];
            C[i]=allStrings[index[i]*5+3];
            answer[i]=allStrings[index[i]*5+4];
            System.out.print("1   "+question[i]);
            System.out.print("2   "+A[i]);
            System.out.print("3   "+B[i]);
            System.out.print("4   "+C[i]);
            System.out.print("5   "+answer[i]);
        }
        for(int i=6;i<8;i++){
            question[i]=allStrings[180+index[i]*2];
            answer[i]=allStrings[180+index[i]*2+1];
            System.out.print("6   "+question[i]);
            System.out.print("7   "+answer[i]);
        }
    }
}
