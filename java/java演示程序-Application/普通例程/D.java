
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
public class D extends Applet implements ActionListener
{Label l1=new Label("dj:");
Label l2=new Label("js:");
Label l3=new Label("ch:");
TextField t1=new TextField(6);
TextField t2=new TextField(6);
TextField t3=new TextField(6);
Button b1=new Button("º∆À„");
public void init()
  {add(l1);
   add(t1);
   add(l2);
   add(t2);
   add(l3);
   add(t3);
   add(b1);
   b1.addActionListener(this);
  }
public void actionPerformed(ActionEvent e)
{int x=Integer.parseInt(t1.getText())*Integer.parseInt(t2.getText());
t3.setText(Integer.toString(x));
}}

 
