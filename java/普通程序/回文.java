package 普通程序;


import java.util.Scanner;
public class 回文 {
    public static void main(String[] args){
    Scanner sc=new Scanner(System.in);
    int num=sc.nextInt();//abc
    int x=num;
    int temp;
    int out=0;
    while(x!=0){
        temp=x%10;
        x/=10;
        out=out*10+temp;
    }
    sc.close();
    System.out.println(out);
    System.out.println(out==num);
}
}