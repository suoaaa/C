package ��ͨ����.��ӰƱ;

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
                        System.out.println(getName()+"����������"+num+"��Ʊ");
                        }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                        } 
                }else break;
            }
        }
        
    }
}
