import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class A implements ActionListener
{JFrame f=new JFrame("XXX");
 JPanel p=new JPanel();
 JButton b=new JButton("0");
 static int count=0;
 public A()
  {
   f.setSize(300,300);
   f.setContentPane(p);
   p.setLayout(new FlowLayout());
   p.add(b);
   b.setFont(new Font("ºÚÌå",Font.BOLD,30));
   b.addActionListener(this);
   f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   f.setVisible(true);
  }
 public void actionPerformed(ActionEvent e)
  {if(e.getSource()==b)count++;
   b.setText(""+count);
  }
 public static void main(String[]args)
  {A a=new A();}
}
