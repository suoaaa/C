//颜色
import java.awt.*;
public class A extends Frame
{public static void main(String[]args)
{A c=new A();
c.setBackground(Color.gray);
c.setLayout(new FlowLayout());
Label t1=new Label("颜色1",Label.CENTER);
t1.setBackground(new Color(254,0,0));
Label t2=new Label("颜色2",Label.CENTER);
t2.setBackground(new Color(54648));
Label t3=new Label("颜色3",Label.CENTER);
t3.setBackground(new Color(0,0,254));
c.add(t1);
c.add(t2);
c.add(t3);
c.setSize(300,200);
c.setVisible(true);
}
}
