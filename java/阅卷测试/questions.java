package 阅卷测试;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

//questions类储存题目信息
//allString成员储存题库所有题目、选项以及答案
//question,A,B,C,answer成员储存选择出来的题目的题目、选项、答案
//index成员负责初始化时获得新的题目，index储存的是题库中题目的序号(从小到大)，进而组建不同的试卷
//index成员的数据通过random函数获得

public class questions {
    static String[] allStrings;                 //使用静态成员变量减小内存占用以及重新组卷的时间占用
    String[] A, B, C;
    String[] question;
    String[] answer;
    int []index;
    
    public questions() {
        if(allStrings==null) {                  //题库为空证明是第一次组卷，首先获取题库，初始化成员变量，定义变量长度
            getAllString();                     //之后的每次组卷不需要重新定义长度，只需要改变变量的值
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
        File f=new File("E:\\个人编程\\代码-全\\java\\阅卷测试\\题库.txt");
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
            index[i]=r.nextInt(10)+i*10;    //从题库的题目序号中随机获取题号，对应的题目作为新的试卷的题目
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
