import java.awt.Color; import java.awt.*; import java.awt.List;
import java.util.*; import java.awt.event.*; import java.io.*;
public class A extends WindowAdapter implements ActionListener,ItemListener
{Frame f; Vector vect=new Vector();
Label label1=new Label("输入编号"); Label label2=new Label("输入姓名");
TextField tf1,tf2;TextArea area;
Checkbox cb1,cb2;Choice c1,c2;
List ls1; Button b1,b2,b3,b4;
public static void main(String arg[])
{(new A()).init();}
public void init()
{Panel p1,p2;CheckboxGroup cg;
f=new Frame("输入学生基本信息窗口");
f.setSize(480,200);f.setLocation(200,150);
f.setBackground(Color.lightGray);
f.setLayout(new GridLayout(1,2)); //网格布局，左右分隔窗口
ls1=new List();//创建列表框
f.add(ls1);//占据窗口左半部分
p1=new Panel();
p1.setLayout(new GridLayout(6,2));//网格布局，6行1列
f.add(p1);//占据窗口右半部分
tf1=new TextField("1");tf2=new TextField("Name");
cg=new CheckboxGroup();//创建复选框组
cb1=new Checkbox("男",cg,true);//创建单选按钮
cb2=new Checkbox("女",cg,false);
c1=new Choice();//创建选择框
c1.addItem("经济管理学院");//添加选择框的选项
c1.addItem("理学院");
c1.addItemListener(this);//注册选择框事件监听程序
c2=new Choice();//创建选择框
c2.addItem("信息系");
b1=new Button("添加");b2=new Button("保存");
b3=new Button("打开文件");b4=new Button("清屏");
b1.addActionListener(this);b2.addActionListener(this);
b3.addActionListener(this);b4.addActionListener(this);
p1.add(label1);p1.add(label2);//组件依次添加到面板p1
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
if(e.getSource()==b1)//单击“添加”按钮时，显示添加内容
{if(cb1.getState())str=cb1.getLabel();
if(cb2.getState())str=cb2.getLabel();
str=tf1.getText()+""+tf2.getText()+""+str+""+c1.getSelectedItem()+""+c2.getSelectedItem();
ls1.add(str);
vect.addElement(str);
tf1.setText(""+(Integer.parseInt(tf1.getText())+1));
}
if(e.getSource()==b2){//单击保存按钮时将添加内容保存到文件中
try{
FileOutputStream fout=new FileOutputStream("student.txt",true);
BufferedWriter out=new BufferedWriter(new OutputStreamWriter(fout));
for(int i=0;i<vect.size();i++){
String xx1=vect.elementAt(i).toString();
out.write(xx1+"\n");}//写入对象
out.close();vect.clear();}
catch(FileNotFoundException fe){}
catch(IOException ioe){}
}
if(e.getSource()==b3){//单击打开按钮打开文件显示文件内容
try{FileInputStream fin=new FileInputStream("student.txt");
BufferedReader in=new BufferedReader(new InputStreamReader(fin));
for(int i=0;;){String xx=in.readLine();//读出内容
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
{if(c1.getSelectedIndex()==0)//对选择框c1操作时触发
{c2.removeAll();//清除选择框c2全部内容
c2.addItem("信息系");//选择框c2添加内容
c2.addItem("经济系");
c2.addItem("会计系");}
if(c1.getSelectedIndex()==1)
{c2.removeAll();
c2.addItem("数学系");
c2.addItem("物理系");
c2.addItem("模型系");}
}
}
