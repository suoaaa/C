import java.awt.*;
import java.awt.event.*;
public class A extends WindowAdapter implements ActionListener
{ Frame  f=new Frame("��������Ϸ");
   Label s1=new Label("�������ݵ����ֵ=");
   Label s2=new Label("�������ݵ���Сֵ=");
   Label s3=new Label("����Ĳ�������=");
   Label s4=new Label("",Label.CENTER);
   Label s5=new Label("",Label.CENTER);
   Button b1=new Button("���¿���");
   Button b2=new Button("��  ��");
   Button b3=new Button("��  ��");
   Button b4=new Button("��  ��");
   
   TextField t1=new TextField("100");
   TextField t2=new TextField("0");
   TextField t3=new TextField("10");
   TextField t4=new TextField();
   Panel p=new Panel();
   int max,min,x,y,n,count;
   boolean b=true;
   public A()
   {   f.add(p,"Center");
       p.setLayout(new GridLayout(6,2,10,10));
       p.add(s1);p.add(t1);
       p.add(s2);p.add(t2);
       p.add(s3);p.add(t3);
       p.add(b1);p.add(b2);
       p.add(s4);p.add(t4);
       p.add(b3);p.add(b4);
       f.add(s5,"South");
       f.pack();
       f.setVisible(true);
       b1.addActionListener(this);
       b2.addActionListener(this);
       b3.addActionListener(this);
       b4.addActionListener(this);
       f.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
       init();
   }
  public void init()
     {  max=Integer.parseInt(t1.getText());
        min=Integer.parseInt(t2.getText());
        n=Integer.parseInt(t3.getText());
        x=(int)(Math.random()*(max-min))+min;
        count=0;
        display(count+1);
        b=true;
        t4.setEditable(true);
        t4.setText("");
        s5.setText("");
     }
  public void display(int m){s4.setText("���ύ��"+m+"�����ݣ�");}
  public void show(int m){s4.setText("��"+m+"������������ǣ�");}
  public void go()
     {  if(count>10 && b)
             { s4.setText("�����¿��֣�");
               s5.setText("x="+x+"     timesout��    ����δ���У�");
               t4.setEditable(false);
             }
        else if(count<=10 && !b)
             { 
               s5.setText("���Ѿ������ˣ������¿��֣�");
             }
 
     }
  public static void main(String[]args){new A();}
  public void actionPerformed(ActionEvent e)
  {if(e.getSource()==b1)init();
   else if(e.getSource()==b2)System.exit(0); 
   else if(e.getSource()==b3)
       { count++;
         if(count<=10 && b)
            {  y=Integer.parseInt(t4.getText());
               if(x==y)
                    {  t4.setEditable(false);
                       s5.setText("x="+x+"   ok��   ������"+count+"�β��и����ݣ�");
                       b=false;
                    }
               else  if(x>y)s5.setText(y+"С�ˣ�");
               else  s5.setText(y+"���ˣ�");
               show(count);
            }
        else go();
       }
   else if(e.getSource()==b4)
        {if(count<=10 && b){t4.setText("");display(count+1);}
         else go();
        }
 }
}

 
 