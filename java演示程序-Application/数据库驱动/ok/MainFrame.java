//���Ե��ϵͳ

import javax.swing.*;              
import java.awt.event.*;
import java.awt.*;
import java.sql.*;            
import java.net.*;
import java.io.*;
import java.util.*;


//����������
class MainFrame extends JFrame implements ItemListener,ActionListener ,Runnable
{
	  Container     contentPane=null;
	  JButton       confirmDish,checkConfirmedDish,payBill,previousDish,
	                nextDish,lookUpDish,suggestion,servicerButton;
	  JTextField    interLookUp=null;
	  JComboBox     sortDish=null;
	  JSplitPane    split_H=null,split_VL=null,split_VR=null;
	  JPanel        dishPane=null,suggestionPane;
	  JLabel        label1,label2,label3,servicerLabel,payforLabel,label_foodName,label_caixi,
	                label_cookStyle,label_taste,label_material,label_seson,label_price,
	                l_foodNameC,l_caixiC,l_cookStyleC,l_tasteC,l_materialC,l_sesonC,l_priceC,
	                name,nameC,danjia,danjiaC,dateLabel,timeLabel;
	  JTextArea     detail;
	  JScrollPane   scroll;
    JMenuBar      menuBar;
    JMenu         admin,help,administrator,culture;
    JMenuItem     updateData,insertData,deleteData,about,cultureIntro;
//	  JToolBar      toolBar;
//	  Image         ima;
//	  Toolkit       tool;
	  DisplayDish   displayDish=null;
	  Connection    con;
	  Statement     sql;
	  ResultSet     rs;
	  Font          font,fontInDetail,fon1,fon2;
	  static BeginFrame    bFrame =null;
	  String[] allDishes= new String[100]; //����ѵ��
	  String[] allDrink=new String[50];
	  String[] allName=new String[150];
	  String[] allPrice=new String[150];
	  int           dishNum=0,drinkNum=0;   //�ѵ��,��ˮ�ļ�����
	  int           allNameNum=0,allPriceNum=0,choose=0;
	  int           billNum=0;
	  LiveMess      liveMess;
	  CheckConfirmedDishFrame    checkConfirmedDishFrame=null; //�鿴�ѵ�˵Ĵ���
	  Grade       grade=null;    //Ϊ������Ա���ֵĴ���
	  LinkedList  confirmedDishLink,confirmedPriceLink;
	  boolean      confirmedDishIsChaneged=false;
//	  IntroducePane  introducePane;
	  IntroduceFrame      introduceFrame;  //�����Ļ����ܴ���
//	  UpdateDataFrame    updateDataFrame;
//	  InsterDataFrame    insterDataFrame;
//	  DeleteDataFrame    deleteDataFrame;
	  Thread         timer;    //ʱ�ӵĽ���
	  
	  
	  
