import java.util.Scanner;

public class 求商和余数 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int dividerd=0,divisor=0,quotient=0,remainder=0;
        dividerd=sc.nextInt();
        divisor=sc.nextInt();
        int temp=dividerd;
        while(temp>=divisor){
            temp-=divisor;
            quotient++;
        }
        remainder=temp;
        System.out.println(quotient);
        System.out.println(remainder);
        sc.close();
    }
}
