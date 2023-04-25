package 普通程序.电影票;

public class ticket extends Thread{
    static Object obj=new Object();
    static int num=0;
    public void run(){
        while(true){
            synchronized(obj){
                if(num<10){
                    try {
                        Thread.sleep(100);
                        num++;
                        System.out.println(getName()+"正在售卖第"+num+"张票");
                        }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                        } 
                }else break;
            }
        }
        
    }
}
