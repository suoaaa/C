import java.io.*;
public class A
{public static void main(String[]args)throws IOException
{FileReader in=new FileReader("B.java");//�����ļ�������
BufferedReader bin=new BufferedReader(in);//��������������
FileWriter out=new FileWriter("B.txt",true);//�����ļ������
String s;
while((s=bin.readLine())!=null)
{System.out.println(s);
out.write(s+"\n");
}
in.close();
out.close();
}
}
