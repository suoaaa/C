import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class A extends JApplet implements MouseListener
{Container c;
 JLabel s;
 public void init()
 {c=getContentPane();
  c.setBackground(Color.YELLOW);
  s=new JLabel("",JLabel.CENTER);
  c.add(s,BorderLayout.NORTH);
  c.addMouseListener(this);
 }
 public void mousePressed(MouseEvent e){}
 public void mouseReleased(MouseEvent e){}
 public void mouseEntered(MouseEvent e){}
 public void mouseExited(MouseEvent e){}
 public void mouseClicked(MouseEvent e)
 {if(e.getSource()==c)
      s.setText("鼠标单击点的坐标为：("+e.getX()+","+e.getY()+")");
 }
}
