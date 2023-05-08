import java.awt.*;
import java.awt.event.*;
public class A extends WindowAdapter implements ActionListener
{Frame f=new Frame("Menu");
Button b1=new Button("Press Me!");
Button b2=new Button("Goodbye!");

public static void main(String[]args)
{new A();}
A(){Label t1,t2;t1=new Label();
t2=new Label();
f.add(b1,"West");
f.add(b2,"East");
f.add(t1,"North");
f.add(t2,"South");
b1.addActionListener(this);
b2.addActionListener(this);
f.addWindowListener(this);
f.pack();
f.setVisible(true);
}
public void actionPerformed(ActionEvent e)
{if(e.getSource()==b1)t1.setText(e.getActionCommand()+"has been pressed");
if(e.getSource()==b2)t1.setText(e.getActionCommand()+"abc");
if(e.getActionCommand()=="Press Me!")t2.setText("AA");
if(e.getActionCommand()=="Goodbye!")t2.setText("BB");
}
public void windowClosing(WindowEvent e){System.exit(0);}
}