import java.awt.Color; import java.awt.*; import java.awt.List;
import java.util.*; import java.awt.event.*; import java.io.*;
public class A extends WindowAdapter implements ActionListener,ItemListener
{Frame f; Vector vect=new Vector();
Label label1=new Label("������"); Label label2=new Label("��������");
TextField tf1,tf2;TextArea area;
Checkbox cb1,cb2;Choice c1,c2;
List ls1; Button b1,b2,b3,b4;
public static void main(String arg[])
{(new A()).init();}
public void init()
{Panel p1,p2;CheckboxGroup cg;
f=new Frame("����ѧ��������Ϣ����");
f.setSize(480,200);f.setLocation(200,150);
f.setBackground(Color.lightGray);
f.setLayout(new GridLayout(1,2)); //���񲼾֣����ҷָ�����
ls1=new List();//�����б��
f.add(ls1);//ռ�ݴ�����벿��
p1=new Panel();
p1.setLayout(new GridLayout(6,2));//���񲼾֣�6��1��
f.add(p1);//ռ�ݴ����Ұ벿��
tf1=new TextField("1");tf2=new TextField("Name");
cg=new CheckboxGroup();//������ѡ����
cb1=new Checkbox("��",cg,true);//������ѡ��ť
cb2=new Checkbox("Ů",cg,false);
c1=new Choice();//����ѡ���
c1.addItem("���ù���ѧԺ");//���ѡ����ѡ��
c1.addItem("��ѧԺ");
c1.addItemListener(this);//ע��ѡ����¼���������
c2=new Choice();//����ѡ���
c2.addItem("��Ϣϵ");
b1=new Button("���");b2=new Button("����");
b3=new Button("���ļ�");b4=new Button("����");
b1.addActionListener(this);b2.addActionListener(this);
b3.addActionListener(this);b4.addActionListener(this);
p1.add(label1);p1.add(label2);//���������ӵ����p1
p1.add(tf1);p1.add(tf2);p1.add(cb1);p1.add(cb2);
p1.add(c1);p1.add(c2);p1.add(b1);p1.add(b2);p1.add(b3);p1.add(b4);
f.addWindowListener(this);
f.setVisible(true);
b1.setEnabled(false);b2.setEnabled(false);
}
public void windowClosing(WindowEvent e)
{System.exit(0);}
public void actionPerformed(ActionEvent e)
{String str=new String("");
if(e.getSource()==b1)//��������ӡ���ťʱ����ʾ�������
{if(cb1.getState())str=cb1.getLabel();
if(cb2.getState())str=cb2.getLabel();
str=tf1.getText()+""+tf2.getText()+""+str+""+c1.getSelectedItem()+""+c2.getSelectedItem();
ls1.add(str);
vect.addElement(str);
tf1.setText(""+(Integer.parseInt(tf1.getText())+1));
}
if(e.getSource()==b2){//�������水ťʱ��������ݱ��浽�ļ���
try{
FileOutputStream fout=new FileOutputStream("student.txt",true);
BufferedWriter out=new BufferedWriter(new OutputStreamWriter(fout));
for(int i=0;i<vect.size();i++){
String xx1=vect.elementAt(i).toString();
out.write(xx1+"\n");}//д�����
out.close();vect.clear();}
catch(FileNotFoundException fe){}
catch(IOException ioe){}
}
if(e.getSource()==b3){//�����򿪰�ť���ļ���ʾ�ļ�����
try{FileInputStream fin=new FileInputStream("student.txt");
BufferedReader in=new BufferedReader(new InputStreamReader(fin));
for(int i=0;;){String xx=in.readLine();//��������
if(xx==null)
break;
ls1.add(xx);
vect.addElement(xx);}
b1.setEnabled(true);b2.setEnabled(true);
in.close(); vect.clear();}
catch(FileNotFoundException fe){}
catch(IOException ioe){}
}
if(e.getSource()==b4){ls1.removeAll();}
}
public void itemStateChanged(ItemEvent e)
{if(c1.getSelectedIndex()==0)//��ѡ���c1����ʱ����
{c2.removeAll();//���ѡ���c2ȫ������
c2.addItem("��Ϣϵ");//ѡ���c2�������
c2.addItem("����ϵ");
c2.addItem("���ϵ");}
if(c1.getSelectedIndex()==1)
{c2.removeAll();
c2.addItem("��ѧϵ");
c2.addItem("����ϵ");
c2.addItem("ģ��ϵ");}
}
}