	  MainFrame(String ss)
	  {
	  	 super(ss);
	  	 
//	  	 System.out.println(""+user.home);
	  	 
	  	 font=new Font("Dialog",Font.PLAIN,22);
       fontInDetail=new Font("Dialog",Font.PLAIN,16);
	  	 fon1=new Font("Dialog",Font.PLAIN,14);
	  	 fon2=new Font("Dialog",Font.PLAIN,12);
	  	 
	  	 
	  	 confirmDish=new JButton("�������");
	  	 confirmDish.addActionListener(this);
	  	 confirmDish.setFont(fon1);
	  	 confirmDish.setBackground(new Color (153,179,179));
	  	 checkConfirmedDish=new JButton("�鿴�ѵ��");
	  	 checkConfirmedDish.setBackground(new Color (153,179,179));
	  	 checkConfirmedDish.setFont(fon1);
	  	 checkConfirmedDish.addActionListener(this);
	  	 payBill=new JButton("Pay");
       payBill.setBackground(new Color (153,179,179));
       payBill.setFont(fon1);
       payBill.addActionListener(this);  
	  	 previousDish=new JButton("Prev");
	  	 previousDish.setBackground(new Color (128,128,255));
	  	 previousDish.setFont(fon1);
	  	 previousDish.addActionListener(this);
	  	 nextDish=new JButton("Next");
	  	 nextDish.setBackground(new Color (128,128,255));
	  	 nextDish.setFont(fon1);
	  	 nextDish.addActionListener(this);
	  	 lookUpDish=new JButton("GO");
	  	 lookUpDish.setBackground(new Color (153,179,179));
	  	 lookUpDish.setFont(fon2);
	  	 lookUpDish.addActionListener(this);
	  	 suggestion=new JButton("Write");
	  	 suggestion.setBackground(new Color (153,179,179));	  	
	  	 suggestion.setFont(fon1);
	  	 suggestion.addActionListener(this);
       servicerButton=new JButton("����");
       servicerButton.setBackground(new Color (153,179,179));
       servicerButton.setFont(fon1);
       servicerButton.addActionListener(this);  
//	  	 help=new JButton("����");
//	  	 tools=new JButton("����");
	  	 
       String[] sort={"","�Ȳ�","���","��ɫ��","����","С��","��ˮ"};
       sortDish=new JComboBox(sort);
       sortDish.setFont(fon2);
       sortDish.setBackground(new Color (36,219,209));
       sortDish.setForeground(new Color (250,0,0));
       sortDish.addItemListener(this);
       
       
       interLookUp=new JTextField(10);
       
       dishPane=new JPanel();
       displayDish=new DisplayDish();
       suggestionPane=new JPanel();
       
       confirmedDishLink=new LinkedList();
       confirmedPriceLink=new LinkedList();
       
       
       
       label1=new JLabel("���ղ���");
       label1.setFont(fon1);
       label2=new JLabel("��ѯ");
       label2.setFont(fon1);
       label3=new JLabel("���½���");
       label3.setFont(fon1);
       servicerLabel=new JLabel("Ϊ����Ա");
       servicerLabel.setFont(fon1);
       payforLabel=new JLabel("��      ��");
       payforLabel.setFont(fon1);
       label_foodName=new JLabel("��       ��:");
       label_foodName.setFont(font);
       label_caixi=new JLabel("��       ϵ:");
       label_caixi.setFont(font);
       label_cookStyle=new JLabel("�������:");   
       label_cookStyle.setFont(font);
       label_taste=new JLabel("ζ       ��:");
       label_taste.setFont(font);
       label_material=new JLabel("��       ��:");
       label_material.setFont(font);
       label_seson=new JLabel("���˼���:");
       label_seson.setFont(font);
       label_price=new JLabel("��       ��:");
       label_price.setFont(font);
	     l_foodNameC=new JLabel();
	     l_foodNameC.setFont(font);
	     l_caixiC=new JLabel();
	     l_caixiC.setFont(font);
	     l_cookStyleC=new JLabel();
	     l_cookStyleC.setFont(font);
	     l_tasteC=new JLabel();
	     l_tasteC.setFont(font);
	     l_materialC=new JLabel();
	     l_materialC.setFont(font);
	     l_sesonC=new JLabel();
	     l_sesonC.setFont(font);
	     l_priceC=new JLabel();
       l_priceC.setFont(font);
       name=new JLabel("��       ��:");
       name.setFont(font);
       nameC=new JLabel("");
       nameC.setFont(font);
       danjia=new JLabel("��       ��:");
       danjia.setFont(font);
       danjiaC=new JLabel("");
       danjiaC.setFont(font);
       dateLabel=new JLabel();
       dateLabel.setFont(fon1);
       dateLabel.setForeground(new Color(0,128,255));
       timeLabel=new JLabel();
       timeLabel.setFont(fon1);
       timeLabel.setForeground(new Color(0,128,255));
       
       
       
       
//      label4=new JLabel("��ϸ");
       
//       detail=new JTextArea();
//       detail.setEditable(false);
//       detail.setFont(fontInDetail);
//       detail.setLineWrap(true);
//       detail.setWrapStyleWord(true);
       
//         introducePane=new IntroducePane();
         URL    url=getClass().getResource("p3.jpg");
         JPanel picPane=new JPanel();
         picPane.add(new JLabel(new ImageIcon(url)));
//         scroll=new JScrollPane(picPane);


//       toolBar=new JToolBar();
//       toolBar.add(tools);
//       toolBar.add(help);
              
       
       menuBar=new JMenuBar();
       admin=new JMenu("����Ա");
       admin.setFont(fon2);
       help=new JMenu("����");
       help.setFont(fon2);
       culture=new JMenu("����");
       culture.setFont(fon2);
       updateData=new JMenuItem("�޸�����");
       updateData.setFont(fon2);
       updateData.addActionListener(this);
       insertData=new JMenuItem("��������");
       insertData.setFont(fon2);
       insertData.addActionListener(this);
       deleteData=new JMenuItem("ɾ������");
       deleteData.setFont(fon2);
       deleteData.addActionListener(this);
       about=new JMenuItem("����");
       about.addActionListener(this);
       cultureIntro=new JMenuItem("�����Ļ�");
       cultureIntro.setFont(fon2);
       cultureIntro.addActionListener(this);
       admin.add(updateData);
       admin.addSeparator();
       admin.add(insertData);
       admin.addSeparator();
       admin.add(deleteData);
       
       help.add(about);
       culture.add(cultureIntro);
       menuBar.add(admin);
       menuBar.add(culture);
       menuBar.add(help);
       
       liveMess=new LiveMess("���Բ�");
       checkConfirmedDishFrame=new CheckConfirmedDishFrame("�ѵ���б�");
       grade=new Grade("Ϊ����Ա����");
       introduceFrame=new IntroduceFrame();
//       updateDataFrame=new UpdateDataFrame("�޸�����");   //����Ա�޸����ݴ���
//       insterDataFrame=new InsterDataFrame("��������");   //����Ա�������ݴ���
//       deleteDataFrame=new DeleteDataFrame("ɾ������");   //����Աɾ�����ݴ���
       
       
       //Ϊ dishPane ����,��Ϊ��������
       dishPane.setLayout(null);
       dishPane.add(label1);
       label1.setBounds(10,20,65,25);
       dishPane.add(sortDish);
       sortDish.setBounds(75,20,80,25);
       dishPane.add(label2);
       label2.setBounds(10,62,65,25);
       dishPane.add(interLookUp);
       interLookUp.setBounds(10,95,90,25);
       dishPane.add(lookUpDish);
       lookUpDish.setBounds(100,95,55,25);
       
       
       //Ϊ suggtionPane ����,��Ϊ��������    
       suggestionPane.setLayout(null);
       suggestionPane.add(payforLabel);
       payforLabel.setBounds(10,20,65,25);
       suggestionPane.add(payBill);
       payBill.setBounds(75,20,70,25);
       suggestionPane.add(label3);
       label3.setBounds(10,70,65,25);
       suggestionPane.add(suggestion);
       suggestion.setBounds(75,70,70,25);
       suggestionPane.add(servicerLabel);
       servicerLabel.setBounds(10,120,65,25);
       suggestionPane.add(servicerButton);
       servicerButton.setBounds(75,120,70,25);
       suggestionPane.add(dateLabel);
       dateLabel.setBounds(34,230,200,30);
       suggestionPane.add(timeLabel);
       timeLabel.setBounds(35,260,200,30);
       
       
       //Ϊ displayDish ����,��Ϊ��������
       displayDish.setLayout(null);

       displayDish.add(confirmDish);
       confirmDish.setBounds(100,5,95,25);
       displayDish.add(checkConfirmedDish);
       checkConfirmedDish.setBounds(210,5,105,25);
       displayDish.add(previousDish);
       previousDish.setBounds(330,5,75,25);
       displayDish.add(nextDish);
       nextDish.setBounds(420,5,75,25);
       nextDish.addActionListener(this);
       
//       nextDish.setBounds(500,5,75,25);
       //ճ��ͼƬ
     //  tool=getToolkit();  
     //  ima=tool.getImage("start.jpg");
       
       
       split_VL=new JSplitPane(JSplitPane.VERTICAL_SPLIT,dishPane,suggestionPane);
       split_VL.setDividerLocation(220);
  //     split_VL.setBackground(Color.red);
       split_VR=new JSplitPane(JSplitPane.VERTICAL_SPLIT,displayDish,picPane);
       split_VR.setDividerLocation(452);
       split_H=new  JSplitPane(JSplitPane.HORIZONTAL_SPLIT,split_VL,split_VR);
       split_H.setDividerLocation(170);
       
       //�������ڽ��г�ʼ��
       contentPane=getContentPane();
       contentPane.add(menuBar,BorderLayout.NORTH);
       contentPane.add(split_H);
       
       
       setVisible(false);
      
//       setBackground(new Color(186,185,133));   //���ñ���ɫ�������� 
       setResizable(false);
       
       
       //���ô�������Ļ���м�
       Dimension  screen=getToolkit().getScreenSize();
       setBounds(  (screen.width-800)/2,(screen.height-600)/2,800,600);
       
       
       addWindowListener(new WindowAdapter()
                             {
                             	 public void windowClosing(WindowEvent e)
                             	 {
                             	 	  System.exit(0);
                             	 	}
                             	} );
                             	
       validate();
    
       timer=new Thread(this);
       
       timer.start();//��ʼһ��ʱ��
    
    }
    
    
    //������ղ����¼�
    public void itemStateChanged(ItemEvent e)
    { 
    	 
    	
    	 if(e.getItemSelectable()==sortDish)
    	 { 
    	 	  if(!(sortDish.getSelectedItem().equals("")))
    	 	  {
    	 	  	 remove(displayDish);
    	 	  	 validate();
    	 	  
    	 	     
    	 	     
    	 	     //����choose��ֵ�жϴ�sortDishѡ������������͵Ĳ�
    	 	     choose=sortDish.getSelectedIndex();
    	 	  
    	 	     
    	 	     //ѡ������Ȳ�
    	 	     if(choose==1)
    	 	     {
    	 	     	  
    	 	     	  String foodName,caixi,cookStyle,taste,material,seson,price;
    	 	     	  
    	 	     	  
    	 	     	  try
    	 	        {
    	 	     	      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	 	        }
    	 	        catch(ClassNotFoundException ek)
    	 	        {
    	 	     	     System.out.print(""+ek);
    	 	        }
    	 	     	  
    	 	     	  
    	 	     	  try
    	 	        {
    	 	     	     con=DriverManager.getConnection("jdbc:odbc:foodmenu","","");
    	 	     	     sql=con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
    	 	     	     rs=sql.executeQuery("SELECT  * FROM cookbook WHERE  ����='�Ȳ�' " );
    	 	     	 
    	 	     	     rs.next();
    	 	     	     
    	 	     	 	   foodName=rs.getString(1);
    	 	     	 	   caixi=rs.getString(3);
    	 	     	 	   cookStyle=rs.getString(4);
    	 	     	 	   taste=rs.getString(5);
    	 	     	 	   material=rs.getString(6);
    	 	     	 	   seson=rs.getString(7); 
    	 	     	 	   price=rs.getString(8);
    	 	     	 	   
    //	 	     	     con.close();
    	 	     	  

    	 	     	     if(foodName.equals("��������")){
    	 	     	        
    	 	     	        displayDish.setNum(0,true);
    	 	     	        displayDish.repaint();
    	 	     	       
    	 	     	        System.out.println("����");
    	 	     	     }   
    	 	            	      
    	 	
    	 	
    	 	
    	 	//     	  displayDish.remove(name);
    	 	//     	  displayDish.remove(nameC);
    	 	//     	  displayDish.remove(danjia);
    	 	//     	  displayDish.remove(danjiaC);
    	 	     	
    	 	     	     name.setText("");
    	 	     	     nameC.setText("");
    	 	     	     danjia.setText("");
    	 	     	     danjiaC.setText("");
    	 	     	     
    	 	     	  label_foodName.setText("��       ��:");
    	 	     	  label_caixi.setText("��       ϵ:");
    	 	     	  label_cookStyle.setText("�������");
	             label_taste.setText("ζ       ��:");
	              label_material.setText("��       ��:");
	              label_seson.setText("���˼���:");
	              label_price.setText("��       ��:");
	              
    	 	     	  
    	 	     	  
    	 	     	  
    	 	     	  displayDish.add(label_foodName);
    	 	        label_foodName.setBounds(80,60,200,40);
    	 	        displayDish.add(label_caixi);
    	 	        label_caixi.setBounds(80,100,200,40);
    	 	        displayDish.add(label_cookStyle);
    	 	        label_cookStyle.setBounds(80,150,200,40);
    	 	        displayDish.add(label_taste);
    	 	        label_taste.setBounds(80,200,200,40);
    	 	        displayDish.add(label_material);
    	 	        label_material.setBounds(80,250,200,40);
    	 	        displayDish.add(label_seson);
    	 	        label_seson.setBounds(80,300,200,40);
    	 	        displayDish.add(label_price);
    	 	        label_price.setBounds(80,350,200,40);
    	 	        
    	 	        l_foodNameC.setText(foodName);
    	 	        displayDish.add(l_foodNameC);
    	 	        l_foodNameC.setBounds(200,60,400,40);
    	 	        l_caixiC.setText(caixi);
    	 	        displayDish.add(l_caixiC);
    	 	        l_caixiC.setBounds(200,100,400,40);
    	 	        l_cookStyleC.setText(cookStyle);
    	 	        displayDish.add(l_cookStyleC);
    	 	        l_cookStyleC.setBounds(200,150,400,40);
    	 	        l_tasteC.setText(taste);
    	 	     	  displayDish.add(l_tasteC);
    	 	     	  l_tasteC.setBounds(200,200,400,40);
    	 	     	  l_materialC.setText(material);
    	 	        displayDish.add(l_materialC);
    	 	        l_materialC.setBounds(200,250,400,40);
    	 	        l_sesonC.setText(seson);
    	 	        displayDish.add(l_sesonC);
    	 	        l_sesonC.setBounds(200,300,400,40);
    	 	        l_priceC.setText(price);
    	 	        displayDish.add(l_priceC);
    	 	        l_priceC.setBounds(200,350,400,40);
    	 	     	  
    	 	     	  }
    	 	     	
                catch(SQLException ee)
    	 	     	  {
    	 	     		   System.out.print(""+ee);
    	 	     	  }	 	  	 
    	 	     
    	 	            	 	        

    	 	        
    	 	        
    	 	     
    	 	     
    	 	     }	  
    	 	     
    	 	     else if(choose==2)
    	 	     {
    	 	     	  
    	 	     	  String foodName,caixi,cookStyle,taste,material,seson,price;
    	 	     	  
    	 	     	  try
    	 	        {
    	 	     	     Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	 	        }
    	 	        catch(ClassNotFoundException ek)
    	 	        {
    	 	     	    System.out.print(""+ek);
    	 	        }
    	 	     	  
    	 	     	  try
    	 	        {
    	 	     	     con=DriverManager.getConnection("jdbc:odbc:foodmenu","","");
    	 	     	     sql=con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
    	 	     	     rs=sql.executeQuery("SELECT  * FROM cookbook WHERE  ����='���' " );
    	 	     	 
    	 	     	     rs.next();
    	 	     	     
    	 	     	 	     foodName=rs.getString(1);
    	 	     	 	     caixi=rs.getString(3);
    	 	     	 	     cookStyle=rs.getString(4);
    	 	     	 	     taste=rs.getString(5);
    	 	     	 	     material=rs.getString(6);
    	 	     	 	    seson=rs.getString(7); 
    	 	     	 	     price=rs.getString(8);
    	 	     	 	   
    	 	  //   	     con.close();
    	 	     	  
    	 	     	 
    	 	     	  if(foodName.equals("��˿����")){
    	 	     	        
    	 	     	        displayDish.setNum(9,true);
    	 	     	        displayDish.repaint();
    	 	     	       
    	 	     	        System.out.println("����"+foodName);
    	 	     	  }   
    	 	     	  
    	 	     	  name.setText("");
    	 	     	  nameC.setText("");
    	 	     	  danjia.setText("");
    	 	     	  danjiaC.setText("");
    	 	     	     
    	 	     	  label_foodName.setText("��       ��:");
    	 	     	  label_caixi.setText("��       ϵ:");
    	 	     	  label_cookStyle.setText("�������");
	             label_taste.setText("ζ       ��:");
	              label_material.setText("��       ��:");
	              label_seson.setText("���˼���:");
	              label_price.setText("��       ��:");
    	 	     	  
    	 	     	  displayDish.add(label_foodName);
    	 	        label_foodName.setBounds(80,60,200,40);
    	 	        displayDish.add(label_caixi);
    	 	        label_caixi.setBounds(80,100,200,40);
    	 	        displayDish.add(label_cookStyle);
    	 	        label_cookStyle.setBounds(80,150,200,40);
    	 	        displayDish.add(label_taste);
    	 	        label_taste.setBounds(80,200,200,40);
    	 	        displayDish.add(label_material);
    	 	        label_material.setBounds(80,250,200,40);
    	 	        displayDish.add(label_seson);
    	 	        label_seson.setBounds(80,300,200,40);
    	 	        displayDish.add(label_price);
    	 	        label_price.setBounds(80,350,200,40);
    	 	        
    	 	        l_foodNameC.setText(foodName);
    	 	        displayDish.add(l_foodNameC);
    	 	        l_foodNameC.setBounds(200,60,400,40);
    	 	        l_caixiC.setText(caixi);
    	 	        displayDish.add(l_caixiC);
    	 	        l_caixiC.setBounds(200,100,400,40);
    	 	        l_cookStyleC.setText(cookStyle);
    	 	        displayDish.add(l_cookStyleC);
    	 	        l_cookStyleC.setBounds(200,150,400,40);
    	 	        l_tasteC.setText(taste);
    	 	     	  displayDish.add(l_tasteC);
    	 	     	  l_tasteC.setBounds(200,200,400,40);
    	 	     	  l_materialC.setText(material);
    	 	        displayDish.add(l_materialC);
    	 	        l_materialC.setBounds(200,250,400,40);
    	 	        l_sesonC.setText(seson);
    	 	        displayDish.add(l_sesonC);
    	 	        l_sesonC.setBounds(200,300,400,40);
    	 	        l_priceC.setText(price);
    	 	        displayDish.add(l_priceC);
    	 	        l_priceC.setBounds(200,350,400,40);
    	 	     	  
    	 	     	  }
    	 	     	
    	 	     	  catch(SQLException ee)
    	 	     	  {
    	 	     		   System.out.print(""+ee);
    	 	     	  }	 	  	 
    	 	     
    	 	     }	  
    	 	     
    	 	     else if(choose==3)
    	 	     {
    	 	     	  String foodName,caixi,cookStyle,taste,material,seson,price;
    	 	     	  
    	 	     	  try
    	 	        {
    	 	     	     Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	 	        }
    	 	        catch(ClassNotFoundException ek)
    	 	        {
    	 	     	    System.out.print(""+ek);
    	 	        }
    	 	     	  
    	 	     	  try
    	 	        {
    	 	     	     con=DriverManager.getConnection("jdbc:odbc:foodmenu","","");
    	 	     	     sql=con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
    	 	     	     rs=sql.executeQuery("SELECT  * FROM cookbook WHERE  ����='��ɫ��' " );
    	 	     	 
    	 	     	     rs.next();
    	 	     	     
    	 	     	 	     foodName=rs.getString(1);
    	 	     	 	     caixi=rs.getString(3);
    	 	     	 	     cookStyle=rs.getString(4);
    	 	     	 	     taste=rs.getString(5);
    	 	     	 	     material=rs.getString(6);
    	 	     	 	     seson=rs.getString(7); 
    	 	     	 	     price=rs.getString(8);
    	 	     	 	   
    	 	    // 	     con.close();
    	 	     	  
    	 	     	   //  System.out.println(foodName+caixi+cookStyle+taste+material+seson+price);
    	 	     	  
    	 	     	  displayDish.setNum(9,false);
    	 	     	  displayDish.repaint();
    	 	     	  
    	 	     	   name.setText("");
    	 	    	     nameC.setText("");
    	 	     	   danjia.setText("");
    	 	     	     danjiaC.setText("");
    	 	     	   
    	 	     	   label_foodName.setText("��       ��:");
    	 	     	  label_caixi.setText("��       ϵ:");
    	 	     	  label_cookStyle.setText("�������");
	             label_taste.setText("ζ       ��:");
	              label_material.setText("��       ��:");
	              label_seson.setText("���˼���:");
	              label_price.setText("��       ��:");
    	 	     	     
    	 	     	    displayDish.add(label_foodName);
    	 	        label_foodName.setBounds(80,60,200,40);
    	 	        displayDish.add(label_caixi);
    	 	        label_caixi.setBounds(80,100,200,40);
    	 	        displayDish.add(label_cookStyle);
    	 	        label_cookStyle.setBounds(80,150,200,40);
    	 	        displayDish.add(label_taste);
    	 	        label_taste.setBounds(80,200,200,40);
    	 	        displayDish.add(label_material);
    	 	        label_material.setBounds(80,250,200,40);
    	 	        displayDish.add(label_seson);
    	 	        label_seson.setBounds(80,300,200,40);
    	 	        displayDish.add(label_price);
    	 	        label_price.setBounds(80,350,200,40);
    	 	        
    	 	        l_foodNameC.setText(foodName);
    	 	        displayDish.add(l_foodNameC);
    	 	        l_foodNameC.setBounds(200,60,400,40);
    	 	        l_caixiC.setText(caixi);
    	 	        displayDish.add(l_caixiC);
    	 	        l_caixiC.setBounds(200,100,400,40);
    	 	        l_cookStyleC.setText(cookStyle);
    	 	        displayDish.add(l_cookStyleC);
    	 	        l_cookStyleC.setBounds(200,150,400,40);
    	 	        l_tasteC.setText(taste);
    	 	     	  displayDish.add(l_tasteC);
    	 	     	  l_tasteC.setBounds(200,200,400,40);
    	 	     	  l_materialC.setText(material);
    	 	        displayDish.add(l_materialC);
    	 	        l_materialC.setBounds(200,250,400,40);
    	 	        l_sesonC.setText(seson);
    	 	        displayDish.add(l_sesonC);
    	 	        l_sesonC.setBounds(200,300,400,40);
    	 	        l_priceC.setText(price);
    	 	        displayDish.add(l_priceC);
    	 	        l_priceC.setBounds(200,350,400,40);     	 	        

    	 	     	  
    	 	     	  
    	 	     	  }
    	 	     	
    	 	     	  catch(SQLException ee)
    	 	     	  {
    	 	     		   System.out.print(""+ee);
    	 	     	  }	 	  	 
    	 	     
    	 	     }	  
    	 	     
    	 	     else if(choose==4)
    	 	     {
    	 	     	  String foodName,caixi,cookStyle,taste,material,seson,price;
    	 	     	  
    	 	     	  try
    	 	        {
    	 	     	     Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	 	        }
    	 	        catch(ClassNotFoundException ek)
    	 	        {
    	 	     	    System.out.print(""+ek);
    	 	        }
    	 	     	  
    	 	     	  try
    	 	        {
    	 	     	     con=DriverManager.getConnection("jdbc:odbc:foodmenu","","");
    	 	     	     sql=con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
    	 	     	     rs=sql.executeQuery("SELECT  * FROM cookbook WHERE  ����='����' " );
    	 	     	 
    	 	     	     rs.next();
    	 	     	     
    	 	     	     
    	 	     	 	     foodName=rs.getString(1);
    	 	     	 	     caixi=rs.getString(3);
    	 	     	 	     cookStyle=rs.getString(4);
    	 	     	 	   taste=rs.getString(5);
    	 	     	 	     material=rs.getString(6);
    	 	     	 	     seson=rs.getString(7); 
    	 	     	 	    price=rs.getString(8);
    	 	     	 	   
    	 	     //	     con.close();
    	 	     	  
    	 	     	  //   System.out.println(foodName+caixi+cookStyle+taste+material+seson+price);
    	 	     	  
                  if(foodName.equals("����������")){
    	 	     	        
    	 	     	        displayDish.setNum(10,true);
    	 	     	        displayDish.repaint();
    	 	     	       
    	 	     	        System.out.println("����"+foodName);
    	 	     	    }   
                  
                  
                  
                  name.setText("");
    	 	     	     nameC.setText("");
                  danjia.setText("");
    	 	     	     danjiaC.setText("");
    	 	     	   
    	 	     	   label_foodName.setText("��       ��:");
    	 	     	  label_caixi.setText("��       ϵ:");
    	 	     	  label_cookStyle.setText("�������");
	             label_taste.setText("ζ       ��:");
	              label_material.setText("��       ��:");
	              label_seson.setText("���˼���:");
	              label_price.setText("��       ��:");
    	 	     	     
                    displayDish.add(label_foodName);
    	 	        label_foodName.setBounds(80,60,200,40);
    	 	        displayDish.add(label_caixi);
    	 	        label_caixi.setBounds(80,100,200,40);
    	 	        displayDish.add(label_cookStyle);
    	 	        label_cookStyle.setBounds(80,150,200,40);
    	 	        displayDish.add(label_taste);
    	 	        label_taste.setBounds(80,200,200,40);
    	 	        displayDish.add(label_material);
    	 	        label_material.setBounds(80,250,200,40);
    	 	        displayDish.add(label_seson);
    	 	        label_seson.setBounds(80,300,200,40);
    	 	        displayDish.add(label_price);
    	 	        label_price.setBounds(80,350,200,40);
    	 	        
    	 	        l_foodNameC.setText(foodName);
    	 	        displayDish.add(l_foodNameC);
    	 	        l_foodNameC.setBounds(200,60,400,40);
    	 	        l_caixiC.setText(caixi);
    	 	        displayDish.add(l_caixiC);
    	 	        l_caixiC.setBounds(200,100,400,40);
    	 	        l_cookStyleC.setText(cookStyle);
    	 	        displayDish.add(l_cookStyleC);
    	 	        l_cookStyleC.setBounds(200,150,400,40);
    	 	        l_tasteC.setText(taste);
    	 	     	  displayDish.add(l_tasteC);
    	 	     	  l_tasteC.setBounds(200,200,400,40);
    	 	     	  l_materialC.setText(material);
    	 	        displayDish.add(l_materialC);
    	 	        l_materialC.setBounds(200,250,400,40);
    	 	        l_sesonC.setText(seson);
    	 	        displayDish.add(l_sesonC);
    	 	        l_sesonC.setBounds(200,300,400,40);
    	 	        l_priceC.setText(price);
    	 	        displayDish.add(l_priceC);
    	 	        l_priceC.setBounds(200,350,400,40);    	 	     	  
    	 	     	  
    	 	     	  
    	 	     	  }
    	 	     	
    	 	     	  catch(SQLException ee)
    	 	     	  {
    	 	     		   System.out.print(""+ee);
    	 	     	  }	 	  	 
    	 	     
    	 	     }	  
    	 	     
    	       else if(choose==5)
    	 	     {
    	 	     	  String foodName,caixi,cookStyle,taste,material,seson,price;
    	 	     	  
    	 	     	  try
    	 	        {
    	 	     	     Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	 	        }
    	 	        catch(ClassNotFoundException ek)
    	 	        {
    	 	     	    System.out.print(""+ek);
    	 	        }
    	 	     	  
    	 	     	  try
    	 	        {
    	 	     	     con=DriverManager.getConnection("jdbc:odbc:foodmenu","","");
    	 	     	     sql=con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
    	 	     	     rs=sql.executeQuery("SELECT  * FROM cookbook WHERE  ����='С��' " );
    	 	     	 
    	 	     	     rs.next();
    	 	     	     
    	 	     	 	     foodName=rs.getString(1);
    	 	     	 	     caixi=rs.getString(3);
    	 	     	 	     cookStyle=rs.getString(4);
    	 	     	 	     taste=rs.getString(5);
    	 	     	 	    material=rs.getString(6);
    	 	     	 	     seson=rs.getString(7); 
    	 	     	 	    price=rs.getString(8);
    	 	     	 	   
    	 	     //	     con.close();
    	 	     	  
    	 	     	  //   System.out.println(foodName+caixi+cookStyle+taste+material+seson+price);
    	 	     	  
    	 	     	    if(foodName.equals("����Ҭ����")){
    	 	     	        
    	 	     	        displayDish.setNum(12,true);
    	 	     	        displayDish.repaint();
    	 	     	       
    	 	     	        System.out.println("����"+foodName);
    	 	     	    }   
    	 	     	  
    	 	     	  
    	 	     	  name.setText("");
    	 	     	     nameC.setText("");
    	 	     	  danjia.setText("");
    	 	     	     danjiaC.setText("");
    	 	     	   
    	 	     	   label_foodName.setText("��       ��:");
    	 	     	  label_caixi.setText("��       ϵ:");
    	 	     	  label_cookStyle.setText("�������");
	             label_taste.setText("ζ       ��:");
	              label_material.setText("��       ��:");
	              label_seson.setText("���˼���:");
	              label_price.setText("��       ��:");
    	 	     	     
    	 	     	      displayDish.add(label_foodName);
    	 	        label_foodName.setBounds(80,60,200,40);
    	 	        displayDish.add(label_caixi);
    	 	        label_caixi.setBounds(80,100,200,40);
    	 	        displayDish.add(label_cookStyle);
    	 	        label_cookStyle.setBounds(80,150,200,40);
    	 	        displayDish.add(label_taste);
    	 	        label_taste.setBounds(80,200,200,40);
    	 	        displayDish.add(label_material);
    	 	        label_material.setBounds(80,250,200,40);
    	 	        displayDish.add(label_seson);
    	 	        label_seson.setBounds(80,300,200,40);
    	 	        displayDish.add(label_price);
    	 	        label_price.setBounds(80,350,200,40);
    	 	        
    	 	        l_foodNameC.setText(foodName);
    	 	        displayDish.add(l_foodNameC);
    	 	        l_foodNameC.setBounds(200,60,400,40);
    	 	        l_caixiC.setText(caixi);
    	 	        displayDish.add(l_caixiC);
    	 	        l_caixiC.setBounds(200,100,400,40);
    	 	        l_cookStyleC.setText(cookStyle);
    	 	        displayDish.add(l_cookStyleC);
    	 	        l_cookStyleC.setBounds(200,150,400,40);
    	 	        l_tasteC.setText(taste);
    	 	     	  displayDish.add(l_tasteC);
    	 	     	  l_tasteC.setBounds(200,200,400,40);
    	 	     	  l_materialC.setText(material);
    	 	        displayDish.add(l_materialC);
    	 	        l_materialC.setBounds(200,250,400,40);
    	 	        l_sesonC.setText(seson);
    	 	        displayDish.add(l_sesonC);
    	 	        l_sesonC.setBounds(200,300,400,40);
    	 	        l_priceC.setText(price);
    	 	        displayDish.add(l_priceC);
    	 	        l_priceC.setBounds(200,350,400,40);
    	 	     	  
    	 	     	  }
    	 	     	
    	 	     	  catch(SQLException ee)
    	 	     	  {
    	 	     		   System.out.print(""+ee);
    	 	     	  }	 	  	 
    	 	     
    	 	     }	  
    	    
    	       else if(choose==6)
    	 	     {
    	 	     	  
    	 	     	  
    	 	     	  String foodName,caixi,cookStyle,taste,material,seson,price;
    	 	     	  
    	 	     	  try
    	 	        {
    	 	     	     Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	 	        }
    	 	        catch(ClassNotFoundException ek)
    	 	        {
    	 	     	    System.out.print(""+ek);
    	 	        }
    	 	     	  
    	 	     	  try
    	 	        {
    	 	     	     con=DriverManager.getConnection("jdbc:odbc:foodmenu","","");
    	 	     	     sql=con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
    	 	     	     rs=sql.executeQuery("SELECT  * FROM cookbook WHERE  ����='��ˮ' " );
    	 	     	 
    	 	     	     rs.next();
    	 	     	     
    	 	     	 	     foodName=rs.getString(1);
    	 	     	 	     caixi=rs.getString(3);
    	 	     	 	    cookStyle=rs.getString(4);
    	 	     	 	  taste=rs.getString(5);
    	 	     	 	    material=rs.getString(6);
    	 	     	 	   seson=rs.getString(7); 
    	 	     	 	   price=rs.getString(8);
    	 	     	 	   
    	 	     //	     con.close();
    	 	     	  
    	 	     	//     System.out.println(foodName+caixi+cookStyle+taste+material+seson+price);
    	 	     	  
    	 	     	  
    	 	     	  displayDish.setNum(9,false);
    	 	     	  displayDish.repaint();
    	 	     	  
    	 	     	  label_foodName.setText("");
    	 	     	  label_caixi.setText("");
    	 	     	  label_cookStyle.setText("");
	             label_taste.setText("");
	              label_material.setText("");
	              label_seson.setText("");
	              label_price.setText("");
	              l_foodNameC.setText("");
	              l_caixiC.setText("");
	              l_cookStyleC.setText("");
	             l_tasteC.setText("");
	              l_materialC.setText("");
	              l_sesonC.setText("");
	              l_priceC.setText("");
	              
	   //         name=new JLabel("��       ��:");
      //          name.setFont(font);
      //         nameC=new JLabel("");
      //          nameC.setFont(font);
       //         danjia=new JLabel("��       ��:");
      //          danjia.setFont(font);
       //          danjiaC=new JLabel("");
      //           danjiaC.setFont(font);   	 	     	  
    	 	     	  
    	 	     	  name.setText("��       ��:");
    	 	     	  danjia.setText("��       ��:");
    	 	     	  
    	 	     	  displayDish.add(name);
    	 	        name.setBounds(80,125,200,40);
    	 	   
    	 	        displayDish.add(danjia);
    	 	        danjia.setBounds(80,175,200,40);
    	 	        
    	 	        nameC.setText(foodName);
    	 	        displayDish.add(nameC);
    	 	        nameC.setBounds(200,125,400,40);
    	 	    
    	 	   
    	 	        danjiaC.setText(price);
    	 	        displayDish.add(danjiaC);
    	 	        danjiaC.setBounds(200,175,400,40);
    	 	     	  
    	 	     	  
    	 	     	  
    	 	     	  
    	 	     	  }
    	 	     	
    	 	     	  catch(SQLException ee)
    	 	     	  {
    	 	     		   System.out.print(""+ee);
    	 	     	  }	 	  	 
    	 	     
    	 	     }	  
    	    
    	    
    	    }
    	 	
    	 	}
    	 	
    	 	
    	 		  	 
    }	
    
