package ��ͨ����;

import java.util.Scanner;

public class ���ת�� {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int money=1;
        System.out.println("������һ����λ�����ڵ���������");
        while(true){
            money=sc.nextInt();
            if(money>0&&money<9999999) break;
            else System.out.println("����������������룺");
        }
        String out=change(money);
        System.out.println("������ת��Ϊ��д���Ϊ��");
        System.out.println(out);
        sc.close();
    }

    public static String change(int num) {
        String out="Ԫ";
        String arr="";
        int length=0;
        while(num>0){
            arr=(num%10)+arr;
            num=num/10;
            length++;
        }
        char []index={'\0','ʰ','��','Ǫ','��','ʰ','��','Ǫ'};
        char []bigNum={'��','Ҽ','��','�q','��','��','½','��','��','��'};
        for(int i=length-1;i>=0;i--){
            if((i-1>=0&&arr.charAt(i)-'0'!=0)||i==0||i==length-1)	out=index[length-i-1]+out;
            if((i==length-1&&arr.charAt(i)=='0')||(arr.charAt(i)=='0'&&arr.charAt(i+1)=='0')){}
            else out=bigNum[arr.charAt(i)-'0']+out;
        }
        return out;
    }
}
