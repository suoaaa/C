import java.awt.*;
import java.awt.event.*;
public class A extends Frame implements ActionListener,MouseListener
{TextArea s=new TextArea();
PopupMenu pm=new PopupMenu();
MenuItem t1=new MenuItem("����");
MenuItem t2=new MenuItem("����");
MenuItem t3=new MenuItem("ճ��");
A(){setTitle("����ʽ�˵�");
setSize(200,150);
addWindowListener(new WindowAdapter(){//ע�ᴰ�ڵ��¼�������
public void windowClosing(WindowEvent e)
{System.exit(0);}
});
add(s);
s.add(pm);//������ʽ�˵����뵽�ı�����
pm.add(t1);
pm.add(t2);
pm.add(t3);
t1.addActionListener(this);
t2.addActionListener(this);
t3.addActionListener(this);
s.addMouseListener(this);//ע���ı���������¼�������
show();   
}
public void actionPerformed(ActionEvent e)
{s.append("��ѡ����"+e.getActionCommand()+"\n");}
public void mouseReleased(MouseEvent e)
{if(e.isPopupTrigger())//�ж��Ƿ񵥻�����Ҽ�
pm.show(this,e.getX(),e.getY());//��ʾ����ʽ�˵�
}
public void mouseClicked(MouseEvent e){}
public void mouseEntered(MouseEvent e){}
public void mouseExited(MouseEvent e){}
public void mousePressed(MouseEvent e){}
public static void main(String arg[]){new A();}
}
