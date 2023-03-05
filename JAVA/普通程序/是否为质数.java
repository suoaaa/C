package 普通程序;


import java.util.Scanner;

public class 是否为质数{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int num=sc.nextInt();
        boolean flag=true;
        for(int i=2;i*i<=num;i++){
            if(num%i==0) {
                flag=false;
                break;
            }
        }
        if(flag)    System.out.println(num+"是质数");
        else System.out.println(num+"不是质数");
        sc.close();
    }
}