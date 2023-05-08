import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class B
{JFrame f=new JFrame("XXX");
 JProgressBar pb=new JProgressBar(JProgressBar.HORIZONTAL,0,100);
 JPanel p=new JPanel();
 public void g()
  {
   f.setSize(300,300);
   f.setContentPane(p);
   p.setLayout(new FlowLayout());
   p.add(pb);
   pb.setStringPainted(true);
   p.setBackground(Color.YELLOW);
   f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   f.setVisible(true);
   for(int i=0;i<=100;i+=1)
     {pb.setValue(i);
      try{Thread.sleep(100);}
      catch(InterruptedException e){}
     }
  }
 public static void main(String[]args)
  {new B().g();}
}




