import java.io.*;
public class A
{public static void main(String[]args)throws IOException
{FileReader in=new FileReader("B.java");//建立文件输入流
BufferedReader bin=new BufferedReader(in);//建立缓冲输入流
FileWriter out=new FileWriter("B.txt",true);//建立文件输出流
String s;
while((s=bin.readLine())!=null)
{System.out.println(s);
out.write(s+"\n");
}
in.close();
out.close();
}
}
