//数字时钟
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class A
{public static void main(String[]args)
{D d=new D();}
}
class D extends Frame
{D()
{super("数字时钟");
addWindowListener(new WindowAdapter(){
public void windowdosing(WindowEvent e){
dispose();System.exit(0);}
});
B b=new B();
Thread t=new Thread(b);
t.start();
add("Center",b);
setSize(380,280);
setVisible(true);
}
}
class B extends Canvas implements Runnable
{int y,m,d,w,h,n,s;
B(){setBackground(Color.black);}
public void paint(Graphics g)
{String x=y+"年"+m+"月"+d+"日";
String y="星期"+w;
String z=h+":"+n+":"+s;
g.setFont(new Font("隶书",Font.BOLD,50));
g.setColor(Color.green);
g.drawString(x,20,50);
g.drawString(y,20,120);
g.drawString(z,20,200);
}
public void run()
{while(true)
{Calendar r=Calendar.getInstance();
y=r.get(Calendar.YEAR);
m=r.get(Calendar.MONTH);
d=r.get(Calendar.DAY_OF_MONTH);
w=r.get(Calendar.DAY_OF_WEEK);
h=r.get(Calendar.HOUR);
n=r.get(Calendar.MINUTE);
s=r.get(Calendar.SECOND);
repaint();
try{Thread.sleep(300);}
catch(InterruptedException e){}
}
}
}
