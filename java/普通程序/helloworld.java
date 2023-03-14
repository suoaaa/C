package 普通程序;

import java.util.Random;
import java.util.Scanner;

public class helloworld{
	public static void main(String[] args){
		float ad=3.0F;
		if(ad==3);
		System.out.println("普通程序.helloworld");
		System.out.println(555);
		System.out.println(-1.32);
		System.out.println("男");
		System.out.println(true);
		System.out.println("张三"+'\t'+"学号");
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
		System.out.println("我的年龄是"+a);
		a=100;
		System.out.println(a <<2);
		System.out.println(a>>2);//25
		a=-100;
		System.out.println(a>>2);//对原码进行移位运算-25
		// switch (a) {
		// 	case 1 -> {a=1;}
		// 	default -> {a=2;}
		// }//						平板的jdk有bug
		// switch (a) {
		// 	case 1,2:a=2;
		// 	break;}	//也有bug


		//无限循环
		// while(true){
		// 	System.out.println("学习");
		// }
		Random r=new Random();
		int num=r.nextInt(9);
		System.out.println(num);

		sc.close();
	}
}