import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class A implements ActionListener
 {JFrame f=new JFrame("计算器");
  JButton b1,b2,b3;
  JTextField t1,t2,t3;
  public static void main(String[]args)
   {new A().g();}
  public void g()
   {b1=new JButton("+");
    b2=new JButton("-");
    b3=new JButton("清除");
    t1=new JTextField(5);
    t2=new JTextField(5);
    t3=new JTextField(5);
    Container c=f.getContentPane();
    c.setLayout(new GridLayout(3,3,5,5));
    c.add(new JLabel("运算量1",JLabel.CENTER));
    c.add(new JLabel("运算量2",JLabel.CENTER));
    c.add(new JLabel("运算结果",JLabel.CENTER));
    c.add(t1);c.add(t2);c.add(t3);
    c.add(b1);c.add(b2);c.add(b3);
    b1.addActionListener(this);
    b2.addActionListener(this);
    b3.addActionListener(this);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.pack();
    f.setVisible(true);
 }
 public void actionPerformed(ActionEvent e)
 {int x=Integer.parseInt(t1.getText());
  int y=Integer.parseInt(t2.getText());
  if(e.getSource()==b1)t3.setText(x+y+"");
  if(e.getSource()==b2)t3.setText(x-y+"");
  if(e.getSource()==b3){t1.setText("");t2.setText("");t3.setText("");}
 }
}
