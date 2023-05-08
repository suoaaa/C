import java.awt.*;
import java.awt.event.*;
public class A extends WindowAdapter implements ActionListener
{Frame f=new Frame("GUI");
MenuBar mb=new MenuBar();
Menu m1=new Menu("File");
Menu m2=new Menu("Help");
MenuItem m11=new MenuItem("Open");
MenuItem m12=new MenuItem("Exit");
MenuItem m21=new MenuItem("Document");
Label t=new Label();
public A()
  {f.setMenuBar(mb);
   mb.add(m1);
   mb.setHelpMenu(m2);
   f.add(t,"South");
   m1.add(m11);
   m1.add(m12);
   m2.add(m21);
   m11.addActionListener(this);
   m12.addActionListener(this);
   m21.addActionListener(this);
   f.addWindowListener(this);
   f.setSize(200,200);
   f.setVisible(true);
  }
public static void main(String args[])
{new A();}
public void actionPerformed(ActionEvent e)
{String s=e.getActionCommand();
 if(s.equals("Open"))t.setText("Open a file!");
 else if(s.equals("Exit"))System.exit(1);
 else if(s.equals("Document"))t.setText("Document information!");
}
public void windowClosing(WindowEvent e)
{System.exit(1);}
}





import java.awt.*;
import java.awt.event.*;
public class B extends WindowAdapter implements ActionListener
{Frame f=new Frame("GUI");
Button b1=new Button("Display");
Button b2=new Button("Exit");
Label t=new Label();
public B()
  {f.add(b1,"West");
   f.add(b2,"Center");
   f.add(t,"North");
   b1.addActionListener(this);
   b2.addActionListener(this);
   f.addWindowListener(this);
   f.setSize(150,100);
   f.setVisible(true);
  }
public static void main(String args[])
{new B();}
public void actionPerformed(ActionEvent e)
{String s=e.getActionCommand();
 if(s.equals("Display"))t.setText("我们来play一个game.");
 else if(s.equals("Exit"))System.exit(1);
}
public void windowClosing(WindowEvent e)
{System.exit(1);}
}





import java.awt.*;
import java.awt.event.*;
public class D extends WindowAdapter implements ActionListener
{Frame f=new Frame("GUI");
Button b1=new Button("Dialog");
Button b2=new Button("Exit");
Dialog d=new Dialog(f,"Dialog",true);
public D()
  {f.add(b1,"West");
   f.add(b2,"Center");
   b1.addActionListener(this);
   b2.addActionListener(this);
   d.add("Center",new Label("I'm a Dialog"));
   d.setSize(100,100);
   d.addWindowListener(this);
   f.addWindowListener(this);
   f.setSize(200,200);
   f.setVisible(true);
  }
public static void main(String args[])
{new D();}
public void actionPerformed(ActionEvent e)
{String s=e.getActionCommand();
 if(s.equals("Dialog"))d.setVisible(true);
 else if(s.equals("Exit")){System.exit(1);}
}
public void windowClosing(WindowEvent e)
{System.exit(1);}
}
