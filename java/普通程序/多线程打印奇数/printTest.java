package 普通程序.多线程打印奇数;

public class printTest {
    public static void main(String[] args) {
        print p1=new print();
        print p2=new print();

        p1.start();p2.start();
    }
}
