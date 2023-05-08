//电脑点菜系统

import javax.swing.*;              
import java.awt.event.*;
import java.awt.*;
import java.sql.*;            
import java.net.*;
import java.io.*;
import java.util.*;


//创建主窗体
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
	  String[] allDishes= new String[100]; //存放已点菜
	  String[] allDrink=new String[50];
	  String[] allName=new String[150];
	  String[] allPrice=new String[150];
	  int           dishNum=0,drinkNum=0;   //已点菜,酒水的计数器
	  int           allNameNum=0,allPriceNum=0,choose=0;
	  int           billNum=0;
	  LiveMess      liveMess;
	  CheckConfirmedDishFrame    checkConfirmedDishFrame=null; //查看已点菜的窗口
	  Grade       grade=null;    //为服务人员评分的窗口
	  LinkedList  confirmedDishLink,confirmedPriceLink;
	  boolean      confirmedDishIsChaneged=false;
//	  IntroducePane  introducePane;
	  IntroduceFrame      introduceFrame;  //饭店文化介绍窗口
//	  UpdateDataFrame    updateDataFrame;
//	  InsterDataFrame    insterDataFrame;
//	  DeleteDataFrame    deleteDataFrame;
	  Thread         timer;    //时钟的进程
	  
	  
	  
	  MainFrame(String ss)
	  {
	  	 super(ss);
	  	 
//	  	 System.out.println(""+user.home);
	  	 
	  	 font=new Font("Dialog",Font.PLAIN,22);
       fontInDetail=new Font("Dialog",Font.PLAIN,16);
	  	 fon1=new Font("Dialog",Font.PLAIN,14);
	  	 fon2=new Font("Dialog",Font.PLAIN,12);
	  	 
	  	 
	  	 confirmDish=new JButton("点这道菜");
	  	 confirmDish.addActionListener(this);
	  	 confirmDish.setFont(fon1);
	  	 confirmDish.setBackground(new Color (153,179,179));
	  	 checkConfirmedDish=new JButton("查看已点菜");
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
       servicerButton=new JButton("评分");
       servicerButton.setBackground(new Color (153,179,179));
       servicerButton.setFont(fon1);
       servicerButton.addActionListener(this);  
//	  	 help=new JButton("帮助");
//	  	 tools=new JButton("工具");
	  	 
       String[] sort={"","热菜","冷菜","特色菜","汤类","小吃","酒水"};
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
       
       
       
       label1=new JLabel("今日餐桌");
       label1.setFont(fon1);
       label2=new JLabel("查询");
       label2.setFont(fon1);
       label3=new JLabel("留下建议");
       label3.setFont(fon1);
       servicerLabel=new JLabel("为服务员");
       servicerLabel.setFont(fon1);
       payforLabel=new JLabel("买      单");
       payforLabel.setFont(fon1);
       label_foodName=new JLabel("菜       名:");
       label_foodName.setFont(font);
       label_caixi=new JLabel("菜       系:");
       label_caixi.setFont(font);
       label_cookStyle=new JLabel("烹调类型:");   
       label_cookStyle.setFont(font);
       label_taste=new JLabel("味       道:");
       label_taste.setFont(font);
       label_material=new JLabel("材       料:");
       label_material.setFont(font);
       label_seson=new JLabel("适宜季节:");
       label_seson.setFont(font);
       label_price=new JLabel("价       格:");
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
       name=new JLabel("名       称:");
       name.setFont(font);
       nameC=new JLabel("");
       nameC.setFont(font);
       danjia=new JLabel("单       价:");
       danjia.setFont(font);
       danjiaC=new JLabel("");
       danjiaC.setFont(font);
       dateLabel=new JLabel();
       dateLabel.setFont(fon1);
       dateLabel.setForeground(new Color(0,128,255));
       timeLabel=new JLabel();
       timeLabel.setFont(fon1);
       timeLabel.setForeground(new Color(0,128,255));
       
       
       
       
//      label4=new JLabel("明细");
       
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
       admin=new JMenu("管理员");
       admin.setFont(fon2);
       help=new JMenu("帮助");
       help.setFont(fon2);
       culture=new JMenu("介绍");
       culture.setFont(fon2);
       updateData=new JMenuItem("修改数据");
       updateData.setFont(fon2);
       updateData.addActionListener(this);
       insertData=new JMenuItem("插入数据");
       insertData.setFont(fon2);
       insertData.addActionListener(this);
       deleteData=new JMenuItem("删除数据");
       deleteData.setFont(fon2);
       deleteData.addActionListener(this);
       about=new JMenuItem("关于");
       about.addActionListener(this);
       cultureIntro=new JMenuItem("饭店文化");
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
       
       liveMess=new LiveMess("留言簿");
       checkConfirmedDishFrame=new CheckConfirmedDishFrame("已点菜列表");
       grade=new Grade("为服务员评分");
       introduceFrame=new IntroduceFrame();
//       updateDataFrame=new UpdateDataFrame("修改数据");   //管理员修改数据窗口
//       insterDataFrame=new InsterDataFrame("插入数据");   //管理员插入数据窗口
//       deleteDataFrame=new DeleteDataFrame("删除数据");   //管理员删除数据窗口
       
       
       //为 dishPane 布局,并为其添加组件
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
       
       
       //为 suggtionPane 布局,并为其添加组件    
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
       
       
       //为 displayDish 布局,并为其添加组件
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
       //粘贴图片
     //  tool=getToolkit();  
     //  ima=tool.getImage("start.jpg");
       
       
       split_VL=new JSplitPane(JSplitPane.VERTICAL_SPLIT,dishPane,suggestionPane);
       split_VL.setDividerLocation(220);
  //     split_VL.setBackground(Color.red);
       split_VR=new JSplitPane(JSplitPane.VERTICAL_SPLIT,displayDish,picPane);
       split_VR.setDividerLocation(452);
       split_H=new  JSplitPane(JSplitPane.HORIZONTAL_SPLIT,split_VL,split_VR);
       split_H.setDividerLocation(170);
       
       //对主窗口进行初始化
       contentPane=getContentPane();
       contentPane.add(menuBar,BorderLayout.NORTH);
       contentPane.add(split_H);
       
       
       setVisible(false);
      
//       setBackground(new Color(186,185,133));   //设置背景色不起作用 
       setResizable(false);
       
       
       //设置窗口在屏幕的中间
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
       
       timer.start();//开始一个时钟
    
    }
    
    
    //处理今日餐桌事件
    public void itemStateChanged(ItemEvent e)
    { 
    	 
    	
    	 if(e.getItemSelectable()==sortDish)
    	 { 
    	 	  if(!(sortDish.getSelectedItem().equals("")))
    	 	  {
    	 	  	 remove(displayDish);
    	 	  	 validate();
    	 	  
    	 	     
    	 	     
    	 	     //根据choose的值判断从sortDish选择的是哪种类型的菜
    	 	     choose=sortDish.getSelectedIndex();
    	 	  
    	 	     
    	 	     //选择的是热菜
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
    	 	     	     rs=sql.executeQuery("SELECT  * FROM cookbook WHERE  类型='热菜' " );
    	 	     	 
    	 	     	     rs.next();
    	 	     	     
    	 	     	 	   foodName=rs.getString(1);
    	 	     	 	   caixi=rs.getString(3);
    	 	     	 	   cookStyle=rs.getString(4);
    	 	     	 	   taste=rs.getString(5);
    	 	     	 	   material=rs.getString(6);
    	 	     	 	   seson=rs.getString(7); 
    	 	     	 	   price=rs.getString(8);
    	 	     	 	   
    //	 	     	     con.close();
    	 	     	  

    	 	     	     if(foodName.equals("冬菇豆腐")){
    	 	     	        
    	 	     	        displayDish.setNum(0,true);
    	 	     	        displayDish.repaint();
    	 	     	       
    	 	     	        System.out.println("画画");
    	 	     	     }   
    	 	            	      
    	 	
    	 	
    	 	
    	 	//     	  displayDish.remove(name);
    	 	//     	  displayDish.remove(nameC);
    	 	//     	  displayDish.remove(danjia);
    	 	//     	  displayDish.remove(danjiaC);
    	 	     	
    	 	     	     name.setText("");
    	 	     	     nameC.setText("");
    	 	     	     danjia.setText("");
    	 	     	     danjiaC.setText("");
    	 	     	     
    	 	     	  label_foodName.setText("菜       名:");
    	 	     	  label_caixi.setText("菜       系:");
    	 	     	  label_cookStyle.setText("烹调类型");
	             label_taste.setText("味       道:");
	              label_material.setText("材       料:");
	              label_seson.setText("适宜季节:");
	              label_price.setText("价       格:");
	              
    	 	     	  
    	 	     	  
    	 	     	  
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
    	 	     	     rs=sql.executeQuery("SELECT  * FROM cookbook WHERE  类型='冷菜' " );
    	 	     	 
    	 	     	     rs.next();
    	 	     	     
    	 	     	 	     foodName=rs.getString(1);
    	 	     	 	     caixi=rs.getString(3);
    	 	     	 	     cookStyle=rs.getString(4);
    	 	     	 	     taste=rs.getString(5);
    	 	     	 	     material=rs.getString(6);
    	 	     	 	    seson=rs.getString(7); 
    	 	     	 	     price=rs.getString(8);
    	 	     	 	   
    	 	  //   	     con.close();
    	 	     	  
    	 	     	 
    	 	     	  if(foodName.equals("三丝拌蛏")){
    	 	     	        
    	 	     	        displayDish.setNum(9,true);
    	 	     	        displayDish.repaint();
    	 	     	       
    	 	     	        System.out.println("画画"+foodName);
    	 	     	  }   
    	 	     	  
    	 	     	  name.setText("");
    	 	     	  nameC.setText("");
    	 	     	  danjia.setText("");
    	 	     	  danjiaC.setText("");
    	 	     	     
    	 	     	  label_foodName.setText("菜       名:");
    	 	     	  label_caixi.setText("菜       系:");
    	 	     	  label_cookStyle.setText("烹调类型");
	             label_taste.setText("味       道:");
	              label_material.setText("材       料:");
	              label_seson.setText("适宜季节:");
	              label_price.setText("价       格:");
    	 	     	  
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
    	 	     	     rs=sql.executeQuery("SELECT  * FROM cookbook WHERE  类型='特色菜' " );
    	 	     	 
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
    	 	     	   
    	 	     	   label_foodName.setText("菜       名:");
    	 	     	  label_caixi.setText("菜       系:");
    	 	     	  label_cookStyle.setText("烹调类型");
	             label_taste.setText("味       道:");
	              label_material.setText("材       料:");
	              label_seson.setText("适宜季节:");
	              label_price.setText("价       格:");
    	 	     	     
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
    	 	     	     rs=sql.executeQuery("SELECT  * FROM cookbook WHERE  类型='汤类' " );
    	 	     	 
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
    	 	     	  
                  if(foodName.equals("龙凤酸辣汤")){
    	 	     	        
    	 	     	        displayDish.setNum(10,true);
    	 	     	        displayDish.repaint();
    	 	     	       
    	 	     	        System.out.println("画画"+foodName);
    	 	     	    }   
                  
                  
                  
                  name.setText("");
    	 	     	     nameC.setText("");
                  danjia.setText("");
    	 	     	     danjiaC.setText("");
    	 	     	   
    	 	     	   label_foodName.setText("菜       名:");
    	 	     	  label_caixi.setText("菜       系:");
    	 	     	  label_cookStyle.setText("烹调类型");
	             label_taste.setText("味       道:");
	              label_material.setText("材       料:");
	              label_seson.setText("适宜季节:");
	              label_price.setText("价       格:");
    	 	     	     
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
    	 	     	     rs=sql.executeQuery("SELECT  * FROM cookbook WHERE  类型='小吃' " );
    	 	     	 
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
    	 	     	  
    	 	     	    if(foodName.equals("海南椰子盅")){
    	 	     	        
    	 	     	        displayDish.setNum(12,true);
    	 	     	        displayDish.repaint();
    	 	     	       
    	 	     	        System.out.println("画画"+foodName);
    	 	     	    }   
    	 	     	  
    	 	     	  
    	 	     	  name.setText("");
    	 	     	     nameC.setText("");
    	 	     	  danjia.setText("");
    	 	     	     danjiaC.setText("");
    	 	     	   
    	 	     	   label_foodName.setText("菜       名:");
    	 	     	  label_caixi.setText("菜       系:");
    	 	     	  label_cookStyle.setText("烹调类型");
	             label_taste.setText("味       道:");
	              label_material.setText("材       料:");
	              label_seson.setText("适宜季节:");
	              label_price.setText("价       格:");
    	 	     	     
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
    	 	     	     rs=sql.executeQuery("SELECT  * FROM cookbook WHERE  类型='酒水' " );
    	 	     	 
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
	              
	   //         name=new JLabel("名       称:");
      //          name.setFont(font);
      //         nameC=new JLabel("");
      //          nameC.setFont(font);
       //         danjia=new JLabel("单       价:");
      //          danjia.setFont(font);
       //          danjiaC=new JLabel("");
      //           danjiaC.setFont(font);   	 	     	  
    	 	     	  
    	 	     	  name.setText("名       称:");
    	 	     	  danjia.setText("单       价:");
    	 	     	  
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
    	  
    	  //处理点激 next按钮 事件
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
    	       
    	       if(foodName.equals("年年有余"))          drawNum=1;
    	 	     else if(foodName.equals("葡萄鱼"))     drawNum=2;
    	 	     else if(foodName.equals("海米珍珠笋"))     drawNum=3;
    	 	     else if(foodName.equals("小笼粉蒸牛肉"))     drawNum=4;
    	 	     else if(foodName.equals("椰盅海皇"))     drawNum=5;
    	 	     else if(foodName.equals("芙蓉鲫鱼"))     drawNum=6;
    	 	     else if(foodName.equals("糖醋咕噜肉"))     drawNum=7;
    	 	     else if(foodName.equals("荔枝虾球"))     drawNum=8;
    	 	     else if(foodName.equals("清汤萝卜燕"))     drawNum=9;
    	 	     else if(foodName.equals("健康萝卜糕"))     drawNum=10;
    	 	     
    	 	     switch(drawNum){
    	 	     	 case 1: {
    	 	     	 	  
    	 	     	        displayDish.setNum(1,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	       case 2: {
    	 	     	 	  
    	 	     	        displayDish.setNum(2,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 3: {
    	 	     	 	  
    	 	     	        displayDish.setNum(3,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 4: {
    	 	     	 	  
    	 	     	        displayDish.setNum(4,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 5: {
    	 	     	 	  
    	 	     	        displayDish.setNum(5,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 6: {
    	 	     	 	  
    	 	     	        displayDish.setNum(6,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 7: {
    	 	     	 	  
    	 	     	        displayDish.setNum(7,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 8: {
    	 	     	 	  
    	 	     	        displayDish.setNum(8,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 9: {
    	 	     	 	  
    	 	     	        displayDish.setNum(11,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 
    	 	     	 case 10: {
    	 	     	 	  
    	 	     	        displayDish.setNum(13,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	       default :{
    	 	       	
    	 	       	      displayDish.setNum(11,false);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("没有图片了");
    	 	     	 }       
    	 	     
    	 	     
    	 	    }
    	 	     
    	 	     
    	 	     	        
    	 	        
    	       
    	       
    	       
    	     
    	     
    	       if(aa==5 || aa==8 || aa==10 || aa==15 || aa==20 || aa==25 ||aa==30)
    	        { 
    	         name.setText("");
    	 	     	     nameC.setText("");
    	 	     	  danjia.setText("");
    	 	     	     danjiaC.setText("");
    	 	     	   
    	 	     	   label_foodName.setText("菜       名:");
    	 	     	  label_caixi.setText("菜       系:");
    	 	     	  label_cookStyle.setText("烹调类型");
	             label_taste.setText("味       道:");
	              label_material.setText("材       料:");
	              label_seson.setText("适宜季节:");
	              label_price.setText("价       格:");
    	 	     	     
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
    	         	
    	         	name.setText("名       称:");
    	 	     	  danjia.setText("单       价:");
    	 	     	  
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
    	  
    	  
    	  
    	  //处理点激 previous按钮实践
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
    	       
    	        
    	       if(foodName.equals("冬菇豆腐"))            drawNum=0;
    	       else if(foodName.equals("年年有余"))       drawNum=1;
    	 	     else if(foodName.equals("葡萄鱼"))         drawNum=2;
    	 	     else if(foodName.equals("海米珍珠笋"))     drawNum=3;
    	 	     else if(foodName.equals("小笼粉蒸牛肉"))   drawNum=4;
    	 	     else if(foodName.equals("椰盅海皇"))       drawNum=5;
    	 	     else if(foodName.equals("芙蓉鲫鱼"))       drawNum=6;
    	 	     else if(foodName.equals("糖醋咕噜肉"))     drawNum=7;
    	 	     else if(foodName.equals("荔枝虾球"))       drawNum=8;
    	 	     else if(foodName.equals("清汤萝卜燕"))     drawNum=9;
    	 	     else if(foodName.equals("健康萝卜糕"))     drawNum=10;
    	 	     else if(foodName.equals("三丝拌蛏"))       drawNum=11;
    	 	     else if(foodName.equals("龙凤酸辣汤"))     drawNum=12;
    	 	     else if(foodName.equals("海南椰子盅"))     drawNum=13;
    	 	     
    	 	     switch(drawNum){
    	 	     	 
    	 	     	 case 0: {
    	 	     	 	  
    	 	     	        displayDish.setNum(0,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 
    	 	     	 case 1: {
    	 	     	 	  
    	 	     	        displayDish.setNum(1,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	       case 2: {
    	 	     	 	  
    	 	     	        displayDish.setNum(2,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 3: {
    	 	     	 	  
    	 	     	        displayDish.setNum(3,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 4: {
    	 	     	 	  
    	 	     	        displayDish.setNum(4,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 5: {
    	 	     	 	  
    	 	     	        displayDish.setNum(5,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 6: {
    	 	     	 	  
    	 	     	        displayDish.setNum(6,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 7: {
    	 	     	 	  
    	 	     	        displayDish.setNum(7,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 8: {
    	 	     	 	  
    	 	     	        displayDish.setNum(8,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 case 9: {
    	 	     	 	  
    	 	     	        displayDish.setNum(11,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 
    	 	     	 case 10: {
    	 	     	 	  
    	 	     	        displayDish.setNum(13,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	       case 11: {
    	 	     	 	  
    	 	     	        displayDish.setNum(9,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	       
    	 	       case 12: {
    	 	     	 	  
    	 	     	        displayDish.setNum(10,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	     	 
    	 	     	 case 13: {
    	 	     	 	  
    	 	     	        displayDish.setNum(12,true);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("画"+foodName);
    	 	     	        break;
    	 	     	 }   
    	 	       default :{
    	 	       	
    	 	       	      displayDish.setNum(11,false);
    	 	     	        displayDish.repaint();
    	 	     	        System.out.println("没有图片了");
    	 	     	 }       
    	 	     
    	 	     
    	 	    }
    	 	     
    	 	     
    	        
    	        
    	        
    	        if(aa==5 || aa==8 || aa==10 || aa==15 || aa==20 || aa==25 ||aa==30)
    	        { 
    	         name.setText("");
    	 	     	     nameC.setText("");
    	 	     	  danjia.setText("");
    	 	     	     danjiaC.setText("");
    	 	     	   
    	 	     	   label_foodName.setText("菜       名:");
    	 	     	  label_caixi.setText("菜       系:");
    	 	     	  label_cookStyle.setText("烹调类型");
	             label_taste.setText("味       道:");
	              label_material.setText("材       料:");
	              label_seson.setText("适宜季节:");
	              label_price.setText("价       格:");
    	 	     	     
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
    	         	
    	         	name.setText("名       称:");
    	 	     	  danjia.setText("单       价:");
    	 	     	  
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
    	  
    	  
    	  //处理点激 点这道菜 按钮事件
    	  else if(ae.getSource()==confirmDish){
    	 		 
    	 		 if(choose<6){
    	 		  
    	 		    int n=JOptionPane.showConfirmDialog(this,"您确认要点这道菜吗?","确认对话框",
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
    	 		 	
    	 		 	  int l=JOptionPane.showConfirmDialog(this,"您确认要点它吗?","确认对话框",
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
    	  
    	  
    	  //处理点激 查看已点菜 按钮事件 
    	  else if(ae.getSource()==checkConfirmedDish){
    	  	
  //  	       detail.setText("您点的菜有:");
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
    	 
    	 
    	 //意见簿窗口
    	 else if(ae.getSource()==suggestion){
    	 	
    	 	    liveMess.setVisible(true);  
    	      liveMess.writeArea.setText("");
    	  
    	 } 
    
       
       //为服务员评分 
       else if(ae.getSource()==servicerButton){
        	
        	 grade.setVisible(true);
    
      }
    
       
       
    
    
    
      //饭店文化介绍
       else if(ae.getSource()==cultureIntro){
       	  
       	   introduceFrame.setVisible(true);
       }	   
     
      //管理员进行修改数据
      else if(ae.getSource()==updateData){
      	 
            Ensure ensure=new Ensure("管理员确认窗口",1);
          	
        
       }	    
    
     
      //管理员进行插入数据
      else if(ae.getSource()==insertData){
           
          Ensure ensure=new Ensure("管理员确认窗口",2);  
          //  insterDataFrame.setVisible(true);
      }      
     
      //管理员进行删除数据
      else if(ae.getSource()==deleteData){
       	
          Ensure ensure=new Ensure("管理员确认窗口",3);  
    //   	    deleteDataFrame.setVisible(true);
      
      } 	    
     
      
      //买单
      else if(ae.getSource()==payBill){
      	
      	   String dish="本次共点的菜有:",writeIn;
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
           
           
           writeIn=dish+" 共计:"+sum+"元"+"       "+year+"年"+month+"月"+day+"日"+hour+"时"
                    +minute+"分"+second+"秒"+"              ";
           
           
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
      
      
      
      //查询用户自定义的菜
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
                
                
                rs=sql.executeQuery("SELECT * FROM cookbook WHERE 菜名 LIKE "+temp); 
            
      //          rs=sql.executeQuery("SELECT * FROM cookbook WHERE 类型 LIKE '%冷%'");
                
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
    	 	     	   
    	 	     	   label_foodName.setText("菜       名:");
    	 	     	  label_caixi.setText("菜       系:");
    	 	     	  label_cookStyle.setText("烹调类型");
	             label_taste.setText("味       道:");
	              label_material.setText("材       料:");
	              label_seson.setText("适宜季节:");
	              label_price.setText("价       格:");
    	 	     	     
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
       
        
      
      //处理点激 about 按钮事件
      else if(ae.getSource()==about){
        
          JOptionPane.showMessageDialog(this,"     版权没有,人权我有. \n      Designer : chenluan",
                                        "消息对话框",JOptionPane.INFORMATION_MESSAGE);
    
    
      }  
    
    
    
    
    }	   	
    
    
    
    public void run(){
    	
 
    	
    	//软件启动线程
    	    try { 
	 	  	
	          Thread.sleep(5000);
	 	      }
	 	      catch(Exception exx) {}
	 	  
	 	      setVisible(true);
	
    
    //时钟线程
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
              
              
              dateLabel.setText(year+"年"+month+"月"+day+"日");
              timeLabel.setText(hour+"时"+minute+"分"+second+"秒");
              
           
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
        
         
         MainFrame stef=new MainFrame("餐厅点菜系统");
    	  Thread gogo=null;
        gogo=new Thread(stef);
    	  gogo.start();
    }
     	    
       
       
       
 }     
 
 
 
 
 
 //粘贴菜品的图片
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
        
            System.out.println("改变位置");
        }
    
    }  
 	  
     

     void setNum(int n,boolean pain){
     	
     	   num=n; 
         paint=pain;
     }

     
}

  
  
  
  //软件启动界面
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
	 	  


//意见簿窗口	 	  
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
	 	  
	 	  ok=new JButton("提交");
	 	  ok.setFont(font);
	 	  ok.setBackground(new Color(104,122,225));
	 	  ok.setForeground(Color.red);
	 	  ok.addActionListener(this);
	 	  
	 	  writeArea=new JTextArea();
	 	  writeArea.setFont(font);
	 	  writeArea.setLineWrap(true);
	 	  
	 	  lab=new JLabel("请您留下宝贵意见",JLabel.CENTER);
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

	 	 	  	   	  
//已点菜的查看窗口
class  CheckConfirmedDishFrame extends JFrame implements ActionListener {
	
	Container     con=null;
	JLabel        lab,titleJLabel,allPrice;
	JButton       delete;
	JPanel        titlePanel,detailPanel;
	JTable        table;
  JScrollPane   scroll;
	JTextField    interNum;
	Object[][]    obj;
	Object[]      ziduan={"序号","菜名","单价"};
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
		  
		  titleJLabel=new JLabel("您点的菜有",JLabel.CENTER);
		  titleJLabel.setFont(fon1);
		  
		  lab=new JLabel("删除序号是");
		  lab.setFont(fon2);
		  
		  allPrice=new JLabel("共计");
		  allPrice.setFont(fon1);
		  
		  delete=new JButton("删除");
		  delete.addActionListener(this);
		  delete.setFont(fon2);
		  
		  interNum=new JTextField(12);
//		  interNum.setFont(fon1);
		  
		  titlePanel=new JPanel();
		  titlePanel.setLayout(new BorderLayout());
		  detailPanel=new JPanel();
		  detailPanel.setLayout(null);
		  
	//	  ziduan={"菜名","单价"};
		  
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
		   System.out.print(number-1+"共循环：");
		   
		   table=new JTable(objw,ziduan);
		   
		   scroll=new JScrollPane(table);
		   
	     
       validate();
   	   
   	   for(int i=0;i<a;i++){
		  	   sumPrice=sumPrice+Integer.parseInt((String)price.get(i)) ;
		   }	  
		   
	    allPrice.setText("共计:"+sumPrice+"元");
   
   
      con.add(scroll);
	    scroll.setBounds(0,41,400,200);
   
   
   }

   public void actionPerformed(ActionEvent aa) {
    	
        
        //判断需要删除的菜的序号是否正确
        
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
		   
		   allPrice.setText("共计:"+sumPrice+"元");
		   
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



//给服务人员评分
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
    	
    	title=new JLabel("为服务人员评分",JLabel.CENTER);
    	person=new JLabel("服务人员:",JLabel.LEFT);
    	name=new JLabel("孙燕姿",JLabel.LEFT);
    	
    	URL  url=getClass().getResource("stef.jpg");
    	photo=new JLabel(new ImageIcon(url));
    	attitudeLabel=new JLabel("服务态度:",JLabel.LEFT);
    	qualityLabel=new JLabel("服务质量:",JLabel.LEFT);
    	satisfyLabel=new JLabel("满意程度:",JLabel.LEFT);
    	               
      attitude=new CheckboxGroup();
      quality=new CheckboxGroup();
      satisfy=new CheckboxGroup();
      
      attitude1=new Checkbox("热情",true,attitude);
      attitude2=new Checkbox("一般",false,attitude);
      attitude3=new Checkbox("很差",false,attitude);
      quality1=new Checkbox("上乘",true,quality);
      quality2=new Checkbox("一般",false,quality);
      quality3=new Checkbox("很差",false,quality);
      satisfy1=new Checkbox("满意",true,satisfy);
      satisfy2=new Checkbox("一般",false,satisfy);
      satisfy3=new Checkbox("很差",false,satisfy);
      
      ok=new JButton("提交");
      ok.addActionListener(this);
      cancel=new JButton("取消");
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
     	    	 if(i==0)        score="服务人员:"+name.getText()+"  ";
     	    	 else if(i==1) {
     	    	 	   if(attitude1.getState())   score=score+"服务态度:"+"热情"+"  ";
                 if(attitude2.getState())   score=score+"服务态度:"+"一般"+"  ";
     	    	     if(attitude3.getState())   score=score+"服务态度:"+"很差"+"  ";
     	       }		  
             else if(i==2) {
           	     if(quality1.getState())   score=score+"服务质量:"+"上乘"+"  ";
                 if(quality2.getState())   score=score+"服务质量:"+"一般"+"  ";
                 if(quality3.getState())   score=score+"服务质量:"+"很差"+"  ";
             }
             else if(i==3) {
             	   if(satisfy1.getState())   score=score+"满意程度:"+"满意"+"  ";    
                 if(satisfy2.getState())   score=score+"满意程度:"+"一般"+"  ";
                 if(satisfy3.getState())   score=score+"满意程度:"+"很差"+"  ";
             
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





//饭店介绍面板
class IntroducePane extends JPanel{
	  Font fon;
	  public void paintComponent(Graphics g){
	  	  super.paintComponent(g);
	  	  
	  	  fon=new Font("Dialog",Font.PLAIN,18);
	  	  g.setFont(fon);
	  	  g.drawString("饭    店    文    化",230,30);
	      g.drawString("中国历史悠久,其烹饪艺术渊远流长,闻名于世.中餐以它的色,香,味形而诱人.",20,70);
	      g.drawString("长期以来由于各地区的自然环境,文化,风俗,习惯不同,中国菜肴形成了不同风",2,90);
	      g.drawString("味的地方菜系.本饭店的烹饪则集各地不同风味之大全.北方菜系以山东口味为",2,110);
	      g.drawString("主流,擅长烹制海味.四川厨师烹调爱放干,鲜辣椒,川菜以麻辣辛香调料而闻名.",2,130);
	      g.drawString("粤菜取料广泛,他们讲究鲜嫩和酥脆.淮扬菜注重原汁原味,特别是油而不腻,清",2,150);
        g.drawString("淡鲜美.",2,170);
        g.drawString("地理位置:",2,220);
	      g.drawString("免费订餐电话:  87654321",2,250);
	      
	  }
}






//饭店介绍的窗口
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









//管理员修改数据窗口
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
	 	  		  
	 	  		  needUpdateName=new JLabel("请输入需要修改的菜名");
	 	  		  needUpdateName.setFont(fon1);
	 	  		  updateStyle=new JLabel("请输入修改后的类型");
	 	  		  updateStyle.setFont(fon1);
	 	  		  updateName=new JLabel("请输入修改后的菜名");
	 	  		  updateName.setFont(fon1);
	 	  		  updateCaiXi=new JLabel("请输入修改后的菜系");
	 	  		  updateCaiXi.setFont(fon1);
	 	  		  
	 	  		  updateCook=new JLabel("请输入修改后的烹调类型");
	 	  	    updateCook.setFont(fon1);
	 	  	    updateSmell=new JLabel("请输入修改后的味道");
	 	  	    updateSmell.setFont(fon1);
	 	  	    updateMaterial=new JLabel("请输入修改后的材料");
	 	  	    updateMaterial.setFont(fon1);
	 	  	    updateSeson=new JLabel("请输入修改后的适宜季节");
	 	  	    updateSeson.setFont(fon1);
	 	  	    updatePrice =new JLabel("请输入修改后的价格");     
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
	 	  	    
	 	  	    ok=new JButton("提交");
	 	  	    ok.setFont(fon2);
	 	  	    ok.addActionListener(this);
	 	  	    cancel=new JButton("取消");
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
        	 	  
        	 	 int num=JOptionPane.showConfirmDialog(this,"确认要对源数据库进行修改吗?",
        	 	                                        "确认对话框",JOptionPane.YES_NO_OPTION);
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
        	 	 
    
       	  
       	      temp="UPDATE cookbook SET 类型=" +s2+",菜名="+s3+" , 菜系=" +s4+ " , 烹调类型=" +s5
       	           + " , 味道=" +s6+ " , 材料类型=" +s7+ " , 适宜季节=" +s8+" , 价格=" +s9+
       	            " WHERE 菜名="+s1;
       	      
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
	 	  	    
	 	  	    
	
//	管理员插入数据窗口
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
	 	  		  
	 	  		  interName=new JLabel("请输入需要插入的菜名");
	 	  		  interName.setFont(fon1);
	 	  		  interStyle=new JLabel("请输入需要插入的类型");
	 	  		  interStyle.setFont(fon1);
	 	  		  interCaiXi=new JLabel("请输入需要插入的菜系");
	 	  		  interCaiXi.setFont(fon1);
	 	  		  interCook=new JLabel("请输入需要插入的烹调类型");
	 	  	    interCook.setFont(fon1);
	 	  	    interSmell=new JLabel("请输入需要插入的味道");
	 	  	    interSmell.setFont(fon1);
	 	  	    interMaterial=new JLabel("请输入需要插入的材料");
	 	  	    interMaterial.setFont(fon1);
	 	  	    interSeson=new JLabel("请输入需要插入的适宜季节");
	 	  	    interSeson.setFont(fon1);
	 	  	    interPrice =new JLabel("请输入需要插入的价格");     
	 	  	    interPrice.setFont(fon1);
	 	  	    
	 	  	    
	 	  	    interNameText=new JTextField(100);
	 	  	    interStyleText=new JTextField(100);
	 	  	    interCaiXiText=new JTextField(100);
	 	  	    interCookText=new JTextField(100);
	 	  	    interSmellText=new JTextField(100);
	 	  	    interMaterialText=new JTextField(100);
	 	  	    interSesonText=new JTextField(100);
	 	  	    interPriceText=new JTextField(100);
	 	  	    
	 	  	    ok=new JButton("提交");
	 	  	    ok.addActionListener(this);
	 	  	    ok.setFont(fon2);
	 	  	    cancel=new JButton("取消");
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
        	    	
        	    	 int num=JOptionPane.showConfirmDialog(this,"确认要对源数据库进行修改吗?",
        	 	                                        "确认对话框",JOptionPane.YES_NO_OPTION);
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


// 	管理员删除数据窗口	
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
	    	  
	    	  dele=new JLabel("请输入需要删除的菜名");
	    	  dele.setFont(fon1);
	    	  deleText=new JTextField(100);
	    	  ok=new JButton("提交");
 	  	    ok.setFont(fon2);
 	  	    ok.addActionListener(this);
	 	  	  cancel=new JButton("取消");
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
           	
           	
           	int num=JOptionPane.showConfirmDialog(this,"您确认要删除此条记录吗?",
        	 	                                        "确认对话框",JOptionPane.YES_NO_OPTION);
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
                
               
                 int aas=sql.executeUpdate("DELETE FROM cookbook WHERE 菜名= "+temp);
               
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




//管理员确认窗口
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
          
          title=new JLabel("请输入管理员密码");
          title.setFont(new Font("Dialog",Font.PLAIN,16));
          pass=new TextField(10);
          pass.setEchoChar('*');
          
          ok=new JButton("确认");
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
          	JLabel welcome=new JLabel("欢迎管理员进入");
            welcome.setFont(new Font("Dialog",Font.PLAIN,18));
            pan.add(welcome);
            welcome.setBounds(30,40,140,40);
            waitting=new Thread(this);
            waitting.start();
            
            num=1;
          
            
          
          }
          else {
          	
          	pan.removeAll();
          	JLabel error=new JLabel("密码有误");
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
	 	     	        updateDataFrame=new UpdateDataFrame("修改数据");
	 	              updateDataFrame.setVisible(true);
	 	     
	 	          }    
	 	          else if(frameNum==2){
	 	     	        insterDataFrame=new InsterDataFrame("插入数据");
	 	     	        insterDataFrame.setVisible(true);
	 	          }	
	 	          else if(frameNum==3){
	 	     	         deleteDataFrame=new DeleteDataFrame("删除数据");
	 	     	         deleteDataFrame.setVisible(true);
	 	     	
	 	          }	 
	 	   
	 	       }
	 	  
	 	  
	 	   }
	 	  
	 	 
	
}	 	     	                          	      