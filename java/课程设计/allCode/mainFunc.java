package �γ����.allCode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class mainFunc {
    public static void main(String[] args) {
        //�������̣߳�����ӿͻ�����������Ĵ���
        //��Ϊ�������ͻ��������������������˽��ͻ�����������Ϊ��ͬ�߳�ִ��

        ExecutorService pool= Executors.newCachedThreadPool();
        pool.execute(new Runnable(){
            public void run(){
                try {new MySever();}
                catch (Exception ignored) {}
            }});

        pool.execute(new Runnable(){
            public void run(){
                try {new MyClient();}
                catch (Exception ignored) {}
            }});
    }
}
