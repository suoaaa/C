package ��ͨ����;

public class AutoModel {
    private AutoModel(){};
    public static int inverse(int m,int p) {//��m��ģp����µ���Ԫ
        for(int i=1;i<p;i++){
            if(m*i%p==1) return i;
        }
        return 0;
    }
    public static boolean isCoprime(int m,int n) {//���m��n�Ƿ���
        if(isPrime(m)==false||isPrime(n)==false) return false;
        return false;
    }
    public static boolean isPrime(int p) {//���
        return false;
    }
    public static void main(String[] args) {
        System.out.println(inverse(2, 9));
    }
}
