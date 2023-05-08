import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class A implements ActionListener
{JFrame f=new JFrame("XXX");
 JToolBar tb=new JToolBar();
 ImageIcon m1=new ImageIcon("1.jpg");
 ImageIcon m2=new ImageIcon("2.jpg");
 ImageIcon m3=new ImageIcon("3.jpg");
 ImageIcon m4=new ImageIcon("4.jpg");
 JButton b1,b2,b3,b4;
 JPanel p=new JPanel();
 public A()
  {
   f.setSize(300,300);
   f.setContentPane(p);
   f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   f.setVisible(true);
   b1=new JButton(m1);b1.setToolTipText("红色");
   b2=new JButton(m2);b2.setToolTipText("绿色");
   b3=new JButton(m3);b3.setToolTipText("兰色");
   b4=new JButton(m4);b4.setToolTipText("粉红色");
   tb.add(b1);tb.add(b2);tb.add(b3);tb.add(b4);
   p.add(tb,BorderLayout.NORTH);
   p.add(new JLabel(),BorderLayout.CENTER);
   p.add(tb);
   b1.addActionListener(this);b2.addActionListener(this);
   b3.addActionListener(this);b4.addActionListener(this);
  }
 public void actionPerformed(ActionEvent e)
  {if(e.getSource()==b1)p.setBackground(Color.RED);
   else if(e.getSource()==b2)p.setBackground(Color.GREEN);
   else if(e.getSource()==b3)p.setBackground(Color.BLUE);
   else if(e.getSource()==b4)p.setBackground(Color.PINK);
  }
 public static void main(String[]args)
  {new A();}
}
