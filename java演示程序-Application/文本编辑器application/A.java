import java.io.*;import java.awt.*;import java.awt.event.*;
public class A extends Frame implements ActionListener
{FileDialog fileDlg;
String str, fileName;
byte byteBuf[]=new byte[10000];
TextArea ta=new TextArea();
MenuBar mb=new MenuBar();
Menu m1=new Menu("�ļ�");
MenuItem open=new MenuItem("��");
MenuItem close=new MenuItem("�ر�");
MenuItem save=new MenuItem("����");
MenuItem exit=new MenuItem("�˳�");
A(){setTitle("�����ı��༭��");
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
else if(e.getSource()==close)//�ر��ļ�
ta.setText(null);//�����ı���Ϊ��
else if(e.getSource()==open)//���ļ�
{fileDlg=new FileDialog(this,"���ļ�");//�����ļ��Ի���
fileDlg.show();//��ʾ�ļ��Ի���
fileName=fileDlg.getFile();//��ȡ�ļ���
try{
FileInputStream in=new FileInputStream(fileName);//�����ļ�������
in.read(byteBuf);//���ļ����ݶ����ֽ�����
in.close();//�ر��ļ�������
str=new String(byteBuf);//���ֽ�����ת�����ַ���
ta.setText(str);//���ַ�����ʾ��������
setTitle("�����ı��༭��һ"+fileName);
}
catch(IOException ioe){}
}
else if(e.getSource()==save)//�����ļ�
{fileDlg=new FileDialog(this,"�����ļ�",FileDialog.SAVE);//�����ļ��Ի���
fileDlg.show();
fileName=fileDlg.getFile();
str=ta.getText();//���ı������ݶ����ַ���
byteBuf=str.getBytes();//���ַ���ת�����ֽ�����
try{
FileOutputStream out=new FileOutputStream(fileName);//�����ļ������
out.write(byteBuf);//���ֽ�����д���ļ������
out.close();//�ر��ļ������
}
catch(IOException ioe){}
}
}
public static void main(String args[])
{new A();}
}
