package 普通程序;

public class AutoModel {
    private AutoModel(){};
    public static int inverse(int m,int p) {//求m在模p情况下的逆元
        for(int i=1;i<p;i++){
            if(m*i%p==1) return i;
        }
        return 0;
    }
    public static boolean isCoprime(int m,int n) {//检测m和n是否互素
        if(isPrime(m)==false||isPrime(n)==false) return false;
        return false;
    }
    public static boolean isPrime(int p) {//检测
        return false;
    }
    public static void main(String[] args) {
        System.out.println(inverse(2, 9));
    }
}
