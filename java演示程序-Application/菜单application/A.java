import java.awt.*;
import java.awt.event.*;
public class A extends Frame implements ActionListener,ItemListener
{TextField t=new TextField();
MenuBar mb=new MenuBar();
Menu m1=new Menu("�˵�");
Menu m2=new Menu("�����˵�");
MenuItem i=new MenuItem("��ͨ�˵���",new MenuShortcut('P'));
CheckboxMenuItem c=new CheckboxMenuItem("��ѡ�˵���");
MenuItem exit=new MenuItem("�˳�");
A(){setTitle("�˵��ۺ�Ӧ��");
setSize(250,200);
add(t);
mb.add(m1);
m1.add(m2);//�������˵�m2���뵽m1��
c.setState(true);  //�趨��ѡ�˵���Ϊѡ��
m1.add(i);
m1.add(c);
m1.addSeparator();
m1.add(exit);
m2.add("�˵���A");//Ϊ�����˵�m2����˵���
m2.add("�˵���B");
i.addActionListener(this);//ע���¼�������
c.addItemListener(this);
exit.addActionListener(this);
setMenuBar(mb);//��ʾ�˵�
show();//��ʾ����
}
public void actionPerformed(ActionEvent e)
{if(e.getSource()==exit)System.exit(0);
else t.setText(e.getActionCommand()+"����");
}
public void itemStateChanged(ItemEvent e)
{if(e.getSource()==c)
if(c.getState())t.setText(c.getLabel()+"��ѡ��");
else t.setText(c.getLabel()+"��ȡ��");
}
public static void main(String[]args)
{new A();}
}
