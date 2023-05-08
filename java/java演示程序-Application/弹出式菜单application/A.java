import java.awt.*;
import java.awt.event.*;
public class A extends Frame implements ActionListener,MouseListener
{TextArea s=new TextArea();
PopupMenu pm=new PopupMenu();
MenuItem t1=new MenuItem("复制");
MenuItem t2=new MenuItem("剪切");
MenuItem t3=new MenuItem("粘贴");
A(){setTitle("弹出式菜单");
setSize(200,150);
addWindowListener(new WindowAdapter(){//注册窗口的事件监听器
public void windowClosing(WindowEvent e)
{System.exit(0);}
});
add(s);
s.add(pm);//将弹出式菜单加入到文本区中
pm.add(t1);
pm.add(t2);
pm.add(t3);
t1.addActionListener(this);
t2.addActionListener(this);
t3.addActionListener(this);
s.addMouseListener(this);//注册文本区的鼠标事件监听器
show();   
}
public void actionPerformed(ActionEvent e)
{s.append("你选择了"+e.getActionCommand()+"\n");}
public void mouseReleased(MouseEvent e)
{if(e.isPopupTrigger())//判断是否单击鼠标右键
pm.show(this,e.getX(),e.getY());//显示弹出式菜单
}
public void mouseClicked(MouseEvent e){}
public void mouseEntered(MouseEvent e){}
public void mouseExited(MouseEvent e){}
public void mousePressed(MouseEvent e){}
public static void main(String arg[]){new A();}
}
