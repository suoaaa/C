import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class A implements ActionListener
{JFrame f=new JFrame("XXX");
 JMenuBar mb=new JMenuBar();
 JMenu m=new JMenu("字号");
 JMenuItem m1=new JMenuItem("30");
 JMenuItem m2=new JMenuItem("20");
 JMenuItem m3=new JMenuItem("10");
 JLabel s=new JLabel("电子科技大学",JLabel.CENTER);
 JPanel p=new JPanel();
 public void g()
  {f.setBackground(Color.YELLOW);
   f.setSize(300,300);
   f.setContentPane(p);
   p.add(s,BorderLayout.CENTER);
   f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   f.setVisible(true);
   f.setJMenuBar(mb);
   mb.add(m);
   m.add(m1);
   m.add(m2);
   m.add(m3);
   m1.addActionListener(this);
   m2.addActionListener(this);
   m3.addActionListener(this);
  }
 public void actionPerformed(ActionEvent e)
  {if(e.getSource()==m1)s.setFont(new Font("黑体",Font.BOLD,30));
   else if(e.getSource()==m2)s.setFont(new Font("黑体",Font.BOLD,20));
   else if(e.getSource()==m3)s.setFont(new Font("黑体",Font.BOLD,10));
  }
 public static void main(String[]args)
  {new A().g();}
}




