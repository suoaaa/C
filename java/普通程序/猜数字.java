package ��ͨ����;


import java.util.Random;
import java.util.Scanner;

public class ������ {
    public static void main(String[] args) {
        Random r=new Random();
        int num=r.nextInt(99)+1;
        Scanner sc=new Scanner(System.in);
        int guess=0;
        boolean flag=true;
        while(flag){
            guess=sc.nextInt();
            if(guess==num) flag=false;
            if(guess>num) System.out.println("�´��ˣ����Сһ��");
            else System.out.println("��С�ˣ���´�һ��");
        }
        System.out.println("��ϲ��¶��ˣ�num��ֵ��"+num);
        sc.close();
    }
}
