import java.awt.*;
import java.awt.event.*;
public class A extends Frame implements ActionListener,ItemListener
{TextField t=new TextField();
MenuBar mb=new MenuBar();
Menu m1=new Menu("菜单");
Menu m2=new Menu("二级菜单");
MenuItem i=new MenuItem("普通菜单项",new MenuShortcut('P'));
CheckboxMenuItem c=new CheckboxMenuItem("复选菜单项");
MenuItem exit=new MenuItem("退出");
A(){setTitle("菜单综合应用");
setSize(250,200);
add(t);
mb.add(m1);
m1.add(m2);//将二级菜单m2加入到m1中
c.setState(true);  //设定复选菜单项为选中
m1.add(i);
m1.add(c);
m1.addSeparator();
m1.add(exit);
m2.add("菜单项A");//为二级菜单m2加入菜单项
m2.add("菜单项B");
i.addActionListener(this);//注册事件监听器
c.addItemListener(this);
exit.addActionListener(this);
setMenuBar(mb);//显示菜单
show();//显示窗口
}
public void actionPerformed(ActionEvent e)
{if(e.getSource()==exit)System.exit(0);
else t.setText(e.getActionCommand()+"被打开");
}
public void itemStateChanged(ItemEvent e)
{if(e.getSource()==c)
if(c.getState())t.setText(c.getLabel()+"被选中");
else t.setText(c.getLabel()+"被取消");
}
public static void main(String[]args)
{new A();}
}
