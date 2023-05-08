import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class B implements ActionListener
{JFrame f;
  JToolBar tb;JMenuBar mb;JMenu m1=new JMenu("ÑÕÉ«"),m2=new JMenu("°ïÖú");
  JPanel p;  JButton b1,b2,b3,b4,b5,b6;  JTextArea t;
  JMenuItem m11,m12,m13,m14,m15,m16;
  public B()
      {f=new JFrame("XXX");p=new JPanel();f.setContentPane(p);        
        tb=new JToolBar();mb=new JMenuBar();
        b1=new JButton(new ImageIcon("r.png"));b1.addActionListener(this);
        b2=new JButton(new ImageIcon("g.png"));b2.addActionListener(this);
        b3=new JButton(new ImageIcon("b.png"));b3.addActionListener(this);
        b4=new JButton(new ImageIcon("y.png"));b4.addActionListener(this);
        b5=new JButton(new ImageIcon("c.png"));b5.addActionListener(this);
        b6=new JButton(new ImageIcon("x.png"));b6.addActionListener(this);
        t=new JTextArea(300,200); p.add(tb,BorderLayout.NORTH);
        p.add(t,BorderLayout.CENTER);
        tb.add(b1);tb.add(b2);tb.add(b3);tb.add(b4);tb.add(b5);
        tb.addSeparator();tb.add(b6);
        m11=new JMenuItem("ºì",new ImageIcon("r.png"));m11.addActionListener(this);
        m12=new JMenuItem("ÂÌ",new ImageIcon("g.png"));m12.addActionListener(this);
        m13=new JMenuItem("À¶",new ImageIcon("b.png"));m13.addActionListener(this);
        m14=new JMenuItem("»Æ",new ImageIcon("y.png"));m14.addActionListener(this);
        m15=new JMenuItem("Çà",new ImageIcon("c.png"));m15.addActionListener(this);
        m16=new JMenuItem("ÍË³ö",new ImageIcon("x.png"));m16.addActionListener(this);
        f.setJMenuBar(mb);mb.add(m1);mb.add(m2);
        m1.add(m11);m1.add(m12);m1.add(m13);m1.add(m14);m1.add(m15);m1.add(m16);
        f.setSize(400,200);f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
      }
  public void actionPerformed(ActionEvent e)
      {if(e.getSource()==b1 || e.getSource()==m11)t.setBackground(Color.RED);
        else if(e.getSource()==b2 || e.getSource()==m12)t.setBackground(Color.GREEN);
        else if(e.getSource()==b3 || e.getSource()==m13)t.setBackground(Color.BLUE);
        else if(e.getSource()==b4 || e.getSource()==m14)t.setBackground(Color.YELLOW);
        else if(e.getSource()==b5 || e.getSource()==m15)t.setBackground(Color.CYAN);
        else if(e.getSource()==b6 || e.getSource()==m16)System.exit(0);
       }
}
public class A
{public static void main(String[]args)
        {B b=new B();}
}
