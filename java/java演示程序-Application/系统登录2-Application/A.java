import java.awt.*;
import java.awt.event.*;
public class A implements ActionListener
 {Frame f,f1;static int n=1;Panel p1,p2,p3;
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
e){System.exit(0);}});
    f.setSize(200,150);f.setVisible(true);
 }
 public void actionPerformed(ActionEvent e)
  {if(e.getSource()==b2){n++;if(n>3)System.exit(0);
      else {t1.setText("");t2.setText("");s.setText("");}}
   if(e.getSource()==b1)       
{if((t1.getText().equals("abc")&&t2.getText().equals("123"))||(t1.getText().equals("efg")&&t2.get 
Text().equals("456")))
       {n=0;f1=new Frame("欢迎"+t1.getText());f1.setSize(150,50);f1.setVisible(true);
        f1.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent  
e){f1.setVisible(false);}});}
     else s.setText("这是你第"+n+"次登录失败！");
    }
  }
}
