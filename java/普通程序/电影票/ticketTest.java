package ��ͨ����.��ӰƱ;

public class ticketTest {
    public static void main(String[] args) {
        ticket t1=new ticket();
        ticket t2=new ticket();

        t1.setName("1�Ŵ���");
        t2.setName("2�Ŵ���");

        t1.start();t2.start();
    }
}
