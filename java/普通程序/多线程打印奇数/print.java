package 普通程序.多线程打印奇数;

public class print extends Thread{
    static Object obj=new Object();
    static int num=0;
    public void run(){
        while(true){
            synchronized(obj){
                if(num>=100){
                    break;
                }else {
                    if(num%2==1){ 
                        System.out.print(" "+num+" ");
                        num++;
                    }else num++;
                }
            }
        }
    }
}