package ��ͨ����;

import java.util.Random;
import java.util.Scanner;

public class helloworld{
	public static void main(String[] args){
		float ad=3.0F;
		if(ad==3);
		System.out.println("��ͨ����.helloworld");
		System.out.println(555);
		System.out.println(-1.32);
		System.out.println("��");
		System.out.println(true);
		System.out.println("����"+'\t'+"ѧ��");
		int a=18;
		System.out.println(a);
		int b=-5;
		System.out.println(a+b);
		long c=12345678999L;
		float d=123.123F;
		System.out.println(d);
		System.out.println(c);
		Scanner sc =new Scanner(System.in);
		System.out.println(a);

		System.out.println("123"+123);
		System.out.println("123"+"123");
		System.out.println(1+23+"123"+123);
		System.out.println("�ҵ�������"+a);
		a=100;
		System.out.println(a <<2);
		System.out.println(a>>2);//25
		a=-100;
		System.out.println(a>>2);//��ԭ�������λ����-25
		// switch (a) {
		// 	case 1 -> {a=1;}
		// 	default -> {a=2;}
		// }//						ƽ���jdk��bug
		// switch (a) {
		// 	case 1,2:a=2;
		// 	break;}	//Ҳ��bug


		//����ѭ��
		// while(true){
		// 	System.out.println("ѧϰ");
		// }
		Random r=new Random();
		int num=r.nextInt(9);
		System.out.println(num);

		sc.close();
	}
}