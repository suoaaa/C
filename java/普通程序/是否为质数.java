package ��ͨ����;


import java.util.Scanner;

public class �Ƿ�Ϊ����{
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
        if(flag)    System.out.println(num+"������");
        else System.out.println(num+"��������");
        sc.close();
    }
}