import java.awt.*;
import java.awt.event.*;
public class A implements ActionListener
 {Frame f=new Frame("计算器");
  Button b1,b2,b3;TextField t1,t2,t3;
  public static void main(String[]args){new A().g();}
  public void g()
   {b1=new Button("+");b2=new Button("-");b3=new Button("清除");
    t1=new TextField(5);t2=new TextField(5);t3=new TextField(5);
    f.setLayout(new GridLayout(3,3,5,5));
    f.add(new Label("运算量1",Label.CENTER));
    f.add(new Label("运算量2",Label.CENTER));
    f.add(new Label("运算结果",Label.CENTER));
    f.add(t1);f.add(t2);f.add(t3);
    f.add(b1);f.add(b2);f.add(b3);
    b1.addActionListener(this);
    b2.addActionListener(this);
    b3.addActionListener(this);
    f.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
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
