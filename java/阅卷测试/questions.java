package �ľ����;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

//questions�ഢ����Ŀ��Ϣ
//allString��Ա�������������Ŀ��ѡ���Լ���
//question,A,B,C,answer��Ա����ѡ���������Ŀ����Ŀ��ѡ���
//index��Ա�����ʼ��ʱ����µ���Ŀ��index��������������Ŀ�����(��С����)�������齨��ͬ���Ծ�
//index��Ա������ͨ��random�������

public class Questions {
    static String[] allStrings;                 //ʹ�þ�̬��Ա������С�ڴ�ռ���Լ���������ʱ��ռ��
    String[] A, B, C;                           //��Ա���ƴ�����
    String[] question;
    String[] answer;
    String[] myAnswer;                          //�����û��������
    int []index;                                //���������ȡ�����Ŀ������
    String path;                                //���·��
    int score=0;                                //�ܷ���
    Date beginTime=new Date();                  //����ʱ��
    boolean isWrite=false;                      //�ж��Ƿ񽫴˴����������ӡ��txt�ļ���

    public Questions(String path) {
        this.path=path;
        if(allStrings==null) {                  //���Ϊ��֤���ǵ�һ��������Ȼ�ȡ��⣬��ʼ����Ա�����������������
            getAllString(path);                 //֮���ÿ�������Ҫ���¶��峤�ȣ�ֻ��Ҫ�ı������ֵ
            A=new String[6];
            B=new String[6];
            C=new String[6];
            question=new String[8];
            answer=new String[8];
            myAnswer=new String[8];
            index=new int[8];
        }
        getNewQuestions();
    };

    private void getAllString(String path) {   //������������Ŀ���浽allString��Ա��
        File f=new File(path,"���.txt");
        FileReader fr;
        char[] c;
        String s;
        try {
            fr = new FileReader(f);
            c = new char[13000];
            fr.read(c,1,c.length-1);
            s=new String(c);
            allStrings=s.split("& ");
            fr.close();
        } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void getNewQuestions(){              //�������index���У�����ȡ����ж�Ӧ��Ŀ���𰸡�ѡ���ʼ��
        beginTime=new Date();
        Random r=new Random();
        for(int i=0;i<8;i++){
            index[i]=r.nextInt(10)+i*10;    //��������Ŀ����������ȡ��ţ���Ӧ����Ŀ��Ϊ�µ��Ծ����Ŀ
        }
        for(int i=0;i<6;i++){
            question[i]=allStrings[index[i]*5];
            A[i]=allStrings[index[i]*5+1];
            B[i]=allStrings[index[i]*5+2];
            C[i]=allStrings[index[i]*5+3];
            String tmp=allStrings[index[i]*5+4];
            for(int j=0;j<tmp.length();j++){
                answer[i]=tmp.substring(0, tmp.length()-2);
            }
            myAnswer[i]="";
        }
        for(int i=6;i<8;i++){
            question[i]=allStrings[180+index[i]*2];
            String tmp = allStrings[180+index[i]*2+1];
            for(int j=0;j<tmp.length();j++){
                answer[i]=tmp.substring(0, tmp.length()-2);
            }
            myAnswer[i]="";
        }
    }

    public int getScore(){                      //��ȡ����
        score=0;
        for(int i=0;i<8;i++){
            if(answer[i].equals(myAnswer[i]))   score=score+i/6*10+10;
        }
        return score;
    }

    public void input(){                        //���Ծ���Ϣ����������ӡ��txt�ļ�
        if(isWrite==true) return;
        else isWrite=true;
        File f=new File(path,"�Ծ�.txt");
        FileWriter fw;
        Date endTime=new Date();
        try {
            fw=new FileWriter(f,true);
            fw.write(
                "\n =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n"+
                "                                                                                  \n"+
                "                                  ��ѧ��������                                    \n"+
                "                                                                                  \n"+
                "   ���ο��Է�Ϊ���ڣ�ǰ����Ϊ����ѡ���⣬ÿС��ʮ�֣�������Ϊ����⣬ÿС��20�֡�   \n"+
                "                                                                                  \n"+
                "                     ���Կ�ʼʱ�䣺"+beginTime.toString()+"                       \n"+
                "                                                                                  \n"+
                "                       ����ʱ�䣺"+endTime.toString()+"                           \n"+
                "                                                                                  \n"+
                "                               ���ο��Ե÷�:"+getScore()+"                        \n"+
                "                                                                                  \n"+
                " ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ \n"+"\n"+
                "�Ծ����飺                                                                        \n");
            for(int i=0;i<6;i++){
                fw.write("��"+(i+1)+"�⡢"+question[i]);
                fw.write(A[i]+"\t\t"+B[i]+"\t\t"+C[i]+"\t\t��ȷ�𰸣�"+answer[i]+"\t\t"+"��Ĵ𰸣�"+myAnswer[i]+"\n");
            }
            for(int i=6;i<8;i++){
                fw.write("��"+(i+1)+"�⡢"+question[i]);
                fw.write("��ȷ�𰸣�"+answer[i]+"\t\t"+"��Ĵ𰸣�"+myAnswer[i]+"\n");
            }
                fw.flush();
                fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
