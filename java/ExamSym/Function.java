package ExamSym;
/*
 * XXX XXXXXXXXXXXXX
 * һ������JAVA�Ŀ��Լ�����ϵͳ
 * ϵͳ: windows 10
 * IDE: Eclipse
 * JRE: jre 1.8.0_251
 * ���ӿƼ���ѧ2020�괺JAVA���Գ������
 * ������Ϊ����ʵ�ֵ�������
 * ��ϵͳʵ�ֹ��ܺ����з����뿴readme.md
 * Date: Wed Jun 17 10:27:35 GMT+08:00 2020
 */

 import javax.imageio.ImageIO;//�����������
 import java.util.Random;
 import java.lang.Math;
 import javax.swing.*;
 
 import java.awt.*;
 import java.io.*;
 import java.awt.event.*;
 import java.awt.image.BufferedImage;
 import java.util.*;
 
 class Function implements ActionListener
 {
	 /* 
	  * �������ڡ���ǩ
	  */
	 Date now=new Date();
	 JButton b1;
	 JLabel l1;
	 JFrame f = new JFrame("��������ϵͳ");
	 JPanel p1 = new JPanel();
	 JPanel p2 = new JPanel();
	 JPanel p3 = new JPanel();
	 JPanel p4 = new JPanel();
 
	 // ��ź��ܷ�
	 int index = 0,total = 0;
	 
	 // 15λ����洢��Ŀ���
	 int[] QueList = new int[15];
	 
	 // �����ļ�����·���Զ���д��
	 File file = new File("E:\\���˱��\\����-ȫ\\java\\ExamSym\\�����2.txt");
	 //File file = new File("D:\\Desktop\\�����2.txt");//��������Դ
	 File ans = new File("E:\\���˱��\\����-ȫ\\java\\ExamSym\\��������.txt");
	 QuestionBank questions[] = new QuestionBank[100];
 
	 JLabel questionString = new JLabel("��Ŀ��");
	 JLabel choiceString = new JLabel("ѡ�");
	 JLabel totalString = new JLabel("�ܷ֣�");
	 TextArea question = new TextArea("",2,30,2);
	 JTextField totaltf = new JTextField("0",10);
	 JRadioButton choiceA = new JRadioButton("A");
	 JRadioButton choiceB = new JRadioButton("B");
	 JRadioButton choiceC = new JRadioButton("C");
	 JRadioButton choiceD = new JRadioButton("D");
	 ButtonGroup radioGroup = new ButtonGroup();
	 
	 //��Button���������
	 JButton beginbt = new JButton("���⿪ʼ");
	 JButton nextbt = new JButton("�¸���Ŀ");
	 JButton practiceAgainbt = new JButton("���¿�ʼ");
 
	 Function()
	 {
		 /* 
		  * ϵͳ����
		  */
		 f.setSize(400,400);
		 f.setLocation(50,50);
		 f.setVisible(true);
		 
		 f.setLayout(new GridLayout(4,1,10,10));
		 p1.setLayout(new FlowLayout(FlowLayout.CENTER));
		 p2.setLayout(new FlowLayout(FlowLayout.CENTER));
		 p3.setLayout(new FlowLayout(FlowLayout.CENTER));
		 p4.setLayout(new FlowLayout(FlowLayout.CENTER));
		 
		 radioGroup.add(choiceA);
		 radioGroup.add(choiceB);
		 radioGroup.add(choiceC);
		 radioGroup.add(choiceD);
		 
		 // �������
		 p1.add(questionString);
		 p1.add(question);
		 
		 // ���ѡ��
		 p2.add(choiceString);
		 p2.add(choiceA);
		 p2.add(choiceB);
		 p2.add(choiceC);
		 p2.add(choiceD);
		 
		 // ����ܷ�
		 p3.add(totalString);
		 p3.add(totaltf);
		 
		 // ���������ť
		 p4.add(beginbt);
		 p4.add(nextbt);
		 p4.add(practiceAgainbt);
			 
		 f.add(p1);
		 f.add(p2);
		 f.add(p3);
		 f.add(p4);
		 
		 nextbt.addActionListener(this);
		 beginbt.addActionListener(this);
		 practiceAgainbt.addActionListener(this);
				 
		 CreatNum();
		 readFile();
		 
		 // ʹ����Ϊϵͳ���
		 try 
		 { 
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //ϵͳ���
			 // UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//Nimbus���
		 }
		 catch (Exception e)
		 { 
			 e.printStackTrace();
		 }	
	 }
	 
	 /* 
	  * ���������Ų������ǴӴ�С����
	  */
	 void CreatNum(){
		 for (int i = 0;i<15;i++){
			 int j = (int)(Math.random()*50);
			 QueList[i] = j;
		 }
		 Arrays.sort(QueList);
	 }
	 
	 /*
	  * ��ָ���ļ��ж�ȡ��Ŀѡ��ʹ�
	  */
	 void readFile()
	 {
		 String s,question,choiceA,choiceB,choiceC,choiceD,answer;
		 try
		 {
			 int count=0;
			 FileInputStream in=new FileInputStream(file);//����Ŀ�ļ�
			 byte b[]=new byte[in.available()];
			 in.read(b);
			 s=new String(b);
			 
			 // �ָ���Ŀ��ѡ��
			 String c[]=s.split("&&");
			 
			 for(int i=0;i<c.length;i++)
			 {
				 // ������Ŀѡ��ʹ�
				 question = c[i++];
				 choiceA = c[i++];
				 choiceB = c[i++];
				 choiceC = c[i++];
				 choiceD = c[i++];
				 answer = c[i];
				 questions[count++] = new QuestionBank(question,choiceA,choiceB,choiceC,choiceD,answer);
			 }
		 }catch(Exception e1){ e1.printStackTrace();}
	 }
	 
	 /*
	  * �жϻش��Ƿ���ȷ��������Ӧ���ܷ��ۼ�
	  */
	 void ifRight()
	 {
		 // ��ȡ����Ŀ�ļ��ĵ���λ
		 // ����ȷ�ܷ�total��һ
		 if(choiceA.isSelected())
		 {
			 if(questions[(int)QueList[index-1]].answer.equals(choiceA.getText()))//
			 {
				 total+=1;
			 }
			 // �����������д��ָ�����ļ�����
			 try {
				 FileWriter out = new FileWriter(ans, true);
				 out.write("["+(1+QueList[(index-1)]));
				 out.write("��A]   ");
				 out.flush();
				 out.close();
			 } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
		 }
		 
		 if(choiceB.isSelected())
		 {
			 if(questions[QueList[index-1]].answer.equals(choiceB.getText()))
			 {
				 total+=1;
			 }
			 // �����������д��ָ�����ļ�����
			 try {
				 FileWriter out = new FileWriter(ans, true);
				 out.write("["+(QueList[(index-1)]+1));
				 out.write("��B]   ");
				 out.flush();
				 out.close();
			 } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
		 }
		 
		 if(choiceC.isSelected())
		 {
			 if(questions[QueList[index-1]].answer.equals(choiceC.getText()))
			 {
				 total+=1;
			 }
			 // �����������д��ָ�����ļ�����
			 try {
				 FileWriter out = new FileWriter(ans, true);
				 out.write("["+(1+QueList[(index-1)]));
				 out.write("��C]   ");
				 out.flush();
				 out.close();
			 } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
		 }
		 
		 if(choiceD.isSelected())
		 {
			 if(questions[QueList[index-1]].answer.equals(choiceD.getText()))
			 {
				 total+=1;
			 }
			 // �����������д��ָ�����ļ�����
			 try {
				 FileWriter out = new FileWriter(ans, true);
				 out.write("["+(1+QueList[(index-1)]));
				 out.write("��D]   ");
				 out.flush();
				 out.close();
			 } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
		 }
		 
		 // ���ж�����һѡ���Ƿ���ȷ�������ܷ�
		 totaltf.setText(Integer.toString(total));
	 }
	 
	 /*
	  * �������Ĳ���polar������Ŀ��ѡ������Ϊ��polarλ
	  */
	 void setQuestion(int polar)
	 {
		 // ������Ŀѡ��
		 question.setText(questions[polar].question);
		 choiceA.setText(questions[polar].choiceA);
		 choiceB.setText(questions[polar].choiceB);
		 choiceC.setText(questions[polar].choiceC);
		 choiceD.setText(questions[polar].choiceD);
		 radioGroup.clearSelection();
	 }
	 
	 /*
	  * ���¸�����ť��Ľ��
	  */
	 public void actionPerformed(ActionEvent e)
	 {
		 
		 // ���¡��¸���Ŀ����index��һ����ת����һ��
		 if(e.getSource() == nextbt)
		 {
			 ifRight();
			 if(index<11)
			 {
				 setQuestion((int)QueList[index]);
				 index += 1;
			 }
			 
		 }
		 
		 // ���¡���ʼ���⡱���ڴ��������ļ���д��̧ͷ
		 if(e.getSource() == beginbt)
		 {
			 try {
				 FileWriter out = new FileWriter(ans, true);
				 out.write(
					"\n\n =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n"+
					"                                                                        \n"+
					"                       "          +now.toString()+        "                 \n"+
					"      ,                                                                \n"+
					"     //\\^/`\\                                                           \n"+
					"    | \\/    |                                                           \n"+
					"    | |     |             "+"���ӿƼ���ѧJAVA���Գ������"+"                   \n"+
					"     \\ \\    /                                                      _ _       \n"+
					"      '\\\\//'                                                     _{ ' }_     \n"+
					"        ||                      XXX XXXXXXXXXXXXX              { `.!.` }    \n"+
					"        ||                      <XXXXXXXXX@qq.com>               ',_/Y\\_,'    \n"+
					"        ||  ,                                                     {_,_}      \n"+
					"    |\\  ||  |\\                                                      |        \n"+
					"    | | ||  | |                     MY BLOG:                      (\\|  /)    \n"+
					"    | | || / /            <https://uestcjhx.github.io/>            \\| //     \n"+
					"     \\ \\||/ /                                                       |//      \n"+
					"      `\\\\//`   \\\\   \\./    \\\\ /     //    \\\\./         \\\\   //   \\\\ |/ /     \n"+
					" ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ \n"
						   +"\n");
				 out.flush();
				 out.close();
			 } catch (IOException x) {
				 // TODO Auto-generated catch block
				 // e.printStackTrace();
			 }
		 }
		 
		 // ���¡����¿�ʼ�����򷵻ص�һ�⣬������Ϊ0
		 if(e.getSource() == practiceAgainbt)
		 {
			 index = 0;
			 total = 0;//����Ϊ0
			 totaltf.setText(Integer.toString(total));
			 try {
				 FileWriter out = new FileWriter(ans, true);
				 out.write("\n\n");
				 out.flush();
				 out.close();
			 } catch (IOException x) {
				 // TODO Auto-generated catch block
				 // x.printStackTrace();
			 }
		 }
	 }
 }