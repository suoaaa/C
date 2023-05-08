import java.io.*;import java.awt.*;import java.awt.event.*;
public class A extends Frame implements ActionListener
{FileDialog fileDlg;
String str, fileName;
byte byteBuf[]=new byte[10000];
TextArea ta=new TextArea();
MenuBar mb=new MenuBar();
Menu m1=new Menu("文件");
MenuItem open=new MenuItem("打开");
MenuItem close=new MenuItem("关闭");
MenuItem save=new MenuItem("保存");
MenuItem exit=new MenuItem("退出");
A(){setTitle("简易文本编辑器");
setSize(400,280); add("Center",ta);
addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e)
{System.exit(0);}
});
m1.add(open);m1.add(close);m1.add(save);
m1.addSeparator();m1.add(exit);
open.addActionListener(this);close.addActionListener(this);
save.addActionListener(this);exit.addActionListener(this);
mb.add(m1);
setMenuBar(mb);
show();
}
public void actionPerformed(ActionEvent e)
{if(e.getSource()==exit)System.exit(0);
else if(e.getSource()==close)//关闭文件
ta.setText(null);//设置文本区为空
else if(e.getSource()==open)//打开文件
{fileDlg=new FileDialog(this,"打开文件");//生成文件对话框
fileDlg.show();//显示文件对话框
fileName=fileDlg.getFile();//获取文件名
try{
FileInputStream in=new FileInputStream(fileName);//建立文件输入流
in.read(byteBuf);//将文件内容读到字节数组
in.close();//关闭文件输入流
str=new String(byteBuf);//将字节数组转换成字符串
ta.setText(str);//将字符串显示在文字区
setTitle("简易文本编辑器一"+fileName);
}
catch(IOException ioe){}
}
else if(e.getSource()==save)//保存文件
{fileDlg=new FileDialog(this,"保存文件",FileDialog.SAVE);//生成文件对话框
fileDlg.show();
fileName=fileDlg.getFile();
str=ta.getText();//将文本区内容读至字符串
byteBuf=str.getBytes();//将字符串转换成字节数组
try{
FileOutputStream out=new FileOutputStream(fileName);//建立文件输出流
out.write(byteBuf);//将字节数组写入文件输出流
out.close();//关闭文件输出流
}
catch(IOException ioe){}
}
}
public static void main(String args[])
{new A();}
}
