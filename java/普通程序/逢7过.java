package 普通程序;


public class 逢7过 {
    public static void main(String[] args) {
        int i=1;
        for(;i<=100;i++){
            if(i%7==0||i%10==7||(i>=70&&i<=80)){
                System.out.print("过");
            }else System.out.print(i);
            System.out.print(" ");
        }
    }
}
