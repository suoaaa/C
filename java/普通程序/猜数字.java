package 普通程序;


import java.util.Random;
import java.util.Scanner;

public class 猜数字 {
    public static void main(String[] args) {
        Random r=new Random();
        int num=r.nextInt(99)+1;
        Scanner sc=new Scanner(System.in);
        int guess=0;
        boolean flag=true;
        while(flag){
            guess=sc.nextInt();
            if(guess==num) flag=false;
            if(guess>num) System.out.println("猜大了，请猜小一点");
            else System.out.println("猜小了，请猜大一点");
        }
        System.out.println("恭喜你猜对了，num的值是"+num);
        sc.close();
    }
}
