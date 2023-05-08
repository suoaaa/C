import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class A extends JFrame implements ActionListener
{JButton b1,b2,b3;
  JLabel s;
  static int count=0;
  JToolBar tb;
  JPanel p;
  JMenuBar mb;
  JMenu m1;
  JMenuItem m11,m12,m13;
  public A()
    {  super("工具栏演示程序");
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       p=new JPanel();p.setLayout(new BorderLayout());
       setContentPane(p);
       tb=new JToolBar();
       b1=new JButton(new ImageIcon("jia.ico"));b1.setToolTipText("加");
       b2=new JButton(new ImageIcon("jian.ico"));b2.setToolTipText("减");
       b3=new JButton(new ImageIcon("zero.ico"));b3.setToolTipText("清零");
       s=new JLabel(""+count,JLabel.CENTER);
       mb=new JMenuBar();
       m1=new JMenu("计数",false);
       m11=new JMenuItem("加",new ImageIcon("jia.ico"));
       m12=new JMenuItem("减",new ImageIcon("jian.ico"));
       m13=new JMenuItem("清零",new ImageIcon("zero.ico"));
       setJMenuBar(mb);
       mb.add(m1);
       m1.add(m11);m1.add(m12);m1.add(m13);
       tb.add(b1);tb.add(b2);tb.add(b3);
       p.add(tb,BorderLayout.NORTH);p.add(s,BorderLayout.CENTER);
       setSize(200,200);setVisible(true);
       b1.addActionListener(this);b2.addActionListener(this);
       b3.addActionListener(this);m11.addActionListener(this);
       m12.addActionListener(this);m13.addActionListener(this);
     }
   public static void main(String[]args)
      {A a=new A();}
   public void actionPerformed(ActionEvent e)
      {if(e.getSource()==b1||e.getSource()==m11)count++;
        else if(e.getSource()==b2 || e.getSource()==m12)count--;
        else if(e.getSource()==b3 || e.getSource()==m13)count=0;
        s.setText(""+count);
       }
}
