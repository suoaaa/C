package ��ͨ����;

import java.util.Scanner;

public class ����ת�ַ��� {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int []arr=new int[3];
        for (int i=0;i<3;i++) {
            arr[i]=sc.nextInt();
        }
        String out=arrToString(arr);
        System.out.println(out);
        sc.close();
    }

    public static String arrToString(int[] arr) {
        if(arr==null) return "";
        if(arr.length==0) return "[]";
        String out="[";
        for (int i=0;i< arr.length;i++) {
            if(i==arr.length-1)	out=out+arr[i]+"]";
            else	out=out+arr[i]+",";
        }
        return out;
    }
}
