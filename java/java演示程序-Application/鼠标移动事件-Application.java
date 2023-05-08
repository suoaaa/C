import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class A implements MouseMotionListener
{JFrame f=new JFrame("XXX");
 JPanel p=new JPanel();
 JLabel s=new JLabel("");
 public A()
 {f.setContentPane(p);
  p.setBackground(Color.YELLOW);
  p.add(s,BorderLayout.NORTH);
  p.addMouseMotionListener(this);
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  f.setSize(300,320);
  f.setVisible(true);
 }
 public void mouseDragged(MouseEvent e){}
 public void mouseMoved(MouseEvent e)
 {if(e.getSource()==p)
      s.setText("鼠标当前的位置坐标为：("+e.getX()+","+e.getY()+")");
 }
 public static void main(String[]args)
 {A a=new A();}
}
