import java.awt.*;
import java.awt.event.*;
class B implements ActionListener{
     Frame f;
     MenuBar mb;
     Menu m1,m11;
     MenuItem m111,m112,m12,m13,m14;
     public B(){
           f=new Frame("XXX");
           mb=new MenuBar();
           m1=new Menu("���ñ�����ɫ");
           m11=new Menu("��ɫ");    m12=new MenuItem("��ɫ");    m13=new MenuItem("��ɫ");
           m14=new MenuItem("���");  m111=new MenuItem("���");   m112=new MenuItem("�ۺ�");
           f.setMenuBar(mb);     mb.add(m1);    mb.add(new Menu("�༭"));  mb.add(new Menu("����"));
           m1.add(m11);m1.add(m12);m1.add(m13); m1.addSeparator(); m1.add(m14);
           m11.add(m111);m11.add(m112);
           m111.addActionListener(this);m112.addActionListener(this);
           m12.addActionListener(this);m13.addActionListener(this);m14.addActionListener(this);
           f.setSize(500,200);           f.setVisible(true);
     }
     public void actionPerformed(ActionEvent e){
           if(e.getSource()==m111){f.setBackground(Color.RED);}
           else if(e.getSource()==m112){f.setBackground(Color.PINK);}
           else if(e.getSource()==m12){f.setBackground(Color.GREEN);}
           else if(e.getSource()==m13){f.setBackground(Color.BLUE);}
           else if(e.getSource()==m14){f.setBackground(Color.WHITE);}
     }     
}
public class A{
     public static void main(String[] t){
           new B();
     }
}