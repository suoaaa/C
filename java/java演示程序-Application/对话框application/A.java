import java.awt.*;
import java.awt.event.*;
public class A extends Frame implements ActionListener
{int r=6,c=40;
Panel p1=new Panel(),p2=new Panel();
TextArea t=new TextArea("�ı�������:"+r+"  ����:"+c,r,c);
Button b=new Button("�˳�"),d=new Button("�Ի���");
A(){setTitle("�Ի���ĸ�����");
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
class B extends Dialog implements ActionListener//�ڲ���
{Label s1=new Label("����������");
  Label s2=new Label("����������");
  TextField rt=new TextField(50);
  TextField ct=new TextField(50);
  Button m=new Button("ȷ��");
  Button n=new Button("ȡ��");
B(A x,boolean y)
{super(x,y);
  setTitle("�Զ���Ի���");
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
   t.setText("�ı���������"+r+"  ������"+c);
 }
 dispose();//�ر��Զ���Ի���
}
} }
