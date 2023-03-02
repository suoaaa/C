import java.util.Scanner;
public class ╩ьнд {
    public static void main(String[] args){
    Scanner sc=new Scanner(System.in);
    int num=sc.nextInt();//abc
    int x=num;
    int temp=0;
    int out=0;
    // int a=num/100;
    // int b=num/10%10;
    // int c=num;
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