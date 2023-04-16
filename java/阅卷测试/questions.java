package 阅卷测试;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

//questions类储存题目信息
//allString成员储存题库所有题目、选项以及答案
//question,A,B,C,answer成员储存选择出来的题目的题目、选项、答案
//index成员负责初始化时获得新的题目，index储存的是题库中题目的序号(从小到大)，进而组建不同的试卷
//index成员的数据通过random函数获得

public class Questions {
    static String[] allStrings;                 //使用静态成员变量减小内存占用以及重新组卷的时间占用
    String[] A, B, C;                           //成员名称代其意
    String[] question;
    String[] answer;
    String[] myAnswer;                          //储存用户答题情况
    int []index;                                //用于随机抽取题库题目的索引
    String path;                                //题库路径
    int score=0;                                //总分数
    Date beginTime=new Date();                  //做题时间
    boolean isWrite=false;                      //判断是否将此次做题情况打印到txt文件中

    public Questions(String path) {
        this.path=path;
        if(allStrings==null) {                  //题库为空证明是第一次组卷，首先获取题库，初始化成员变量，定义变量长度
            getAllString(path);                 //之后的每次组卷不需要重新定义长度，只需要改变变量的值
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

    private void getAllString(String path) {   //方法将所有题目储存到allString成员中
        File f=new File(path,"题库.txt");
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

    public void getNewQuestions(){              //产生随机index序列，并获取题库中对应题目、答案、选项并初始化
        beginTime=new Date();
        Random r=new Random();
        for(int i=0;i<8;i++){
            index[i]=r.nextInt(10)+i*10;    //从题库的题目序号中随机获取题号，对应的题目作为新的试卷的题目
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

    public int getScore(){                      //获取分数
        score=0;
        for(int i=0;i<8;i++){
            if(answer[i].equals(myAnswer[i]))   score=score+i/6*10+10;
        }
        return score;
    }

    public void input(){                        //将试卷信息与答题情况打印进txt文件
        if(isWrite==true) return;
        else isWrite=true;
        File f=new File(path,"试卷.txt");
        FileWriter fw;
        Date endTime=new Date();
        try {
            fw=new FileWriter(f,true);
            fw.write(
                "\n =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n"+
                "                                                                                  \n"+
                "                                  开学考试试题                                    \n"+
                "                                                                                  \n"+
                "   本次考试分为两节：前六题为单项选择题，每小题十分；后两题为填空题，每小题20分。   \n"+
                "                                                                                  \n"+
                "                     考试开始时间："+beginTime.toString()+"                       \n"+
                "                                                                                  \n"+
                "                       交卷时间："+endTime.toString()+"                           \n"+
                "                                                                                  \n"+
                "                               本次考试得分:"+getScore()+"                        \n"+
                "                                                                                  \n"+
                " ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ \n"+"\n"+
                "试卷详情：                                                                        \n");
            for(int i=0;i<6;i++){
                fw.write("第"+(i+1)+"题、"+question[i]);
                fw.write(A[i]+"\t\t"+B[i]+"\t\t"+C[i]+"\t\t正确答案："+answer[i]+"\t\t"+"你的答案："+myAnswer[i]+"\n");
            }
            for(int i=6;i<8;i++){
                fw.write("第"+(i+1)+"题、"+question[i]);
                fw.write("正确答案："+answer[i]+"\t\t"+"你的答案："+myAnswer[i]+"\n");
            }
                fw.flush();
                fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
