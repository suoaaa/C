package 阅卷测试;

import java.io.FileReader;
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

    private void getNewQuestions(){
        Random r=new Random();
        for(int i=0;i<8;i++){
            index[i]=r.nextInt(10)+i*10;    //从题库的题目序号中随机获取题号，对应的题目作为新的试卷的题目
        }
    }
    private void getAllString(){
        FileReader f=new FileReader(null)
    }
}
