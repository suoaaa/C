package �ľ����;

import java.sql.Time;
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
        }
        getNewQuestions();
    };

    private void getNewQuestions(){
        index=new int[8];
        Random r=new Random();
        for(int i=0;i<8;i++){
            index[i]=r.nextInt(10)+i*10;    //��������Ŀ����������ȡ��ţ���Ӧ����Ŀ��Ϊ�µ��Ծ����Ŀ
        }
    }
    private void getAllString(){

    }
}
