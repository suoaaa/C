import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class A
{public static void main(String[]args)
{T k=new T("多线程时钟");}
}
class T extends Frame
{public T(String s)
{super(s);
addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e) 
{dispose();System.exit(0);}
});//关闭窗口
setLayout(new GridLayout(1,2));//设置布局1行3列
R x=new R("北京","GMT+9:16");//创建时钟
R y=new R("温哥华","GMT-8");//创建时钟
R z=new R("东京","GMT+8");//创建时钟
add(x);add(y);add(z);//添加时钟到框架中
setSize(350,160);//设置框架宽、高
setVisible(true);//显示框架
}
}
class R extends Canvas implements Runnable
{private int s=0;//存放所在时区时间的秒数值
private String c;//定义所在时区的城市
private GregorianCalendar d;//声明格列高利历(阳历)类对象
Thread t;//定义线程对象
public R(String m,String n)
{c=m;//设置画布中显示的城市名字
d=new GregorianCalendar(TimeZone.getTimeZone(n));
t=new Thread(this);
t.start();//启动线程
setSize(125,125);//设置画布大小
setBackground(Color.black);//设置画布背景颜色为黑色
}
//重写父类的 paint()方法绘制时钟图形
public void paint(Graphics g)
{Graphics2D a=(Graphics2D)g;//把父类对象强制转化为子类对象
BasicStroke b=new BasicStroke(2.0f);
a.setStroke(b);//设置画笔画线的宽度
a.setColor(Color.green);//设置画笔的颜色
a.drawOval(0,0,100,100);//画时钟的外框
//求小时、分钟和秒钟对应的角度，以便绘图
double hh=2*Math.PI*(s-3*60*60)/(12*60*60);
double mm=2*Math.PI*(s-16*60)/(60*60);
double ss=2*Math.PI*(s-16)/(60);
b=new BasicStroke(5.0f);
a.setStroke(b);//设置画笔画线宽度画小时指针
a.drawLine(50,50,50+(int)(30*Math.cos(hh)),50+(int)(30*Math.sin(hh)));
b=new BasicStroke(3.0f);
a.setStroke(b);//设置画笔画线宽度画分钟指针
a.drawLine(50,50,50+(int)(40*Math.cos(mm)),50+(int)(40*Math.sin(mm)));
b=new BasicStroke(1.0f);
a.setStroke(b);//设置画笔画线宽度画秒钟指针
a.drawLine(50,50,50+(int)(45*Math.cos(ss)),50+(int)(45*Math.sin(ss)));
a.setColor(Color.red);//设置画笔的颜色
a.drawString(c,35,116);//显示城市名
}
public void timeElapsed()//得到当前时间
{d.setTime(new Date());
//将当前时间的时、分、秒转化为用秒表示的时间
s=d.get(Calendar.HOUR)*60*60+d.get(Calendar.MINUTE)*60+d.get(Calendar.SECOND);
}
public void run()//实现Runnable接口中的方法，每隔300毫秒获取当前时间并显示
{try{while(true)
{t.sleep(300);//休眠300毫秒
timeElapsed();//调用方法获取当前新的时间
repaint();//调用paint()方法，重绘时钟显示界面
}
}
catch(InterruptedException e){}
}
}
