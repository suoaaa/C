import java.awt.*;
import java.awt.event.*;
public class A implements ActionListener
 {Frame f,f1,f2;static int n=1,x=0,y=0,z=1;Panel p1,p2,p3;
  Button b1,b2;TextField t1,t2;Label s=new Label("",Label.CENTER);
  public static void main(String[]args){new A().g();}
  public void g()
   {f=new Frame("系统登录");b1=new Button("提交");b2=new Button("重填");
    p1=new Panel();p2=new Panel();p3=new Panel();
    t1=new TextField(10);t2=new TextField(10);t2.setEchoChar('*');
    f.setLayout(new GridLayout(4,1,5,5));
    f.add(p1);p1.add(new Label("姓名：",Label.CENTER));p1.add(t1);
    f.add(p2);p2.add(new Label("口令：",Label.CENTER));p2.add(t2);
    f.add(p3);p3.add(b1);p3.add(b2);f.add(s);
    b1.addActionListener(this);
    b2.addActionListener(this);
    f.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent  
e){z--;if(z==0)System.exit(0);else f.setVisible(false);}});
    f.setSize(200,150);f.setVisible(true);
 }
 public void actionPerformed(ActionEvent e)
  {if(e.getSource()==b2){n++;if(n>3){z--;if(z==0)System.exit(0);else f.setVisible(false);}
      else {t1.setText("");t2.setText("");s.setText("");}}
   if(e.getSource()==b1)
    {if(t1.getText().equals("abc")&&t2.getText().equals("123"))
       {n=0;x++;z++;if(x==1){f1=new Frame("欢迎"+t1.getText());f1.setSize(120,50);
        f1.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent  
e){z--;if(z==0)System.exit(0);else f1.setVisible(false);}});}f1.setVisible(true);
       }
     else if(t1.getText().equals("efg")&&t2.getText().equals("456"))   
       {n=0;y++;z++;if(y==1){f2=new Frame("欢迎"+t1.getText());f2.setSize(120,50);
        f2.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent  
e){z--;if(z==0)System.exit(0);else f2.setVisible(false);}});}f2.setVisible(true);
       }
     else s.setText("这是你第"+n+"次登录失败！");
    }
  }
}
