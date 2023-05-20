package 课程设计.allCode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class mainFunc {
    public static void main(String[] args) {
        //创建多线程，并添加客户端与服务端类的创建
        //因为服务端与客户端有许多阻塞方法，因此将客户端与服务端作为不同线程执行

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
