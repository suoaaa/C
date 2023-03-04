import java.util.Random;

public class Ëæ»ú´òÂÒ {
    public static void main(String[] args) {
        int num[]={0,1,2,3,4,5};
        Random r=new Random();
        int Index=0;
        int temp=0;
        for (int i = 0; i < num.length; i++) {
            Index=r.nextInt(5);
            temp=num[i];
            num[i]=num[Index];
            num[Index]=temp;
        }
        for (int i : num) {
            System.out.println(num[i]);
        }
    }
}