    public void actionPerformed(ActionEvent ae)
    {
    	  int drawNum=20;
    	  
    	  //����㼤 next��ť �¼�
    	  if(ae.getSource()==nextDish)
    	  {
    	  	String foodName,caixi,cookStyle,taste,material,seson,price;
    	  	
    	    try
    	 	  {
    	       rs.next();
    	 	     	     
    	 	     foodName=rs.getString(1);
    	 	     caixi=rs.getString(3);
    	 	     cookStyle=rs.getString(4);
    	 	     taste=rs.getString(5);
    	 	     material=rs.getString(6);
    	 	     seson=rs.getString(7); 
    	 	     price=rs.getString(8);
    	 	     int aa=Integer.parseInt(price); 	   
    	       
    	       if(foodName.equals("��������"))          drawNum=1;
    	 	     else if(foodName.equals("������"))     drawNum=2;
    	 	     else if(foodName.equals("����������"))     drawNum=3;
    	 	     else if(foodName.equals("С������ţ��"))     drawNum=4;
    	 	     else if(foodName.equals("Ҭ�Ѻ���"))     drawNum=5;
    	 	     else if(foodName.equals("ܽ������"))     drawNum=6;
    	 	     else if(foodName.equals("�Ǵ׹�����"))     drawNum=7;
    	 	     else if(foodName.equals("��֦Ϻ��"))     drawNum=8;
    	 	     else if(foodName.equals("�����ܲ���"))     drawNum=9;
    	 	     else if(foodName.equals("�����ܲ���"))     drawNum=10;
    	 	     
    	 	     switch(drawNum){
    	 	     	 case 1: {
    	 	     	 	  
    	 	     	        displayDish.setNum(1,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	       case 2: {
    	 	     	 	  
    	 	     	        displayDish.setNum(2,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 3: {
    	 	     	 	  
    	 	     	        displayDish.setNum(3,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 4: {
    	 	     	 	  
    	 	     	        displayDish.setNum(4,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 5: {
    	 	     	 	  
    	 	     	        displayDish.setNum(5,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 6: {
    	 	     	 	  
    	 	     	        displayDish.setNum(6,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 7: {
    	 	     	 	  
    	 	     	        displayDish.setNum(7,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 8: {
    	 	     	 	  
    	 	     	        displayDish.setNum(8,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 9: {
    	 	     	 	  
    	 	     	        displayDish.setNum(11,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 
    	 	     	 case 10: {
    	 	     	 	  
    	 	     	        displayDish.setNum(13,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	       default :{
    	 	       	
    	 	       	      displayDish.setNum(11,false);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("û��ͼƬ��");
    	 	     	 }       
    	 	     
    	 	     
    	 	    }
    	 	     
    	 	     
    	 	     	        
    	 	        
    	       
    	       
    	       
    	     
    	     
    	       if(aa==5 || aa==8 || aa==10 || aa==15 || aa==20 || aa==25 ||aa==30)
    	        { 
    	         name.setText("");
    	 	     	     nameC.setText("");
    	 	     	  danjia.setText("");
    	 	     	     danjiaC.setText("");
    	 	     	   
    	 	     	   label_foodName.setText("��       ��:");
    	 	     	  label_caixi.setText("��       ϵ:");
    	 	     	  label_cookStyle.setText("�������");
	             label_taste.setText("ζ       ��:");
	              label_material.setText("��       ��:");
	              label_seson.setText("���˼���:");
	              label_price.setText("��       ��:");
    	 	     	     
    	 	     	      displayDish.add(label_foodName);
    	 	        label_foodName.setBounds(80,60,200,40);
    	 	        displayDish.add(label_caixi);
    	 	        label_caixi.setBounds(80,100,200,40);
    	 	        displayDish.add(label_cookStyle);
    	 	        label_cookStyle.setBounds(80,150,200,40);
    	 	        displayDish.add(label_taste);
    	 	        label_taste.setBounds(80,200,200,40);
    	 	        displayDish.add(label_material);
    	 	        label_material.setBounds(80,250,200,40);
    	 	        displayDish.add(label_seson);
    	 	        label_seson.setBounds(80,300,200,40);
    	 	        displayDish.add(label_price);
    	 	        label_price.setBounds(80,350,200,40);
    	 	        
    	 	        l_foodNameC.setText(foodName);
    	 	        displayDish.add(l_foodNameC);
    	 	        l_foodNameC.setBounds(200,60,400,40);
    	 	        l_caixiC.setText(caixi);
    	 	        displayDish.add(l_caixiC);
    	 	        l_caixiC.setBounds(200,100,400,40);
    	 	        l_cookStyleC.setText(cookStyle);
    	 	        displayDish.add(l_cookStyleC);
    	 	        l_cookStyleC.setBounds(200,150,400,40);
    	 	        l_tasteC.setText(taste);
    	 	     	  displayDish.add(l_tasteC);
    	 	     	  l_tasteC.setBounds(200,200,400,40);
    	 	     	  l_materialC.setText(material);
    	 	        displayDish.add(l_materialC);
    	 	        l_materialC.setBounds(200,250,400,40);
    	 	        l_sesonC.setText(seson);
    	 	        displayDish.add(l_sesonC);
    	 	        l_sesonC.setBounds(200,300,400,40);
    	 	        l_priceC.setText(price);
    	 	        displayDish.add(l_priceC);
    	 	        l_priceC.setBounds(200,350,400,40);
    	         }
    	         
    	         else if(aa>100 || aa==4 ||aa==6)
    	         {
    	         	 
    	         	
    	         	  label_foodName.setText("");
    	 	     	  label_caixi.setText("");
    	 	     	  label_cookStyle.setText("");
	             label_taste.setText("");
	              label_material.setText("");
	              label_seson.setText("");
	              label_price.setText("");
	              l_foodNameC.setText("");
	              l_caixiC.setText("");
	              l_cookStyleC.setText("");
	             l_tasteC.setText("");
	              l_materialC.setText("");
	              l_sesonC.setText("");
	              l_priceC.setText("");
    	         	
    	         	name.setText("��       ��:");
    	 	     	  danjia.setText("��       ��:");
    	 	     	  
    	 	     	  displayDish.add(name);
    	 	        name.setBounds(80,125,200,40);
    	 	   
    	 	        displayDish.add(danjia);
    	 	        danjia.setBounds(80,175,200,40);
    	 	        
    	 	        nameC.setText(foodName);
    	 	        displayDish.add(nameC);
    	 	        nameC.setBounds(200,125,400,40);
    	 	    
    	 	   
    	 	        danjiaC.setText(price);
    	 	        displayDish.add(danjiaC);
    	 	        danjiaC.setBounds(200,175,400,40);
    	 	     	  
    	 	     	  
    	         	
    	         }
    	        
    	       }
    	 	     	
    	 	     catch(SQLException ee)
    	 	     {
    	 	     	  System.out.print(""+ee);
    	 	     }	 	  	
    	       	
    	  }
    	  
    	  
    	  
    	  //����㼤 previous��ťʵ��
    	  else if(ae.getSource()==previousDish){
    	  	
    	  	drawNum=20;
    	  	
    	  	String foodName,caixi,cookStyle,taste,material,seson,price;
    	  	
    	    try
    	 	  {
    	       rs.previous();
    	 	     	     
    	 	     foodName=rs.getString(1);
    	 	     caixi=rs.getString(3);
    	 	     cookStyle=rs.getString(4);
    	 	     taste=rs.getString(5);
    	 	     material=rs.getString(6);
    	 	     seson=rs.getString(7); 
    	 	     price=rs.getString(8);
    	 	     int aa=Integer.parseInt(price); 	   
    	       
    	        
    	       if(foodName.equals("��������"))            drawNum=0;
    	       else if(foodName.equals("��������"))       drawNum=1;
    	 	     else if(foodName.equals("������"))         drawNum=2;
    	 	     else if(foodName.equals("����������"))     drawNum=3;
    	 	     else if(foodName.equals("С������ţ��"))   drawNum=4;
    	 	     else if(foodName.equals("Ҭ�Ѻ���"))       drawNum=5;
    	 	     else if(foodName.equals("ܽ������"))       drawNum=6;
    	 	     else if(foodName.equals("�Ǵ׹�����"))     drawNum=7;
    	 	     else if(foodName.equals("��֦Ϻ��"))       drawNum=8;
    	 	     else if(foodName.equals("�����ܲ���"))     drawNum=9;
    	 	     else if(foodName.equals("�����ܲ���"))     drawNum=10;
    	 	     else if(foodName.equals("��˿����"))       drawNum=11;
    	 	     else if(foodName.equals("����������"))     drawNum=12;
    	 	     else if(foodName.equals("����Ҭ����"))     drawNum=13;
    	 	     
    	 	     switch(drawNum){
    	 	     	 
    	 	     	 case 0: {
    	 	     	 	  
    	 	     	        displayDish.setNum(0,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 
    	 	     	 case 1: {
    	 	     	 	  
    	 	     	        displayDish.setNum(1,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	       case 2: {
    	 	     	 	  
    	 	     	        displayDish.setNum(2,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 3: {
    	 	     	 	  
    	 	     	        displayDish.setNum(3,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 4: {
    	 	     	 	  
    	 	     	        displayDish.setNum(4,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 5: {
    	 	     	 	  
    	 	     	        displayDish.setNum(5,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 6: {
    	 	     	 	  
    	 	     	        displayDish.setNum(6,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 7: {
    	 	     	 	  
    	 	     	        displayDish.setNum(7,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 8: {
    	 	     	 	  
    	 	     	        displayDish.setNum(8,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 9: {
    	 	     	 	  
    	 	     	        displayDish.setNum(11,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 
    	 	     	 case 10: {
    	 	     	 	  
    	 	     	        displayDish.setNum(13,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	       case 11: {
    	 	     	 	  
    	 	     	        displayDish.setNum(9,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	       
    	 	       case 12: {
    	 	     	 	  
    	 	     	        displayDish.setNum(10,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 
    	 	     	 case 13: {
    	 	     	 	  
    	 	     	        displayDish.setNum(12,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("��"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	       default :{
    	 	       	
    	 	       	      displayDish.setNum(11,false);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("û��ͼƬ��");
    	 	     	 }       
    	 	     
    	 	     
    	 	    }
    	 	     
    	 	     
    	        
    	        
    	        
    	        if(aa==5 || aa==8 || aa==10 || aa==15 || aa==20 || aa==25 ||aa==30)
    	        { 
    	         name.setText("");
    	 	     	     nameC.setText("");
    	 	     	  danjia.setText("");
    	 	     	     danjiaC.setText("");
    	 	     	   
    	 	     	   label_foodName.setText("��       ��:");
    	 	     	  label_caixi.setText("��       ϵ:");
    	 	     	  label_cookStyle.setText("�������");
	             label_taste.setText("ζ       ��:");
	              label_material.setText("��       ��:");
	              label_seson.setText("���˼���:");
	              label_price.setText("��       ��:");
    	 	     	     
    	 	     	      displayDish.add(label_foodName);
    	 	        label_foodName.setBounds(80,60,200,40);
    	 	        displayDish.add(label_caixi);
    	 	        label_caixi.setBounds(80,100,200,40);
    	 	        displayDish.add(label_cookStyle);
    	 	        label_cookStyle.setBounds(80,150,200,40);
    	 	        displayDish.add(label_taste);
    	 	        label_taste.setBounds(80,200,200,40);
    	 	        displayDish.add(label_material);
    	 	        label_material.setBounds(80,250,200,40);
    	 	        displayDish.add(label_seson);
    	 	        label_seson.setBounds(80,300,200,40);
    	 	        displayDish.add(label_price);
    	 	        label_price.setBounds(80,350,200,40);
    	 	        
    	 	        l_foodNameC.setText(foodName);
    	 	        displayDish.add(l_foodNameC);
    	 	        l_foodNameC.setBounds(200,60,400,40);
    	 	        l_caixiC.setText(caixi);
    	 	        displayDish.add(l_caixiC);
    	 	        l_caixiC.setBounds(200,100,400,40);
    	 	        l_cookStyleC.setText(cookStyle);
    	 	        displayDish.add(l_cookStyleC);
    	 	        l_cookStyleC.setBounds(200,150,400,40);
    	 	        l_tasteC.setText(taste);
    	 	     	  displayDish.add(l_tasteC);
    	 	     	  l_tasteC.setBounds(200,200,400,40);
    	 	     	  l_materialC.setText(material);
    	 	        displayDish.add(l_materialC);
    	 	        l_materialC.setBounds(200,250,400,40);
    	 	        l_sesonC.setText(seson);
    	 	        displayDish.add(l_sesonC);
    	 	        l_sesonC.setBounds(200,300,400,40);
    	 	        l_priceC.setText(price);
    	 	        displayDish.add(l_priceC);
    	 	        l_priceC.setBounds(200,350,400,40);
    	         }
    	         
    	         else if(aa>100 || aa==4 ||aa==6)
    	         {
    	         	 
    	         	
    	         	  label_foodName.setText("");
    	 	     	  label_caixi.setText("");
    	 	     	  label_cookStyle.setText("");
	             label_taste.setText("");
	              label_material.setText("");
	              label_seson.setText("");
	              label_price.setText("");
	              l_foodNameC.setText("");
	              l_caixiC.setText("");
	              l_cookStyleC.setText("");
	             l_tasteC.setText("");
	              l_materialC.setText("");
	              l_sesonC.setText("");
	              l_priceC.setText("");
    	         	
    	         	name.setText("��       ��:");
    	 	     	  danjia.setText("��       ��:");
    	 	     	  
    	 	     	  displayDish.add(name);
    	 	        name.setBounds(80,125,200,40);
    	 	   
    	 	        displayDish.add(danjia);
    	 	        danjia.setBounds(80,175,200,40);
    	 	        
    	 	        nameC.setText(foodName);
    	 	        displayDish.add(nameC);
    	 	        nameC.setBounds(200,125,400,40);
    	 	    
    	 	   
    	 	        danjiaC.setText(price);
    	 	        displayDish.add(danjiaC);
    	 	        danjiaC.setBounds(200,175,400,40);
    	 	     	  
    	 	     	  
    	         	
    	         }
    	        
    	       }
    	 	     	
    	 	     catch(SQLException ee)
    	 	     {
    	 	     	  System.out.print(""+ee);
    	 	     }	 	  	
    	  	
    	  
    	 }
    	  
    	  
    	  //����㼤 ������� ��ť�¼�
    	  else if(ae.getSource()==confirmDish){
    	 		 
    	 		 if(choose<6){
    	 		  
    	 		    int n=JOptionPane.showConfirmDialog(this,"��ȷ��Ҫ���������?","ȷ�϶Ի���",
    	 		                                        JOptionPane.YES_NO_OPTION);
    	 		    
    	 		    if(n==JOptionPane.YES_OPTION){                                    
    	 		    
    //	 		              dishNum++;
   //	 		                allName[allNameNum]=l_foodNameC.getText();
    //	 		              allNameNum++;
   //	 		                allPriceNum++;
    	 		      
    	 		      confirmedDishLink.add(l_foodNameC.getText());
    	 		      confirmedPriceLink.add(l_priceC.getText());
    	 		    
    	// 		      System.out.println( (String)confirmedDishLink.get(0));
    	 		    
    	 		    }
    	 		    
    	 		 
    	 		 }
    	 		 else{
    	 		 	
    	 		 	  int l=JOptionPane.showConfirmDialog(this,"��ȷ��Ҫ������?","ȷ�϶Ի���",
    	 		                                        JOptionPane.YES_NO_OPTION);
    	 		 	  
    	 		 	  if(l==JOptionPane.YES_OPTION){ 
    	 		 	     
    	// 		 	            drinkNum++;
    	// 		 	            allName[allNameNum]=nameC.getText();
    	// 		              allNameNum++;
    	// 		              allPriceNum++; 
    	 		     confirmedDishLink.add(nameC.getText());
    	 		     confirmedPriceLink.add(danjiaC.getText());
    	 		    
    	 		 	  
    	 		 	  
    	 		 	  
    	 		 	  }
    	 		 }	
    	 		
    	  }	
    	  
    	  
    	  //����㼤 �鿴�ѵ�� ��ť�¼� 
    	  else if(ae.getSource()==checkConfirmedDish){
    	  	
  //  	       detail.setText("����Ĳ���:");
  //  	       for(int i=0;i<dishNum;i++){
  //  	       	   detail.append(allDishes[i]+"      ");
  //  	       }	  	
  //  	  	   for(int i=0;i<drinkNum;i++){
  //	  	   	    detail.append(allDrink[i]+"      ");
  //  	  	   }	  
    	  	
    	  	 checkConfirmedDishFrame.setVisible(true);
    	  	 
    	  	  
    	     if(checkConfirmedDishFrame.checkIsChanged()){
    	  	 	  confirmedDishIsChaneged=true;
    	  	 }
    	     
    	 
    	     if(!confirmedDishIsChaneged){
    	          checkConfirmedDishFrame.set(confirmedDishLink,confirmedPriceLink);
    	  	 }
    	  	 else{
    	  	 	  confirmedDishLink=checkConfirmedDishFrame.returnName();
    	  	    confirmedPriceLink=checkConfirmedDishFrame.returnPrice();
    	  	    
    	  	    checkConfirmedDishFrame.set(confirmedDishLink,confirmedPriceLink);
    	       
    	        System.out.println("KKKKKKKK");
    	     }
    	     
    	     
    	     
    	 }
    	 
    	 
    	 //���������
    	 else if(ae.getSource()==suggestion){
    	 	
    	 	    liveMess.setVisible(true);  
    	      liveMess.writeArea.setText("");
    	  
    	 } 
    
       
       //Ϊ����Ա���� 
       else if(ae.getSource()==servicerButton){
        	
        	 grade.setVisible(true);
    
      }
    
       
       
    
    
    
      //�����Ļ�����
       else if(ae.getSource()==cultureIntro){
       	  
       	   introduceFrame.setVisible(true);
       }	   
     
      //����Ա�����޸�����
      else if(ae.getSource()==updateData){
      	 
            Ensure ensure=new Ensure("����Աȷ�ϴ���",1);
          	
        
       }	    
    
     
      //����Ա���в�������
      else if(ae.getSource()==insertData){
           
          Ensure ensure=new Ensure("����Աȷ�ϴ���",2);  
          //  insterDataFrame.setVisible(true);
      }      
     
      //����Ա����ɾ������
      else if(ae.getSource()==deleteData){
       	
          Ensure ensure=new Ensure("����Աȷ�ϴ���",3);  
    //   	    deleteDataFrame.setVisible(true);
      
      } 	    
     
      
      //��
      else if(ae.getSource()==payBill){
      	
      	   String dish="���ι���Ĳ���:",writeIn;
      	   int sum=0;
      	   int dishNum=confirmedDishLink.size();
      	   int priceNum=confirmedPriceLink.size();
      	   FileWriter    fileWriter;
	         BufferedWriter  out;
      	   Calendar      calen;
      	   int   year,month,day,hour,minute,second;
      	   
      	   
      	   
      	   for(int i=0;i<dishNum;i++){
      	   	 dish=dish+(String)confirmedDishLink.get(i)+" ";
      	   }	 
          
           for(int i=0;i<priceNum;i++){
           	  sum=sum+Integer.parseInt((String)confirmedPriceLink.get(i));
           }	  
      
           
           calen=Calendar.getInstance();
           year=calen.get(Calendar.YEAR);
           month=calen.get(Calendar.MONTH)+1;
           day=calen.get(Calendar.DAY_OF_MONTH);
           hour=calen.get(Calendar.HOUR_OF_DAY);
           minute=calen.get(Calendar.MINUTE);
           second=calen.get(Calendar.SECOND);
           
           
           writeIn=dish+" ����:"+sum+"Ԫ"+"       "+year+"��"+month+"��"+day+"��"+hour+"ʱ"
                    +minute+"��"+second+"��"+"              ";
           
           
           try{
                File file=new File("D:\\","Bill"+billNum+".txt");
                fileWriter=new FileWriter(file);
                out=new BufferedWriter(fileWriter); 
      
                out.write(writeIn,0,writeIn.length() );
        
                out.flush();
                out.close();
                fileWriter.close();
                
                billNum++;
       
           }

           catch(IOException e2)
           {}
                
      }
      
      
      
      //��ѯ�û��Զ���Ĳ�
      else if(ae.getSource()==lookUpDish){
      	
      	 if( !interLookUp.getText().equals("")){
      	 
      	    Connection  con;
      	    Statement   sql;
  //    	    ResultSet   rs;
      	  
      	    try{
    	 	     	     Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	 	    }
    	 	    catch(ClassNotFoundException ek){
    	 	    
    	 	     	    System.out.print(""+ek);
    	 	    }
      	    
      	    try{
    	 	     	 
    	 	     	  con=DriverManager.getConnection("jdbc:odbc:foodmenu","","");
    	 	     	  sql=con.createStatement(); 
                
                String temp="'%"+interLookUp.getText()+"%'";
                
                
                rs=sql.executeQuery("SELECT * FROM cookbook WHERE ���� LIKE "+temp); 
            
      //          rs=sql.executeQuery("SELECT * FROM cookbook WHERE ���� LIKE '%��%'");
                
                System.out.println("KKKKKKK");
                
                String foodName,caixi,cookStyle,taste,material,seson;
                int    price;
            
                rs.next();
    	 	     	     
    	 	     	 	     foodName=rs.getString(1);
    	 	     	 	     caixi=rs.getString(3);
    	 	     	 	     cookStyle=rs.getString(4);
    	 	     	 	     taste=rs.getString(5);
    	 	     	 	    material=rs.getString(6);
    	 	     	 	     seson=rs.getString(7); 
    	 	     	 	    price=rs.getInt(8);
    	 	     	 	   
    	 	     
    	 	     	  
    	 	     	  
    	 	     	  name.setText("");
    	 	     	     nameC.setText("");
    	 	     	  danjia.setText("");
    	 	     	     danjiaC.setText("");
    	 	     	   
    	 	     	   label_foodName.setText("��       ��:");
    	 	     	  label_caixi.setText("��       ϵ:");
    	 	     	  label_cookStyle.setText("�������");
	             label_taste.setText("ζ       ��:");
	              label_material.setText("��       ��:");
	              label_seson.setText("���˼���:");
	              label_price.setText("��       ��:");
    	 	     	     
    	 	     	      displayDish.add(label_foodName);
    	 	        label_foodName.setBounds(80,60,200,40);
    	 	        displayDish.add(label_caixi);
    	 	        label_caixi.setBounds(80,100,200,40);
    	 	        displayDish.add(label_cookStyle);
    	 	        label_cookStyle.setBounds(80,150,200,40);
    	 	        displayDish.add(label_taste);
    	 	        label_taste.setBounds(80,200,200,40);
    	 	        displayDish.add(label_material);
    	 	        label_material.setBounds(80,250,200,40);
    	 	        displayDish.add(label_seson);
    	 	        label_seson.setBounds(80,300,200,40);
    	 	        displayDish.add(label_price);
    	 	        label_price.setBounds(80,350,200,40);
    	 	        
    	 	        l_foodNameC.setText(foodName);
    	 	        displayDish.add(l_foodNameC);
    	 	        l_foodNameC.setBounds(200,60,400,40);
    	 	        l_caixiC.setText(caixi);
    	 	        displayDish.add(l_caixiC);
    	 	        l_caixiC.setBounds(200,100,400,40);
    	 	        l_cookStyleC.setText(cookStyle);
    	 	        displayDish.add(l_cookStyleC);
    	 	        l_cookStyleC.setBounds(200,150,400,40);
    	 	        l_tasteC.setText(taste);
    	 	     	  displayDish.add(l_tasteC);
    	 	     	  l_tasteC.setBounds(200,200,400,40);
    	 	     	  l_materialC.setText(material);
    	 	        displayDish.add(l_materialC);
    	 	        l_materialC.setBounds(200,250,400,40);
    	 	        l_sesonC.setText(seson);
    	 	        displayDish.add(l_sesonC);
    	 	        l_sesonC.setBounds(200,300,400,40);
    	 	        l_priceC.setText(""+price);
    	 	        displayDish.add(l_priceC);
    	 	        l_priceC.setBounds(200,350,400,40);
    	 	     	  
            } 
       
            catch(SQLException ee){
    	 	     		   
    	 	     		   System.out.print(""+ee);
    	 	    }	 	  	   
      
          }
      }	
       
        
      
      //����㼤 about ��ť�¼�
      else if(ae.getSource()==about){
        
          JOptionPane.showMessageDialog(this,"     ��Ȩû��,��Ȩ����. \n      Designer : chenluan",
                                        "��Ϣ�Ի���",JOptionPane.INFORMATION_MESSAGE);
    
    
      }  
    
    
    
    
    }	   	
    
    
    
    public void run(){
    	
 
    	
    	//��������߳�
    	    try { 
	 	  	
	          Thread.sleep(5000);
	 	      }
	 	      catch(Exception exx) {}
	 	  
	 	      setVisible(true);
	
    
    //ʱ���߳�
        if(Thread.currentThread()==timer){
       	
       	   while(true){
       	   	  
              Calendar      calen;
      	      int   year,month,day,hour,minute,second;
    
              calen=Calendar.getInstance();
              year=calen.get(Calendar.YEAR);
              month=calen.get(Calendar.MONTH)+1;
              day=calen.get(Calendar.DAY_OF_MONTH);
              hour=calen.get(Calendar.HOUR_OF_DAY);
              minute=calen.get(Calendar.MINUTE);
              second=calen.get(Calendar.SECOND);
              
              
              dateLabel.setText(year+"��"+month+"��"+day+"��");
              timeLabel.setText(hour+"ʱ"+minute+"��"+second+"��");
              
           
              try{
              	 
              	 timer.sleep(1000);
              }
              
              catch(InterruptedException  ins)
              {} 	  
           
           
           
           }   
       }       
    
    
    }
    
    public static void  main(String[] args)
    {
        bFrame=new BeginFrame();
        bFrame.start();
        
         
         MainFrame stef=new MainFrame("�������ϵͳ");
    	  Thread gogo=null;
        gogo=new Thread(stef);
    	  gogo.start();
    }
     	    
       
       
       
 }     
 
 
 
 
 
 //ճ����Ʒ��ͼƬ
 class DisplayDish extends JPanel
 {
	  Image []   ima=new Image[14];
    int        num=0;
	  Toolkit    tool;
 	 	boolean    paint=false;  
 	 	  
 	 	  DisplayDish(){

         tool=getToolkit();
         
         for( int i=0;i<14;i++){
       	  
      	  ima[i]=tool.getImage(i+".jpg");
 	  	   }
 	  
 	    }
 	  
 	  
 	  public void paintComponent(Graphics g)
 	  {
         super.paintComponent(g);
         
         if(paint){  
      
	  	      int x=350,y=60;
 	  	      g.drawImage(ima[num],x,y,ima[num].getWidth(this),ima[num].getHeight(this),this);

 	       }
 	  
         else{
         	
         	  int x=900,y=60;
 	  	      g.drawImage(ima[num],x,y,ima[num].getWidth(this),ima[num].getHeight(this),this);
        
            System.out.println("�ı�λ��");
        }
    
    }  
 	  
     

     void setNum(int n,boolean pain){
     	
     	   num=n; 
         paint=pain;
     }

     
}

  
  
  
  //�����������
  class BeginFrame extends JWindow implements Runnable {

	 Thread splashThread=null;
	 
	 BeginFrame(){
	 	 
	 	 setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
	 	 JPanel splash=new JPanel(new BorderLayout());
	 	 URL    url=getClass().getResource("good.jpg");
	 	 
	 	 if(url !=null){
	 	 	
	 	 	  splash.add(new JLabel( new ImageIcon(url) ) ,BorderLayout.CENTER );
	 	 }
	 	 
	 	 setContentPane(splash);
	 	 
	 	 Dimension  screen=getToolkit().getScreenSize();
	 	 pack();
	 	 setLocation( (screen.width-getSize().width)/2,(screen.height-getSize().height)/2);
	 	 
	    
	 
	 
	 }
	 
	 public void start (){
	 	
	 	  this.toFront();
	 	  splashThread=new Thread(this);
	 	  splashThread.start();
	 	  
	 }
	 
	 public void run(){
	 	
	 	  try {
	 	  	
	 	  	show();
	 	  	Thread.sleep(5000);
	 	  }
	 	  catch(Exception ex) {}
	 	  
	 	  this.dispose();
	 }
	 
}    	  	
	 	  


//���������	 	  
class LiveMess extends JFrame implements ActionListener{
	
	 JButton     ok;
	 JTextArea   writeArea;
	 Font       font=null;
	 int        messNum=1;
	 String     messContent;
	 FileWriter    fileWriter;
	 BufferedWriter  out;
	 Container   con;
	 JScrollPane scroll;
	 JPanel     jPanel;
	 JLabel     lab;
	 
	 
	 LiveMess(String mess){
	 	
	 	  super(mess);
	 	  
	 	  con=getContentPane();
	 	  font=new Font("Dialog",Font.PLAIN,20);
	 	  
	 	  ok=new JButton("�ύ");
	 	  ok.setFont(font);
	 	  ok.setBackground(new Color(104,122,225));
	 	  ok.setForeground(Color.red);
	 	  ok.addActionListener(this);
	 	  
	 	  writeArea=new JTextArea();
	 	  writeArea.setFont(font);
	 	  writeArea.setLineWrap(true);
	 	  
	 	  lab=new JLabel("�������±������",JLabel.CENTER);
	 	  lab.setFont(font);
	 	  jPanel=new JPanel();
	 	  jPanel.add(lab);
	 	  
	 	  scroll=new JScrollPane(writeArea);
	 	  
	 	  con.setLayout(null);
   	  con.add(jPanel);
   	  jPanel.setBounds(2,0,390,40);
   	  con.add(scroll);
   	  scroll.setBounds(2,40,390,200);
   	  con.add(ok);
   	  ok.setBounds(2,245,390,30);
   	  
   	  setVisible(false);
   	  setResizable(false);
   	  
   	  Dimension  screen=getToolkit().getScreenSize();
   	  pack();
   	  
   	  setBounds(  (screen.width-400)/2,(screen.height-310)/2,400,310);
   	  addWindowListener(new WindowAdapter()
                             {
                             	 public void windowClosing(WindowEvent e)
                             	 {
                             	 	  setVisible(false);
                             	 	}
                             	} );
                             	
       validate();
	 	  
	 }	
	
	 public void actionPerformed(ActionEvent e){
	 	
	 	   if(e.getSource()==ok){
	 	     
	 	       setVisible(false);
	 	   	
	 	   	   
           try{
           
                File file=new File("D:\\","messsge"+messNum+".txt");
                fileWriter=new FileWriter(file);
                out=new BufferedWriter(fileWriter);
	 	   	   
	 	   	        messContent=new String(writeArea.getText());
	 	   	   
	 	   	        out.write(messContent,0,messContent.length() );
        
                out.flush();
                out.close();
                fileWriter.close();
                
                messNum++;
       
           }

           catch(IOException e2)
           {}
	 	   	   
	 	   	   
	 	   	
	 	   }
	 	   	
	 	   
	 }
   


}	  

	 	 	  	   	  
//�ѵ�˵Ĳ鿴����
class  CheckConfirmedDishFrame extends JFrame implements ActionListener {
	
	Container     con=null;
	JLabel        lab,titleJLabel,allPrice;
	JButton       delete;
	JPanel        titlePanel,detailPanel;
	JTable        table;
  JScrollPane   scroll;
	JTextField    interNum;
	Object[][]    obj;
	Object[]      ziduan={"���","����","����"};
	Font          fon1,fon2;
	int           a, interNumber=1;
	boolean       isChanged=false; 
  LinkedList    name,price;
	
	
	
	CheckConfirmedDishFrame(String s) {
		 
		  super(s);
		 
		 fon1=new Font("Dialog",Font.PLAIN,18);
		 fon2=new Font("Dialog",Font.PLAIN,14);
		 
		  setVisible(false);
		  con=getContentPane();
		  con.setLayout(null);
		  
		  titleJLabel=new JLabel("����Ĳ���",JLabel.CENTER);
		  titleJLabel.setFont(fon1);
		  
		  lab=new JLabel("ɾ�������");
		  lab.setFont(fon2);
		  
		  allPrice=new JLabel("����");
		  allPrice.setFont(fon1);
		  
		  delete=new JButton("ɾ��");
		  delete.addActionListener(this);
		  delete.setFont(fon2);
		  
		  interNum=new JTextField(12);
//		  interNum.setFont(fon1);
		  
		  titlePanel=new JPanel();
		  titlePanel.setLayout(new BorderLayout());
		  detailPanel=new JPanel();
		  detailPanel.setLayout(null);
		  
	//	  ziduan={"����","����"};
		  
		  obj=new Object[20][3];
		  table=new JTable(obj,ziduan);
		  table.setGridColor( Color.blue);
		  table.setSelectionBackground(Color.red);
		  table.setSelectionForeground(Color.yellow);
		  
//		  int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
//		  int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
//		  scroll=new JScrollPane(table,v,h);
	     
	     scroll=new JScrollPane(table);
	
		  
	    titlePanel.add(titleJLabel,BorderLayout.CENTER);
	    
	    detailPanel.add(allPrice);
	    allPrice.setBounds(30,10,120,25);
	    detailPanel.add(lab);
	    lab.setBounds(200,10,80,25);
	    detailPanel.add(interNum);
	    interNum.setBounds(273,10,30,25);
	    detailPanel.add(delete);
	    delete.setBounds(315,10,75,25);
	    
	    con.add(titlePanel);
	    titlePanel.setBounds(0,0,400,40);
	    con.add(scroll);
	    scroll.setBounds(0,41,400,200);
	    con.add(detailPanel);
	    detailPanel.setBounds(0,242,400,58);
	    
	    
	    
	    
	    Dimension  screen=getToolkit().getScreenSize();
   	  
   	  setResizable(false);
   	  setBounds(  (screen.width-420)/2,(screen.height-320)/2,420,320);
   	  addWindowListener(new WindowAdapter()
                             {
                             	 public void windowClosing(WindowEvent e)
                             	 {
                             	 	  
                             	 	  setVisible(false);
                             	 	}
                             	} );
                             	
       validate();
	 
	 
	 }	  
		 	 	    	  	 

   public void set(LinkedList name,LinkedList price) {
   	
   	   remove(scroll);
   	   
   	   
   	   this.name=name;
   	   this.price=price;
   	   
   	   int n=0,sumPrice=0,number=1;
   	   
   	   Object  objw[][];
   	   
   	   a=name.size(); 
   	   System.out.println("mmm"+a);
   	   
   	   objw=new Object[a][3];
		   
		   
		   for(int i=0;i<a;i++){
		   	 for(int j=0;j<3;j++){
		   	    if(j==0)      objw[i][j]=""+number; 
		   	    else if(j==1) objw[i][j]=(String)name.get(i); 
		   	    else if(j==2) objw[i][j]=(String)price.get(i);	  
		   	 }
		   	 
		     number++;
		     
		   }	  	   
		   System.out.print(number-1+"��ѭ����");
		   
		   table=new JTable(objw,ziduan);
		   
		   scroll=new JScrollPane(table);
		   
	     
       validate();
   	   
   	   for(int i=0;i<a;i++){
		  	   sumPrice=sumPrice+Integer.parseInt((String)price.get(i)) ;
		   }	  
		   
	    allPrice.setText("����:"+sumPrice+"Ԫ");
   
   
      con.add(scroll);
	    scroll.setBounds(0,41,400,200);
   
   
   }

   public void actionPerformed(ActionEvent aa) {
    	
        
        //�ж���Ҫɾ���Ĳ˵�����Ƿ���ȷ
        
        int dele=Integer.parseInt(interNum.getText());
        name.remove(dele-1);
        price.remove(dele-1);
        
        int n=name.size(),number=1,p=0,sumPrice=0;
    	  
    	  JTable tableNew;
    	  JScrollPane scrollNew;
    	  
    	  
    	  obj=new Object[n][3];
    	
        this.remove(scroll); 
        
        
        
        
         for(int i=0;i<n;i++){
		   	     for(int j=0;j<3;j++){
		   	        if(j==0)      obj[i][j]=""+number;
		   	        else if(j==1) obj[i][j]=(String)name.get(p);
		   	        else if(j==2) obj[i][j]=(String)price.get(p);	  
		   	 }
		   	 p++;
		     number++;
		   }	  	   
   
   
   
        for(int i=0;i<n;i++){
		  	   
		  	   sumPrice=sumPrice+Integer.parseInt((String)price.get(i)) ;
		    }	  
		   
		   allPrice.setText("����:"+sumPrice+"Ԫ");
		   
		   tableNew=new JTable(obj,ziduan);
       
       scrollNew=new JScrollPane(tableNew);
       con.add(scrollNew);
       scrollNew.setBounds(420,41,400,200);
       
       
       validate();
       
       
        
       interNum.setText("");
    
       isChanged=true;
   
   } 

   
   boolean checkIsChanged(){
   	 return isChanged;
   }	 

   public LinkedList returnName(){
   	 return  name;
   }
   
   public LinkedList returnPrice(){
   	return  price;
   }		 

}



//��������Ա����
class Grade extends JFrame implements ActionListener{
	 
	  JPanel        topPanel,midPanel,bottPanel;
    JLabel        title,person,name,photo,attitudeLabel,qualityLabel,satisfyLabel; 
    CheckboxGroup attitude,quality,satisfy;
    Checkbox      attitude1,attitude2,attitude3,quality1,quality2,quality3,
                  satisfy1,satisfy2,satisfy3;
    JButton       ok,cancel;
    Container     con;
    int           gradeNum=1;
    FileWriter    fileWriter;
	  BufferedWriter  out;
    
    Grade(String ss){
    	
    	super(ss);
    	
    	setVisible(false);
    	
    	title=new JLabel("Ϊ������Ա����",JLabel.CENTER);
    	person=new JLabel("������Ա:",JLabel.LEFT);
    	name=new JLabel("������",JLabel.LEFT);
    	
    	URL  url=getClass().getResource("stef.jpg");
    	photo=new JLabel(new ImageIcon(url));
    	attitudeLabel=new JLabel("����̬��:",JLabel.LEFT);
    	qualityLabel=new JLabel("��������:",JLabel.LEFT);
    	satisfyLabel=new JLabel("����̶�:",JLabel.LEFT);
    	               
      attitude=new CheckboxGroup();
      quality=new CheckboxGroup();
      satisfy=new CheckboxGroup();
      
      attitude1=new Checkbox("����",true,attitude);
      attitude2=new Checkbox("һ��",false,attitude);
      attitude3=new Checkbox("�ܲ�",false,attitude);
      quality1=new Checkbox("�ϳ�",true,quality);
      quality2=new Checkbox("һ��",false,quality);
      quality3=new Checkbox("�ܲ�",false,quality);
      satisfy1=new Checkbox("����",true,satisfy);
      satisfy2=new Checkbox("һ��",false,satisfy);
      satisfy3=new Checkbox("�ܲ�",false,satisfy);
      
      ok=new JButton("�ύ");
      ok.addActionListener(this);
      cancel=new JButton("ȡ��");
      cancel.addActionListener(this);
      
      topPanel=new JPanel();
      topPanel.add(title);
      
      midPanel=new JPanel();
      midPanel.setLayout(null);
      midPanel.add(person);
      person.setBounds(10,12,80,25);
      midPanel.add(name);
      name.setBounds(95,12,70,25);
      midPanel.add(photo);
      photo.setBounds(175,10,40,40);
      midPanel.add(attitudeLabel);
      attitudeLabel.setBounds(10,70,80,25);
      midPanel.add(attitude1);
      attitude1.setBounds(95,70,70,25);
      midPanel.add(attitude2);
      attitude2.setBounds(175,70,70,25);
      midPanel.add(attitude3);
      attitude3.setBounds(255,70,70,25);
      midPanel.add(qualityLabel);
      qualityLabel.setBounds(10,105,80,25);
      midPanel.add(quality1);
      quality1.setBounds(95,105,70,25);
      midPanel.add(quality2);
      quality2.setBounds(175,105,70,25);
      midPanel.add(quality3);
      quality3.setBounds(255,105,70,25);
      midPanel.add(satisfyLabel);
      satisfyLabel.setBounds(10,140,80,25);
      midPanel.add(satisfy1);      
      satisfy1.setBounds(95,140,70,25);
      midPanel.add(satisfy2);
      satisfy2.setBounds(175,140,70,25);
      midPanel.add(satisfy3);
      satisfy3.setBounds(255,140,70,25);
      
      bottPanel=new JPanel();
      bottPanel.setLayout(null);
      bottPanel.add(ok);
      ok.setBounds(80,10,60,20);
      bottPanel.add(cancel);
      cancel.setBounds(190,10,60,20);
      
      con=getContentPane();
      con.setLayout(null);
      con.add(topPanel);
      topPanel.setBounds(0,0,310,30);
      con.add(midPanel);
      midPanel.setBounds(0,31,310,165);
      con.add(bottPanel);
      bottPanel.setBounds(0,200,310,40);
      
      
      Dimension  screen=getToolkit().getScreenSize();
   	  
   	  setResizable(false);
   	  setBounds(  (screen.width-310)/2,(screen.height-280)/2,310,280);
   	  addWindowListener(new WindowAdapter()
                             {
                             	 public void windowClosing(WindowEvent e)
                             	 {
                             	 	  setVisible(false);
                             	 	}
                             	} );
                             	
       validate();
      
     } 
      
     public void actionPerformed(ActionEvent aa){
     	
     	  if(aa.getSource()==ok){
     	  	String  score="";
     	   	for(int i=0;i<4;i++){
     	    	 if(i==0)        score="������Ա:"+name.getText()+"  ";
     	    	 else if(i==1) {
     	    	 	   if(attitude1.getState())   score=score+"����̬��:"+"����"+"  ";
                 if(attitude2.getState())   score=score+"����̬��:"+"һ��"+"  ";
     	    	     if(attitude3.getState())   score=score+"����̬��:"+"�ܲ�"+"  ";
     	       }		  
             else if(i==2) {
           	     if(quality1.getState())   score=score+"��������:"+"�ϳ�"+"  ";
                 if(quality2.getState())   score=score+"��������:"+"һ��"+"  ";
                 if(quality3.getState())   score=score+"��������:"+"�ܲ�"+"  ";
             }
             else if(i==3) {
             	   if(satisfy1.getState())   score=score+"����̶�:"+"����"+"  ";    
                 if(satisfy2.getState())   score=score+"����̶�:"+"һ��"+"  ";
                 if(satisfy3.getState())   score=score+"����̶�:"+"�ܲ�"+"  ";
             
            }
          }
        
          try{
           
              File file=new File("D:\\","ServicerGrade"+gradeNum+".txt");
              fileWriter=new FileWriter(file);
              out=new BufferedWriter(fileWriter);
          
              out.write(score,0,score.length() );
        
              out.flush();
              out.close();
              fileWriter.close();
                
              gradeNum++;
       
          }

          catch(IOException e2)
          {}
          
          setVisible(false);
        }
      
        else if(aa.getSource()==cancel) {
        	 setVisible(false);
        }      

     }

}





//����������
class IntroducePane extends JPanel{
	  Font fon;
	  public void paintComponent(Graphics g){
	  	  super.paintComponent(g);
	  	  
	  	  fon=new Font("Dialog",Font.PLAIN,18);
	  	  g.setFont(fon);
	  	  g.drawString("��    ��    ��    ��",230,30);
	      g.drawString("�й���ʷ�ƾ�,���������ԨԶ����,��������.�в�������ɫ,��,ζ�ζ�����.",20,70);
	      g.drawString("�����������ڸ���������Ȼ����,�Ļ�,����,ϰ�߲�ͬ,�й������γ��˲�ͬ��",2,90);
	      g.drawString("ζ�ĵط���ϵ.�����������򼯸��ز�ͬ��ζ֮��ȫ.������ϵ��ɽ����ζΪ",2,110);
	      g.drawString("����,�ó����ƺ�ζ.�Ĵ���ʦ������Ÿ�,������,����������������϶�����.",2,130);
	      g.drawString("����ȡ�Ϲ㷺,���ǽ������ۺ��ִ�.�����ע��ԭ֭ԭζ,�ر����Ͷ�����,��",2,150);
        g.drawString("������.",2,170);
        g.drawString("����λ��:",2,220);
	      g.drawString("��Ѷ��͵绰:  87654321",2,250);
	      
	  }
}






//������ܵĴ���
class IntroduceFrame extends JFrame{
	 
	  IntroducePane  introducePane;
	  Container    con;
	 
	  IntroduceFrame(){
	  	  
	  	  setVisible(false);
	  	  introducePane=new IntroducePane();
	  	  
	  	  con=getContentPane();
	  	  con.add(introducePane);
	  	  
        Dimension  screen=getToolkit().getScreenSize();
   	  
   	  setResizable(false);
   	  setBounds(  (screen.width-617)/2,(screen.height-350)/2,617,350);
   	  addWindowListener(new WindowAdapter()
                             {
                             	 public void windowClosing(WindowEvent e)
                             	 {
                             	 	  setVisible(false);
                             	 	}
                             	} );
                             	
       validate();
	  	
	  }
	  
}









//����Ա�޸����ݴ���
class UpdateDataFrame extends JFrame implements ActionListener{
	 
	 	  	JLabel      needUpdateName,updateStyle,updateName,updateCaiXi,updateCook,
	 	  	            updateSmell,updateMaterial,updateSeson,updatePrice;
	 	  	JTextField  needNameText,styleText,nameText,caixiText,cookText,smellText,
	 	  	            materialText,sesonText,priceText;
	 	  	JPanel      pan;
	 	  	JButton     ok,cancel;
	 	  	
	 	  	Container   con;
	 	  	Connection  conn;
	      Statement   sql;
	      ResultSet   rs;
	 	  	Font        fon1=new Font("Dialog",Font.PLAIN,14)
	 	  	             ,fon2=new Font("Dialog",Font.PLAIN,13);
	 	  	
	 	  	
	 	  	UpdateDataFrame(String ss){
	 	  		  
	 	  		  super(ss);
	 	  		  
	 	  		  needUpdateName=new JLabel("��������Ҫ�޸ĵĲ���");
	 	  		  needUpdateName.setFont(fon1);
	 	  		  updateStyle=new JLabel("�������޸ĺ������");
	 	  		  updateStyle.setFont(fon1);
	 	  		  updateName=new JLabel("�������޸ĺ�Ĳ���");
	 	  		  updateName.setFont(fon1);
	 	  		  updateCaiXi=new JLabel("�������޸ĺ�Ĳ�ϵ");
	 	  		  updateCaiXi.setFont(fon1);
	 	  		  
	 	  		  updateCook=new JLabel("�������޸ĺ���������");
	 	  	    updateCook.setFont(fon1);
	 	  	    updateSmell=new JLabel("�������޸ĺ��ζ��");
	 	  	    updateSmell.setFont(fon1);
	 	  	    updateMaterial=new JLabel("�������޸ĺ�Ĳ���");
	 	  	    updateMaterial.setFont(fon1);
	 	  	    updateSeson=new JLabel("�������޸ĺ�����˼���");
	 	  	    updateSeson.setFont(fon1);
	 	  	    updatePrice =new JLabel("�������޸ĺ�ļ۸�");     
	 	  	    updatePrice.setFont(fon1);
	 	  	    needNameText=new JTextField(100);
	 	  	    styleText=new JTextField(100);
	 	  	    nameText=new JTextField(100);
	 	  	    caixiText=new JTextField(100);
	 	  	    cookText=new JTextField(100);
	 	  	    smellText=new JTextField(100);
	 	  	    materialText=new JTextField(100);
	 	  	    sesonText=new JTextField(100);
	 	  	    priceText=new JTextField(100);
	 	  	    
	 	  	    ok=new JButton("�ύ");
	 	  	    ok.setFont(fon2);
	 	  	    ok.addActionListener(this);
	 	  	    cancel=new JButton("ȡ��");
	 	  	    cancel.setFont(fon2);
	 	  	    cancel.addActionListener(this);
	 	  	    
	 	  	    con=getContentPane();
	 	  	    con.setLayout(new BorderLayout());
	 	  	    pan=new JPanel();
	 	  	    pan.setLayout(null);
	 	  	    
	 	  	    /////////////////////////////////////////
	 	  	    pan.add(needUpdateName);
	 	  	    needUpdateName.setBounds(40,20,200,25);
	 	  	    pan.add(needNameText);
	 	  	    needNameText.setBounds(230,20,110,25);
	 	  	    pan.add(updateStyle);
	 	  	    updateStyle.setBounds(40,55,200,25);
	 	  	    pan.add(styleText);
	 	  	    styleText.setBounds(230,55,110,25);
	 	  	    pan.add(updateName);
            updateName.setBounds(40,90,200,25);
            pan.add(nameText); 
            nameText.setBounds(230,90,110,25);
            pan.add(updateCaiXi);	 	  	    	 	  	  
            updateCaiXi.setBounds(40,125,200,25);
            pan.add(caixiText);  
            caixiText.setBounds(230,125,110,25);
            pan.add(updateCook);
	 	  	    updateCook.setBounds(40,160,200,25);
	 	  	    pan.add(cookText);
	 	  	    cookText.setBounds(230,160,110,25);
	 	  	    pan.add(updateSmell);
	 	  	    updateSmell.setBounds(40,195,200,25);
	 	  	    pan.add(smellText);
	 	  	    smellText.setBounds(230,195,110,25);
	 	  	    pan.add(updateMaterial);
	 	  	    updateMaterial.setBounds(40,230,200,25);
	 	  	    pan.add(materialText);
	 	  	    materialText.setBounds(230,230,110,25);
	 	  	    pan.add(updateSeson);
	 	  	    updateSeson.setBounds(40,265,200,25);
	 	  	    pan.add(sesonText);
	 	  	    sesonText.setBounds(230,265,110,25);
	 	  	    pan.add(updatePrice);
	 	  	    updatePrice.setBounds(40,300,200,25);
	 	  	    pan.add(priceText);
	 	  	    priceText.setBounds(230,300,110,25);
	 	  	    
	 	  	    pan.add(ok);
	 	  	    ok.setBounds(110,360,60,30);
	 	  	    pan.add(cancel);
	 	  	    cancel.setBounds(240,360,60,30);
	 	  	    
	 	  	    
	 	  	    
	 	  	    con.add(pan,BorderLayout.CENTER);
	 	  	    
	 	  	    setVisible(false);
	 	  	    
	 	  	    Dimension  screen=getToolkit().getScreenSize();
   	  
   	        setResizable(false);
   	        setBounds(  (screen.width-420)/2,(screen.height-450)/2,420,450);
   	        addWindowListener(new WindowAdapter()
                             {
                             	 public void windowClosing(WindowEvent e)
                             	 {
                             	 	  setVisible(false);
                             	 	}
                             	} );
                             	
            validate();
	 	  	    
	 	  	}
	 	  	

        public void actionPerformed(ActionEvent ee){
        	
        	 if(ee.getSource()==ok){
        	 	  
        	 	 int num=JOptionPane.showConfirmDialog(this,"ȷ��Ҫ��Դ���ݿ�����޸���?",
        	 	                                        "ȷ�϶Ի���",JOptionPane.YES_NO_OPTION);
        	 	 if(num==JOptionPane.YES_OPTION){                                      
        	 	  
        	 	  
        	 	  
        	 	  
        	 	  
        	 	  try
    	 	      {
    	 	     	   Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	 	      }
    	 	      catch(ClassNotFoundException ek)
    	 	      {
    	 	     	  System.out.print(""+ek);
    	 	      }
        	 	  try
        	 	  {  
        	 	  	conn=DriverManager.getConnection("jdbc:odbc:foodmenu","","");
    	 	        sql=conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE ,
                                      ResultSet.CONCUR_UPDATABLE);
    	 	        
    	 	         
    	 	        String  s1,s2,s3,s4,s5,s6,s7,s8,temp;
    	 	     //          modifyStyle,modifyName,modifyCaiXi,modifyCook,modifySmell
    	 	     //          modifyMaterial,modifySeson,modifyPrice,temp;
       	      
       	      
       	      s1= "'"+ needNameText.getText().trim() +"'";
        	 	  s2= "'"+ styleText.getText().trim()    +"'";
        	 	  s3= "'"+ nameText.getText().trim()     +"'";
        	 	  s4= "'"+ caixiText.getText().trim()    +"'";
        	 	  s5= "'"+ cookText.getText().trim()     +"'";
        	 	  s6= "'"+ smellText.getText().trim()    +"'";
	 	  	      s7= "'"+ materialText.getText().trim() +"'";
	 	  	      s8= "'"+ sesonText.getText().trim()    +"'";
	   	        int   s9= Integer.parseInt(priceText.getText().trim());
        	 	 
    
       	  
       	      temp="UPDATE cookbook SET ����=" +s2+",����="+s3+" , ��ϵ=" +s4+ " , �������=" +s5
       	           + " , ζ��=" +s6+ " , ��������=" +s7+ " , ���˼���=" +s8+" , �۸�=" +s9+
       	            " WHERE ����="+s1;
       	      
       	      System.out.println(temp);
       	      
       	//      conn=DriverManager.getConnection("jdbc:odbc:foodmenu","","");
              
       
             int dada=sql.executeUpdate(temp); 
        	 	  
        	 	  System.out.print(""+dada);
              }
              catch(SQLException sa)
    	 	     	{
    	 	     	   System.out.print(sa);  
    	 	     	}	 	 
              
              setVisible(false);
            
            }    
           
           
           }      
           
           else if(ee.getSource()==cancel){  
           	  
           	   setVisible(false);   
           }
                 
       }          
       
}	 	  	    
	 	  	    
	 	  	    
	
//	����Ա�������ݴ���
class InsterDataFrame extends JFrame implements ActionListener{
	  	 
	
	      JLabel      interName,interStyle,interCaiXi,interCook,
	 	  	            interSmell,interMaterial,interSeson,interPrice;
	 	  	JTextField  interNameText,interStyleText,interCaiXiText,interCookText,
	 	  	            interSmellText,interMaterialText,interSesonText,interPriceText;
	 	  	JPanel      pan;
	 	  	JButton     ok,cancel;
	 	  	
	 	  	Container   con;
	 	  	Connection  conn;
	      Statement   sql;
	      ResultSet   rs;
	 	  	Font        fon1=new Font("Dialog",Font.PLAIN,14)
	 	  	             ,fon2=new Font("Dialog",Font.PLAIN,13);
	 	  	
	 	  	
	 	  	InsterDataFrame(String ss){
	 	  		  
	 	  		  super(ss);
	 	  		  
	 	  		  interName=new JLabel("��������Ҫ����Ĳ���");
	 	  		  interName.setFont(fon1);
	 	  		  interStyle=new JLabel("��������Ҫ���������");
	 	  		  interStyle.setFont(fon1);
	 	  		  interCaiXi=new JLabel("��������Ҫ����Ĳ�ϵ");
	 	  		  interCaiXi.setFont(fon1);
	 	  		  interCook=new JLabel("��������Ҫ������������");
	 	  	    interCook.setFont(fon1);
	 	  	    interSmell=new JLabel("��������Ҫ�����ζ��");
	 	  	    interSmell.setFont(fon1);
	 	  	    interMaterial=new JLabel("��������Ҫ����Ĳ���");
	 	  	    interMaterial.setFont(fon1);
	 	  	    interSeson=new JLabel("��������Ҫ��������˼���");
	 	  	    interSeson.setFont(fon1);
	 	  	    interPrice =new JLabel("��������Ҫ����ļ۸�");     
	 	  	    interPrice.setFont(fon1);
	 	  	    
	 	  	    
	 	  	    interNameText=new JTextField(100);
	 	  	    interStyleText=new JTextField(100);
	 	  	    interCaiXiText=new JTextField(100);
	 	  	    interCookText=new JTextField(100);
	 	  	    interSmellText=new JTextField(100);
	 	  	    interMaterialText=new JTextField(100);
	 	  	    interSesonText=new JTextField(100);
	 	  	    interPriceText=new JTextField(100);
	 	  	    
	 	  	    ok=new JButton("�ύ");
	 	  	    ok.addActionListener(this);
	 	  	    ok.setFont(fon2);
	 	  	    cancel=new JButton("ȡ��");
	 	  	    cancel.addActionListener(this);
	 	  	    cancel.setFont(fon2);
	 	  	    
	 	  	    
	 	  	    con=getContentPane();
	 	  	    con.setLayout(new BorderLayout());
	 	  	    pan=new JPanel();
	 	  	    pan.setLayout(null);
	 	  	    
	 	  	    /////////////////////////////////////////
	 	  	    pan.add(interName);
	 	  	    interName.setBounds(40,20,200,25);
	 	  	    pan.add(interNameText);
	 	  	    interNameText.setBounds(230,20,110,25);
	 	  	    pan.add(interStyle);
	 	  	    interStyle.setBounds(40,55,200,25);
	 	  	    pan.add(interStyleText);
	 	  	    interStyleText.setBounds(230,55,110,25);
	 	  	    pan.add(interCaiXi);
            interCaiXi.setBounds(40,90,200,25);
            pan.add(interCaiXiText); 
            interCaiXiText.setBounds(230,90,110,25);
            pan.add(interCook);	 	  	    	 	  	  
            interCook.setBounds(40,125,200,25);
            pan.add(interCookText);  
            interCookText.setBounds(230,125,110,25);
            pan.add(interSmell);
	 	  	    interSmell.setBounds(40,160,200,25);
	 	  	    pan.add(interSmellText);
	 	  	    interSmellText.setBounds(230,160,110,25);
	 	  	    pan.add(interMaterial);
	 	  	    interMaterial.setBounds(40,195,200,25);
	 	  	    pan.add(interMaterialText);
	 	  	    interMaterialText.setBounds(230,195,110,25);
	 	  	    pan.add(interSeson);
	 	  	    interSeson.setBounds(40,230,200,25);
	 	  	    pan.add(interSesonText);
	 	  	    interSesonText.setBounds(230,230,110,25);
	 	  	    pan.add(interPrice);
	 	  	    interPrice.setBounds(40,265,200,25);
	 	  	    pan.add(interPriceText);
	 	  	    interPriceText.setBounds(230,265,110,25);
	 	  	    
	 	  	    
	 	  	    
	 	  	    pan.add(ok);
	 	  	    ok.setBounds(110,330,60,30);
	 	  	    pan.add(cancel);
	 	  	    cancel.setBounds(240,330,60,30);
	 	  	    
	 	  	    
	 	  	    
	 	  	    con.add(pan,BorderLayout.CENTER);
	 	  	    
	 	  	    setVisible(false);
	 	  	    
	 	  	    Dimension  screen=getToolkit().getScreenSize();
   	  
   	        setResizable(false);
   	        setBounds(  (screen.width-420)/2,(screen.height-420)/2,420,420);
   	        addWindowListener(new WindowAdapter()
                             {
                             	 public void windowClosing(WindowEvent e)
                             	 {
                             	 	  setVisible(false);
                             	 	}
                             	} );
                             	
            validate();
	 	  	    
	 	  	}


        public void actionPerformed(ActionEvent dd){
        	
        	    if(dd.getSource()==ok){
        	    	
        	    	 int num=JOptionPane.showConfirmDialog(this,"ȷ��Ҫ��Դ���ݿ�����޸���?",
        	 	                                        "ȷ�϶Ի���",JOptionPane.YES_NO_OPTION);
        	 	     if(num==JOptionPane.YES_OPTION){  
        	    	
        	    	      try
    	 	              {
    	 	     	            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	 	              }
    	 	              catch(ClassNotFoundException ek)
    	 	              {
    	 	     	            System.out.print(""+ek);
    	 	              }
                      try
        	 	          {         	 	   
        	 	  	          conn=DriverManager.getConnection("jdbc:odbc:foodmenu","","");
    	 	                  sql=conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE ,
                                                   ResultSet.CONCUR_UPDATABLE);
    	 	        
    	 	         
    	 	                  String  s1,s2,s3,s4,s5,s6,s7,s8,temp;
    	 	     
       	                 
       	                  
       	                  s1= "'"+ interNameText.getText().trim() +"'";
        	 	              s2= "'"+ interStyleText.getText().trim()    +"'";
        	 	              s3= "'"+ interCaiXiText.getText().trim()     +"'";
        	 	              s4= "'"+ interCookText.getText().trim()    +"'";
        	 	              s5= "'"+ interSmellText.getText().trim()     +"'";
        	 	              s6= "'"+ interMaterialText.getText().trim()    +"'";
	 	  	                  s7= "'"+ interSesonText.getText().trim() +"'";
	 	  	                  s8= "'"+ Integer.parseInt(interPriceText.getText().trim())+"'";
	   	                    
        	 	              temp="INSERT INTO cookbook  VALUES ("+s1+","+s2+","+s3+","+s4+
        	 	                   ","+s5+","+s6+","+s7+","+s8+")";
        	 	                   
        	 	              int dada=sql.executeUpdate(temp);     
        	    	
        	    	          System.out.print(""+dada);
                      }   
                      catch(SQLException sa)
    	 	     	        {
    	 	     	            System.out.print(sa);  
    	 	     	        }	 	  
                      
        	            setVisible(false);
        	        }
        	    }
        	    
        	    else if(dd.getSource()==cancel){
        	    	
                   setVisible(false);	    	
        	    }
        
        }	
        	
}	


// 	����Աɾ�����ݴ���	
class DeleteDataFrame extends JFrame  implements ActionListener{

	   
	    JLabel       dele;
	    JTextField   deleText;
	    JButton      ok,cancel;
	    JPanel       pan;
	    Container    con;
	    Font        fon1=new Font("Dialog",Font.PLAIN,14)
	 	  	             ,fon2=new Font("Dialog",Font.PLAIN,13);
	    
	    
	    DeleteDataFrame(String ss) {
	    	 
	    	  super(ss);
	    	  
	    	  dele=new JLabel("��������Ҫɾ���Ĳ���");
	    	  dele.setFont(fon1);
	    	  deleText=new JTextField(100);
	    	  ok=new JButton("�ύ");
 	  	    ok.setFont(fon2);
 	  	    ok.addActionListener(this);
	 	  	  cancel=new JButton("ȡ��");
//	 	  	    cancel.addActionListener(this);
	    	  cancel.setFont(fon2);
	    	  
	    	   
	    	  pan=new JPanel();
	    	  pan.setLayout(null);
	    	  
	    	  pan.add(dele);
	    	  dele.setBounds(20,50,200,25);
	    	  pan.add(deleText);
	    	  deleText.setBounds(240,50,110,25);
	    	  pan.add(ok);
	    	  ok.setBounds(110,100,60,30);
	    	  pan.add(cancel);
	    	  cancel.setBounds(240,100,60,30);
	    	  
	    	  con=getContentPane();
	    	  con.add(pan);
	    	  
	    	  setVisible(false);
	 	  	    
	 	  	    Dimension  screen=getToolkit().getScreenSize();
   	  
   	        setResizable(false);
   	        setBounds(  (screen.width-420)/2,(screen.height-260)/2,420,260);
   	        addWindowListener(new WindowAdapter()
                             {
                             	 public void windowClosing(WindowEvent e)
                             	 {
                             	 	  setVisible(false);
                             	 	}
                             	} );
                             	
            validate();
       }
       

       public void actionPerformed(ActionEvent ee){
       	
          if(ee.getSource()==ok){ 	
           	
           	
           	int num=JOptionPane.showConfirmDialog(this,"��ȷ��Ҫɾ��������¼��?",
        	 	                                        "ȷ�϶Ի���",JOptionPane.YES_NO_OPTION);
        	 	 if(num==JOptionPane.YES_OPTION){ 
        	 	 	
        	 	   Connection  con;
      	       Statement   sql;
               ResultSet   rs;
      	  
      	       try{
    	 	     	     Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	 	       }
    	 	       catch(ClassNotFoundException ek){
    	 	    
    	 	      	    System.out.print(""+ek);
    	 	       }
      	    
      	       try{
    	 	     	 
    	 	      	  con=DriverManager.getConnection("jdbc:odbc:foodmenu","","");
    	 	      	  sql=con.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE , 
                                      ResultSet.CONCUR_UPDATABLE); 
       	
                  String temp="'"+deleText.getText()+"'";
                
               
                 int aas=sql.executeUpdate("DELETE FROM cookbook WHERE ����= "+temp);
               
                 System.out.print(""+aas);
               }      
                
               catch(SQLException ew){
    	 	     		   
    	 	     		   System.out.print(""+ew);
    	 	       }	 	  	      
               
               setVisible(false);
                
             }     
        
          }
          else if(ee.getSource()==cancel){
          	
            setVisible(false);
          }	
        }        


}  




//����Աȷ�ϴ���
class Ensure extends JFrame implements ActionListener,Runnable{
	
          JPanel      pan;
          JLabel      title;
          JButton     ok;
          TextField   pass;
          Container   con;
          Thread      waitting;
          boolean     isAdmin=false;
          int          frameNum,num=0;
          UpdateDataFrame  updateDataFrame;
          InsterDataFrame  insterDataFrame;
          DeleteDataFrame  deleteDataFrame;
          
      Ensure(String ass,int frameNum){
          
          super(ass);
          
          this.frameNum=frameNum;
          
          title=new JLabel("���������Ա����");
          title.setFont(new Font("Dialog",Font.PLAIN,16));
          pass=new TextField(10);
          pass.setEchoChar('*');
          
          ok=new JButton("ȷ��");
          ok.addActionListener(this);
          pan=new JPanel();
          pan.setLayout(null);
          pan.add(title);
          title.setBounds(40,40,140,30);
          pan.add(pass);
          pass.setBounds(40,80,140,20);
          pan.add(ok);
          ok.setBounds(90,140,60,25);
          
          
          con=getContentPane();
          con.add(pan);
          
          
          setVisible(true);
          setResizable(false);
       
          Dimension  screen=getToolkit().getScreenSize();
          setBounds(  (screen.width-250)/2,(screen.height-220)/2,250,220);
          addWindowListener(new WindowAdapter()
                             {
                             	 public void windowClosing(WindowEvent e)
                             	 {
                             	 	  setVisible(false);
                             	 	}
                             	} );
                             	
          validate();
      }
      
      public void actionPerformed(ActionEvent act){
      	
         if(pass.getText().equals("909b0")){
          	
          	pan.removeAll();
          	JLabel welcome=new JLabel("��ӭ����Ա����");
            welcome.setFont(new Font("Dialog",Font.PLAIN,18));
            pan.add(welcome);
            welcome.setBounds(30,40,140,40);
            waitting=new Thread(this);
            waitting.start();
            
            num=1;
          
            
          
          }
          else {
          	
          	pan.removeAll();
          	JLabel error=new JLabel("��������");
            error.setFont(new Font("Dialog",Font.PLAIN,18));
            pan.add(error);
            error.setBounds(30,40,140,40);
            waitting=new Thread(this);
            waitting.start();
          
            num=0;  
          
          }
	 	  } 
	 	  
	 	  public void run(){
	 	  	
	 	  	   try{
	 	  		
	 	  		     waitting.sleep(3000);
	 	  	   }
	 	  	   catch(InterruptedException in)
	 	  	   {}
	 	     
	 	       dispose(); 		
	 	  
	 	       if(num==1){
	 	     
	 	          if(frameNum==1){
	 	     	        updateDataFrame=new UpdateDataFrame("�޸�����");
	 	              updateDataFrame.setVisible(true);
	 	     
	 	          }    
	 	          else if(frameNum==2){
	 	     	        insterDataFrame=new InsterDataFrame("��������");
	 	     	        insterDataFrame.setVisible(true);
	 	          }	
	 	          else if(frameNum==3){
	 	     	         deleteDataFrame=new DeleteDataFrame("ɾ������");
	 	     	         deleteDataFrame.setVisible(true);
	 	     	
	 	          }	 
	 	   
	 	       }
	 	  
	 	  
	 	   }
	 	  
	 	 
	
}	 	     	                          	      