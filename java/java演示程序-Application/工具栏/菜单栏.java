import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class A implements ActionListener
{JFrame f;;
 JPanel p;
 JMenuBar mb;
 JMenu m1;
 JMenuItem m11,m12,m13,m14;
 Color defaultColor=Color.YELLOW;
 public void init()
 {f=new JFrame("XXX");
  p=new JPanel();
  f.setContentPane(p);
  p.setBackground(Color.YELLOW);
  mb=new JMenuBar();
  m1=new JMenu("设置背景颜色");
  m11=new JMenuItem("红色");
  m12=new JMenuItem("绿色");
  m13=new JMenuItem("兰色");
  m14=new JMenuItem("还原");
  f.setJMenuBar(mb);
  mb.add(m1);
  m1.add(m11);m1.add(m12);m1.add(m13);
  m1.addSeparator();m1.add(m14);
  m11.addActionListener(this);
  m12.addActionListener(this);
  m13.addActionListener(this);
  m14.addActionListener(this);
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  p.setBackground(defaultColor);
  f.setBounds(200,200,200,200);
  f.setVisible(true);
 }
 public void actionPerformed(ActionEvent e)
 {if(e.getSource()==m11)p.setBackground(Color.RED);
  else if(e.getSource()==m12)p.setBackground(Color.GREEN);
  else if(e.getSource()==m13)p.setBackground(Color.BLUE);
  else if(e.getSource()==m14)p.setBackground(defaultColor);
 }
 public static void main(String[]args)
 {A a=new A();a.init();}
}