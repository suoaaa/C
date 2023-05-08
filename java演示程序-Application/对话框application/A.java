import java.awt.*;
import java.awt.event.*;
public class A extends Frame implements ActionListener
{int r=6,c=40;
Panel p1=new Panel(),p2=new Panel();
TextArea t=new TextArea("文本区行数:"+r+"  列数:"+c,r,c);
Button b=new Button("退出"),d=new Button("对话框");
A(){setTitle("对话框的父窗口");
setSize(350,200);
add("Center",p1);
add("South",p2);
p1.add(t);
p2.add(b);
p2.add(d);
b.addActionListener(this);
d.addActionListener(this);
setVisible(true);
}
public static void main(String[]args)
{new A();}
public void actionPerformed(ActionEvent e)
{if(e.getSource()==b)System.exit(0);
else {B k=new B(this,true);k.show();}
}
class B extends Dialog implements ActionListener//内部类
{Label s1=new Label("请输入行数");
  Label s2=new Label("请输入列数");
  TextField rt=new TextField(50);
  TextField ct=new TextField(50);
  Button m=new Button("确定");
  Button n=new Button("取消");
B(A x,boolean y)
{super(x,y);
  setTitle("自定义对话框");
  setSize(260,140);
  setResizable(false);
  setLayout(null);
add(s1);
add(s2);
s1.setBounds(50,30,65,20);
s2.setBounds(50,60,65,20);
add(rt);
add(ct);
rt.setText(Integer.toString(t.getRows()));
ct.setText(Integer.toString(t.getColumns()));
rt.setBounds(130,30,90,20);
ct.setBounds(130,60,90,20);
add(m);
add(n);
m.setBounds(60,100,60,25);
n.setBounds(140,100,60,25);
m.addActionListener(this);
n.addActionListener(this);
}
public void actionPerformed(ActionEvent e)
{if(e.getSource()==m)
  {int r=Integer.parseInt(rt.getText());
   int c=Integer.parseInt(ct.getText());
   t.setRows(r);
   t.setColumns(c);
   t.setText("文本区行数："+r+"  列数："+c);
 }
 dispose();//关闭自定义对话框
}
} }
