package 普通程序.电影票;

public class ticketTest {
    public static void main(String[] args) {
        ticket t1=new ticket();
        ticket t2=new ticket();

        t1.setName("1号窗口");
        t2.setName("2号窗口");

        t1.start();t2.start();
    }
}
