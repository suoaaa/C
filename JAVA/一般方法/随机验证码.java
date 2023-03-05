package 一般方法;

import java.util.Random;

public class 随机验证码 {
    public static void main(String[] args) {
        System.out.println( );
        System.out.println(securityCode(5));
    }
    public static char[] securityCode(int n) {
        char []code=new char[n];
        char allCode[]=new char[10+26+26];                  //记录验证码中的全部元素
        for (int i = 0; i < allCode.length; i++) {          //for循环给数组元素赋值：0-9，a-z，A-Z
            if(i<10)    allCode[i]=(char)('0'+i);
            if(i<36&&i>=10) allCode[i]=(char)('a'+i-10);
            if(i>=36) allCode[i]=(char)('A'+i-36);
            //System.out.print(allCode[i]+" ");
        }
        Random r=new Random();                              
        for (int i=0;i<n;i++) {
            code[i]=allCode[r.nextInt(62)];            //使用随机数获得最后输出验证码的个元素
        }
        return code;
    }
}
