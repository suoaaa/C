import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class A
{public static void main(String[]args)
{T k=new T("���߳�ʱ��");}
}
class T extends Frame
{public T(String s)
{super(s);
addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e) 
{dispose();System.exit(0);}
});//�رմ���
setLayout(new GridLayout(1,2));//���ò���1��3��
R x=new R("����","GMT+9:16");//����ʱ��
R y=new R("�¸绪","GMT-8");//����ʱ��
R z=new R("����","GMT+8");//����ʱ��
add(x);add(y);add(z);//���ʱ�ӵ������
setSize(350,160);//���ÿ�ܿ���
setVisible(true);//��ʾ���
}
}
class R extends Canvas implements Runnable
{private int s=0;//�������ʱ��ʱ�������ֵ
private String c;//��������ʱ���ĳ���
private GregorianCalendar d;//�������и�����(����)�����
Thread t;//�����̶߳���
public R(String m,String n)
{c=m;//���û�������ʾ�ĳ�������
d=new GregorianCalendar(TimeZone.getTimeZone(n));
t=new Thread(this);
t.start();//�����߳�
setSize(125,125);//���û�����С
setBackground(Color.black);//���û���������ɫΪ��ɫ
}
//��д����� paint()��������ʱ��ͼ��
public void paint(Graphics g)
{Graphics2D a=(Graphics2D)g;//�Ѹ������ǿ��ת��Ϊ�������
BasicStroke b=new BasicStroke(2.0f);
a.setStroke(b);//���û��ʻ��ߵĿ��
a.setColor(Color.green);//���û��ʵ���ɫ
a.drawOval(0,0,100,100);//��ʱ�ӵ����
//��Сʱ�����Ӻ����Ӷ�Ӧ�ĽǶȣ��Ա��ͼ
double hh=2*Math.PI*(s-3*60*60)/(12*60*60);
double mm=2*Math.PI*(s-16*60)/(60*60);
double ss=2*Math.PI*(s-16)/(60);
b=new BasicStroke(5.0f);
a.setStroke(b);//���û��ʻ��߿�Ȼ�Сʱָ��
a.drawLine(50,50,50+(int)(30*Math.cos(hh)),50+(int)(30*Math.sin(hh)));
b=new BasicStroke(3.0f);
a.setStroke(b);//���û��ʻ��߿�Ȼ�����ָ��
a.drawLine(50,50,50+(int)(40*Math.cos(mm)),50+(int)(40*Math.sin(mm)));
b=new BasicStroke(1.0f);
a.setStroke(b);//���û��ʻ��߿�Ȼ�����ָ��
a.drawLine(50,50,50+(int)(45*Math.cos(ss)),50+(int)(45*Math.sin(ss)));
a.setColor(Color.red);//���û��ʵ���ɫ
a.drawString(c,35,116);//��ʾ������
}
public void timeElapsed()//�õ���ǰʱ��
{d.setTime(new Date());
//����ǰʱ���ʱ���֡���ת��Ϊ�����ʾ��ʱ��
s=d.get(Calendar.HOUR)*60*60+d.get(Calendar.MINUTE)*60+d.get(Calendar.SECOND);
}
public void run()//ʵ��Runnable�ӿ��еķ�����ÿ��300�����ȡ��ǰʱ�䲢��ʾ
{try{while(true)
{t.sleep(300);//����300����
timeElapsed();//���÷�����ȡ��ǰ�µ�ʱ��
repaint();//����paint()�������ػ�ʱ����ʾ����
}
}
catch(InterruptedException e){}
}
}
