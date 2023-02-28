import java.util.Scanner;
public class helloworld{
	public static void main(String[] args){
		System.out.println("helloworld");
		System.out.println(555);
		System.out.println(-1.32);
		System.out.println('男');
		System.out.println(true);
		System.out.println("张三"+'\t'+"学号");
		int a=10;
		System.out.println(a);
		int b=-5;
		System.out.println(a+b);
		long c=12345678999L;
		float d=123.123F;
		System.out.println(d);
		System.out.println(c);

		Scanner sc =new Scanner(System.in);
		a=sc.nextInt();
		System.out.println(a);
		
	}
}