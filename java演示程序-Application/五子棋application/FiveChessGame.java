import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

 


class FiveChessGame extends Frame implements MouseListener,ActionListener,Runnable
{
   int              x=-1,y=-1,isStart=0,
                    颜色=1,blackTimeOver=0,whiteTimeOver=0,
                    chess_blackNum=0,                   chess_whiteNum=0,
                    rightBlackUpNum=0,                  rightWhiteUpNum=0,
                    rightBlackDownNum=0,                rightWhiteDownNum=0,
                    rightBlackLeftNum=0,                rightWhiteLeftNum=0,
                    rightBlackRightNum=0,               rightWhiteRightNum=0,
                    rightBlackRightInclinedUpNum=0,     rightWhiteRightInclinedUpNum=0,
                    rightBlackLeftInclinedDownNum=0,    rightWhiteLeftInclinedDownNum=0,
                    rightBlackLeftInclinedUpNum=0,      rightWhiteLeftInclinedUpNum=0,
                    rightBlackRightInclinedDownNum=0,   rightWhiteRightInclinedDownNum=0;
                
   MenuBar          menuBar;
   Menu             game,choose,help;
   MenuItem         start,quit,open,save,backgroundColor,usingTime,about;
   TextField        textField1,textField2,time1,time2;
   Button           button;
   Chess_Black      chessBlack[]=new Chess_Black[100];
   Chess_White      chessWhite[]=new Chess_White[100];
   Thread           TimeThread=null;
   Label            label1,label2;
   Color            background;
   MyDialog         dialog;
   boolean          timeIsSet=false;
   BufferedReader   in;
   FileReader       fileReader;
   BufferedWriter   out;
   FileWriter       fileWriter;
   FileDialog       filedialog_save,filedialog_open;






   FiveChessGame(String s)        //  棋盘类的构造
   {
      super(s);
      setSize(560,560);
      setLayout(null);
      setBackground(Color.pink);
      addMouseListener(this);
      setResizable(false);
      setVisible(true);
      validate();
      addWindowListener(new WindowAdapter()
                            {  public void windowClosing(WindowEvent e)
                               {
                                  System.exit(0);
                               }
                            }
                       );      //窗口适配器

      menuBar=new MenuBar();
      setMenuBar(menuBar);
      
      game=new Menu("开始");
      
      choose=new Menu("选项"); 
      help=new Menu("帮助"); 
  
      start=new MenuItem("重新开局");
      start.addActionListener(this);      
      save=new MenuItem("保存游戏");      
      save.addActionListener(this);
      open=new MenuItem("打开游戏");      
      open.addActionListener(this);
      quit=new  MenuItem("退出游戏");
      quit.addActionListener(this);
 
      
      usingTime=new MenuItem("比赛时间");
      usingTime.addActionListener(this); 
      backgroundColor=new MenuItem("背景颜色");
      backgroundColor.addActionListener(this); 


      about=new MenuItem("关于");
      about.addActionListener(this);      

      game.add(start);
      game.addSeparator();
      game.add(save);
      game.addSeparator();
      game.add(open);     
      game.addSeparator();
      game.add(quit);
      choose.add(usingTime);
      choose.addSeparator();
      choose.add(backgroundColor);
      help.add(about);

      menuBar.add(game);
      menuBar.add(choose);
      menuBar.add(help);
    
     
      button=new Button("悔棋");   
      button.setBounds(80,50,40,18);
      Cursor c=new Cursor(Cursor.HAND_CURSOR);
      button.setCursor(c);
      button.addActionListener(this);
                
      
      textField1=new TextField("请黑方走棋");
      textField1.setBounds(160,50,80,18);
      textField2=new TextField("");
      textField2.setBounds(310,50,80,18);
      time1=new  TextField("10:59");
      time1.setBounds(80,472,50,18);    
      time2=new TextField("10:59");
      time2.setBounds(370,472,50,18);

      textField1.setEditable(false);
      textField2.setEditable(false);
      time1.setEditable(false);
      time2.setEditable(false);

 
      label1=new Label("黑方计时");
      label2=new Label("白方计时");
      label1.setBounds(80,450,52,18);
      label2.setBounds(370,450,52,18);

     
      dialog=new MyDialog("比赛时间");     //创建一个修改比赛时间的类   


      add(button);
      add(textField1); 
      add(textField2);
      add(time1);
      add(time2);
      add(label1);
      add(label2);
      
 
      TimeThread=new Thread(this);       // 比赛记时的线程


      filedialog_save=new FileDialog(this,"保存文件对话框",FileDialog.SAVE);
      filedialog_open=new FileDialog(this,"打开文件对话框",FileDialog.LOAD);      

      filedialog_save.addWindowListener(new WindowAdapter()
                                        {
                                           public void windowClosing(WindowEvent e)
                                           {
                                               filedialog_save.setVisible(false);
                                           }
                                         });   //保存对话框适配器

      

      filedialog_open.addWindowListener(new WindowAdapter()
                                        {
                                           public void windowClosing(WindowEvent e)
                                           {
                                               filedialog_open.setVisible(false);
                                           }
                                         });    //打开对话框适配器



   }
      
  
   public void paint(Graphics g)        // 画出整个棋盘
   { 
      for(int i=80;i<=420;i=i+20)
      {
         g.drawLine(80,i,420,i);
      }
   
      g.drawLine(80,420,420,420);

      for(int i=80;i<=420;i=i+20)
      {
         g.drawLine(i,80,i,420);
      }
   
      g.drawLine(420,80,420,420);

      g.fillOval(137,137,6,6);
      g.fillOval(357,137,6,6);
      g.fillOval(137,357,6,6);
      g.fillOval(357,357,6,6);
      g.fillOval(237,237,6,6);

   }
    
 
   public void mousePressed(MouseEvent e)
   {
      if(isStart==1)
      {

        if(timeIsSet==true)      // 如果比赛时间被修改，则~~~~~
        {
            timeIsSet=false;              
        }



        if(e.getModifiers()==InputEvent.BUTTON1_MASK)
        {
           Chess_Black  chess_black=new Chess_Black();
           Chess_White  chess_white=new Chess_White();          

           
           // 得到当前鼠标的位置
           x=(int)e.getX();
           y=(int)e.getY();
           int a=(x+10)/20;
           int b=(y+10)/20;
 
           if( x<80 || x>420 || y<80 || y>420 )       //  如果在棋盘之外      
           {}
  
           else 
           {
              if(颜色==1)
              {   
                  this.add(chess_black);
                  chess_black.setBounds(a*20-7,b*20-7,18,18);
                  
                  chessBlack[chess_blackNum]=new Chess_Black();
                  chessBlack[chess_blackNum]=chess_black;
                  
                  for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_blackNum;j++)
                      {
                           if((int)(chessBlack[j].getBounds().x)==a*20-7 && (int)(chessBlack[j].getBounds().y)==b*20-7-(i+1)*20)
                           { 
                                rightBlackUpNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightBlackUpNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的上面是否有 >=4 颗棋子

                  

                   for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_blackNum;j++)
                      {
                           if((int)(chessBlack[j].getBounds().x)==a*20-7 && (int)(chessBlack[j].getBounds().y)==b*20-7+(i+1)*20)
                           { 
                                rightBlackDownNum++;
                                continue;
                           }
                           
                       }
                       
                       if(rightBlackDownNum<=i)
                       break;
                   
                   }           // 走的这颗棋子的上面是否有 >=4 颗棋子      


                   for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_blackNum;j++)
                      {
                           if((int)(chessBlack[j].getBounds().x)==a*20-7-(i+1)*20 && (int)(chessBlack[j].getBounds().y)==b*20-7)
                           { 
                                rightBlackLeftNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightBlackLeftNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的左面是否有 >=4 颗棋子

                  
                  for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_blackNum;j++)
                      {
                           if((int)(chessBlack[j].getBounds().x)==a*20-7+(i+1)*20 && (int)(chessBlack[j].getBounds().y)==b*20-7)
                           { 
                                rightBlackRightNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightBlackRightNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的右面是否有 >=4 颗棋子
                  
                   
                  for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_blackNum;j++)
                      {
                           if((int)(chessBlack[j].getBounds().x)==a*20-7+(i+1)*20 && (int)(chessBlack[j].getBounds().y)==b*20-7-(i+1)*20)
                           { 
                                rightBlackRightInclinedUpNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightBlackRightInclinedUpNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的右斜上面是否有 >=4 颗棋子


                   
                  for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_blackNum;j++)
                      {
                           if((int)(chessBlack[j].getBounds().x)==a*20-7-(i+1)*20 && (int)(chessBlack[j].getBounds().y)==b*20-7+(i+1)*20)
                           { 
                                rightBlackLeftInclinedDownNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightBlackLeftInclinedDownNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的左斜下面是否有 >=4 颗棋子



                  for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_blackNum;j++)
                      {
                           if((int)(chessBlack[j].getBounds().x)==a*20-7-(i+1)*20 && (int)(chessBlack[j].getBounds().y)==b*20-7-(i+1)*20)
                           { 
                                rightBlackLeftInclinedUpNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightBlackLeftInclinedUpNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的左斜上面是否有 >=4 颗棋子



                  for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_blackNum;j++)
                      {
                           if((int)(chessBlack[j].getBounds().x)==a*20-7+(i+1)*20 && (int)(chessBlack[j].getBounds().y)==b*20-7+(i+1)*20)
                           { 
                                rightBlackRightInclinedDownNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightBlackRightInclinedDownNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的右斜下面是否有 >=4 颗棋子





                   if(rightBlackUpNum+rightBlackDownNum>=4 || rightBlackLeftNum+rightBlackRightNum>=4 || 
                      rightBlackRightInclinedUpNum+rightBlackLeftInclinedDownNum>=4 ||                                                                                    rightBlackLeftInclinedUpNum+rightBlackRightInclinedDownNum>=4  )
                   {  
                        
                      TimeThread.interrupt();      
                      JOptionPane.showMessageDialog(this,"黑方获胜","比赛结果",JOptionPane.INFORMATION_MESSAGE);
                      颜色=0;
                      chess_blackNum++;
                   }


                   else
                   {
                      颜色=颜色*(-1);
                      textField1.setText("");
                      textField2.setText("请白方走棋");
                      chess_blackNum++;
                      rightBlackUpNum=0;
                      rightBlackDownNum=0;
                      rightBlackLeftNum=0;
                      rightBlackRightNum=0;
                      rightBlackRightInclinedUpNum=0;
                      rightBlackLeftInclinedDownNum=0;
                      rightBlackLeftInclinedUpNum=0;
                      rightBlackRightInclinedDownNum=0;
               
                      

                   }
                   
                   

              }

              else if(颜色==-1)
              {
                  this.add(chess_white);
                  chess_white.setBounds(a*20-7,b*20-7,18,18);
                  
                  chessWhite[chess_whiteNum]=new Chess_White();
                  chessWhite[chess_whiteNum]=chess_white;
                  

                 
                  for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_whiteNum;j++)
                      {
                           if((int)(chessWhite[j].getBounds().x)==a*20-7 && (int)(chessWhite[j].getBounds().y)==b*20-7-(i+1)*20)
                           { 
                                rightWhiteUpNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightWhiteUpNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的上面是否有 >=4 颗棋子

                  

                   for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_whiteNum;j++)
                      {
                           if((int)(chessWhite[j].getBounds().x)==a*20-7 && (int)(chessWhite[j].getBounds().y)==b*20-7+(i+1)*20)
                           { 
                                rightWhiteDownNum++;
                                continue;
                           }
                           
                       }
                       
                       if(rightWhiteDownNum<=i)
                       break;
                   
                   }           // 走的这颗棋子的上面是否有 >=4 颗棋子      


                   for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_whiteNum;j++)
                      {
                           if((int)(chessWhite[j].getBounds().x)==a*20-7-(i+1)*20 && (int)(chessWhite[j].getBounds().y)==b*20-7)
                           { 
                                rightWhiteLeftNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightWhiteLeftNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的左面是否有 >=4 颗棋子

                  
                  for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_whiteNum;j++)
                      {
                           if((int)(chessWhite[j].getBounds().x)==a*20-7+(i+1)*20 && (int)(chessWhite[j].getBounds().y)==b*20-7)
                           { 
                                rightWhiteRightNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightWhiteRightNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的右面是否有 >=4 颗棋子
                  
                   
                  for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_whiteNum;j++)
                      {
                           if((int)(chessWhite[j].getBounds().x)==a*20-7+(i+1)*20 && (int)(chessWhite[j].getBounds().y)==b*20-7-(i+1)*20)
                           { 
                                rightWhiteRightInclinedUpNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightWhiteRightInclinedUpNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的右斜上面是否有 >=4 颗棋子


                   
                  for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_whiteNum;j++)
                      {
                           if((int)(chessWhite[j].getBounds().x)==a*20-7-(i+1)*20 && (int)(chessWhite[j].getBounds().y)==b*20-7+(i+1)*20)
                           { 
                                rightWhiteLeftInclinedDownNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightWhiteLeftInclinedDownNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的左斜下面是否有 >=4 颗棋子



                  for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_whiteNum;j++)
                      {
                           if((int)(chessWhite[j].getBounds().x)==a*20-7-(i+1)*20 && (int)(chessWhite[j].getBounds().y)==b*20-7-(i+1)*20)
                           { 
                                rightWhiteLeftInclinedUpNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightWhiteLeftInclinedUpNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的左斜上面是否有 >=4 颗棋子



                  for(int i=0;i<4;i++)
                  {
                      for(int j=0;j<chess_whiteNum;j++)
                      {
                           if((int)(chessWhite[j].getBounds().x)==a*20-7+(i+1)*20 && (int)(chessWhite[j].getBounds().y)==b*20-7+(i+1)*20)
                           { 
                                rightWhiteRightInclinedDownNum++;
                                continue;
                           }
                           
                      
                       }
                       
                       if(rightWhiteRightInclinedDownNum<=i)
                       break;
                   
                  }        // 走的这颗棋子的右斜下面是否有 >=4 颗棋子





                   if(rightWhiteUpNum+rightWhiteDownNum>=4 || rightWhiteLeftNum+rightWhiteRightNum>=4 || 
                      rightWhiteRightInclinedUpNum+rightWhiteLeftInclinedDownNum>=4 ||                                                                                    rightWhiteLeftInclinedUpNum+rightWhiteRightInclinedDownNum>=4  )
                   {  
                        
                      TimeThread.interrupt();     
                      JOptionPane.showMessageDialog(this,"白方获胜","比赛结果",JOptionPane.INFORMATION_MESSAGE);
                      颜色=0;
                      chess_whiteNum++;
                   }


                   else
                   {
                      颜色=颜色*(-1);
                      textField1.setText("请黑方走棋");
                      textField2.setText("");
                      chess_whiteNum++;
                      rightWhiteUpNum=0;
                      rightWhiteDownNum=0;
                      rightWhiteLeftNum=0;
                      rightWhiteRightNum=0;
                      rightWhiteRightInclinedUpNum=0;
                      rightWhiteLeftInclinedDownNum=0;
                      rightWhiteLeftInclinedUpNum=0;
                      rightWhiteRightInclinedDownNum=0;

                   }
                   
                   

              }

           }

        } 
     
     }   
   
   }           

   public void mouseReleased(MouseEvent e)  {}
   public void mouseEntered(MouseEvent e)   {}
   public void mouseExited(MouseEvent e)    {}
   public void mouseClicked(MouseEvent e)   {}


   

   public void run()
   {
      if(Thread.currentThread()==TimeThread)
      {
        
 
          while(true)
          {
             
              if(颜色>0)                 
              {

                 StringTokenizer fenxi=new StringTokenizer(time1.getText(),":");
             
                 String a[]=new String[2];
             
                 for(int i=0;i<2;i++)
                 {
                    a[i]=fenxi.nextToken();
                 }  
                            
                 int minute=Integer.parseInt(a[0]);
                 int second=Integer.parseInt(a[1]);
 
                 if(second==0)  
                 {
                     second=59;
                     minute--;
                   
                     if(minute==-1)
                     {
                         minute=0;
                         second=0;             
                     
                     } 

                 }
 
                 else           
                 {
                   second--;
                 }
 
                 time1.setText(""+minute+":"+second);

                 try
                 { 
                    TimeThread.sleep(900);
                 }
 
                 catch(InterruptedException e)                   
                 {
                     blackTimeOver=1;    //黑方超时
                     return;
                 }


                 if(minute==0 && second==0) 
                 {
                     JOptionPane.showMessageDialog(this,"黑方超时,白方获胜","比赛结果",JOptionPane.INFORMATION_MESSAGE);
                     颜色=0;
                     TimeThread.interrupt();
                    
                 }

            
                 
                                      
                 

             }  
        

         
             else        
             {     
                   
                  StringTokenizer fenxi=new StringTokenizer(time2.getText(),":");
            
                  String a[]=new String[2];
             
                  for(int i=0;i<2;i++)
                  {
                      a[i]=fenxi.nextToken();
                  }  
                            
                  int minute=Integer.parseInt(a[0]);
                  int second=Integer.parseInt(a[1]);
 
                  if(second==0)  
                  {
                     second=59;
                     minute--;
                     
                     if(minute==-1)
                     {
                        minute=0;             
                        second=0;
                     }             
      
                  } 

                  else           
                  {
                     second--;
                  }
 
                  time2.setText(""+minute+":"+second);

                  try
                  { 
                      TimeThread.sleep(900);
                  }
 
                  catch(InterruptedException e)                   
                  {
                       whiteTimeOver=1;       //白方超时
                       return;
                  }


                  if(minute==0 && second==0) 
                  {
                      JOptionPane.showMessageDialog(this,"白方超时,黑方获胜","比赛结果",JOptionPane.INFORMATION_MESSAGE);
                      颜色=0;
                      TimeThread.interrupt();
                  
                  }

            
               
             }    

         
          }

      }

   
   }





   public void actionPerformed(ActionEvent e)
   {
       
       if(e.getSource()==usingTime)    //修改时间
       {
             
             dialog.setVisible(true);

             timeIsSet=true;

       } 



       else if(e.getSource()==start)    //比赛重新开始
       {
           isStart=1;
           

           this.removeAll();
           
           颜色=1;
           
           blackTimeOver=0;
           whiteTimeOver=0;
  
           chess_blackNum=0;
           chess_whiteNum=0;
           
           rightBlackUpNum=0;
           rightBlackDownNum=0;
           rightBlackLeftNum=0;
           rightBlackRightNum=0;
           rightBlackRightInclinedUpNum=0;
           rightBlackLeftInclinedDownNum=0;
           rightBlackLeftInclinedUpNum=0;           
           rightBlackRightInclinedDownNum=0;

           rightWhiteUpNum=0;
           rightWhiteDownNum=0;
           rightWhiteLeftNum=0;
           rightWhiteRightNum=0;
           rightWhiteRightInclinedUpNum=0;
           rightWhiteLeftInclinedDownNum=0;
           rightWhiteLeftInclinedUpNum=0;
           rightWhiteRightInclinedDownNum=0;


           button=new Button("悔棋");   
           button.setBounds(80,50,40,18);
           button.addActionListener(this);
           Cursor c=new Cursor(Cursor.HAND_CURSOR);
           button.setCursor(c);

  
           textField1=new TextField("请黑方走棋");
           textField1.setBounds(160,50,80,18);

           textField2=new TextField("");
           textField2.setBounds(310,50,80,18);
     

           
           if(timeIsSet==true)
           {
               time1=new  TextField();
               time1.setBounds(80,472,50,18);
               time2=new  TextField();
               time2.setBounds(370,472,50,18);

               time1.setText(dialog.get()+":"+59);
               time2.setText(dialog.get()+":"+59);  
            
               add(time1);
               add(time2); 


           }
           
           else
           {
                time1=new  TextField("10:59");
                time1.setBounds(80,472,50,18);      
                time2=new TextField("10:59");
                time2.setBounds(370,472,50,18);

                add(time1);
                add(time2); 

           }

           

           if(TimeThread.isAlive())
           {
               TimeThread.interrupt();
           }   

           TimeThread=new Thread(this);     
           TimeThread.start();





           textField1.setEditable(false);
           textField2.setEditable(false);
           time1.setEditable(false);
           time2.setEditable(false);

           label1=new Label("黑方计时");
           label2=new Label("白方计时");
           label1.setBounds(80,450,52,18);
           label2.setBounds(370,450,52,18);


           dialog=new MyDialog("比赛时间"); 

           add(button);
           add(textField1);
           add(textField2);
           add(label1);
           add(label2);
           
           



       }

    
       else if(e.getSource()==quit)    //退出游戏
       {
           System.exit(0);
       }

       


       else if(e.getSource()==save)      //保存游戏
       {
            filedialog_save.setVisible(true);
            
            if(filedialog_save.getFile()!=null)
            {
                try
                {
                   File file=new File(filedialog_save.getDirectory(),filedialog_save.getFile());
                   fileWriter=new FileWriter(file);
                   out=new BufferedWriter(fileWriter);
                   
                   String b=new String("");
                   String w=new String("");





                   for(int i=0;i<chess_blackNum;i++)
                   {
                       String  tempBlack=new String(""+chessBlack[i].getBounds().x+"#"+chessBlack[i].getBounds().y+"#");
                       b=b+tempBlack;
                   }


                   for(int j=0;j<chess_whiteNum;j++)
                   {
                       String  tempWhite=new String(""+chessWhite[j].getBounds().x+"#"+chessWhite[j].getBounds().y+"#");      
                       w=w+tempWhite;
                   } 
                   

                   String all=new String(b+w);

                   out.write(all,0,all.length() );
        
                   out.flush();
                   out.close();
                   fileWriter.close();
       
                }

                catch(IOException e2)
                {}

            }


       }
                    


       else if(e.getSource()==open)          //载入以保存的游戏
       {
           filedialog_open.setVisible(true);

           try
           {
               File file=new File(filedialog_open.getDirectory(),filedialog_open.getFile());
               fileReader=new FileReader(file);

               int   len=(int)file.length();
               int   n;
               char  ch[]=new char[len];
           
               while( (n=fileReader.read(ch,0,len))!=-1)
               {
                   String s=new String(ch,0,n);
            
                   StringTokenizer fenxi=new StringTokenizer(s,"#");
                   int num=fenxi.countTokens();
              
                   int i=0;
              
                   String a=new String("");
                   String b=new String("");
              
                   颜色=1;
              
                   while(fenxi.hasMoreTokens())
                   {
                       i++;
                                 
                       if(i%2==0)
                       {
                           b=fenxi.nextToken();
                    
                    
                           int x=Integer.parseInt(a);
                           int y=Integer.parseInt(b);

                           if(颜色==1)
                           {
                               Chess_Black black=new Chess_Black();
                               this.add(black);
                               black.setBounds(x,y,18,18);
                        
                               颜色=颜色*(-1);
                           }

                           else if(颜色==-1)
                          {
                               Chess_White white=new Chess_White();
                               this.add(white);
                               white.setBounds(x,y,18,18);
                     
                               颜色=颜色*(-1);
                          }

                       }
                       else
                      {
                           a=fenxi.nextToken();
                      }
          
 
                   }                                             
           
               
                }

            }
 
            catch(IOException e1)
            {}      



      }


       else if(e.getSource()==button)        //  悔棋
       {
           if( 颜色== 1)
           {
                remove(chessWhite[chess_whiteNum-1]);
                颜色=-1;
                textField2.setText("请白方走棋");
                textField1.setText("");
           }   
           

           else if( 颜色== -1)
           {
               remove(chessBlack[chess_blackNum-1]);
               颜色=1;
               textField1.setText("请黑方走棋");
               textField2.setText("");
           }

 
       }

           
       else if(e.getSource()==backgroundColor)     // 设置棋盘背景色
       {
            background=JColorChooser.showDialog(this,"调色板",Color.pink);
            setBackground(background);
        
       }

     

       else if(e.getSource()==about)     // 关于软件
       {
           JOptionPane.showMessageDialog(this,"Designer: chenluan \n"+"E_Mail: luanch302@163.com","关于",           JOptionPane.INFORMATION_MESSAGE);



       }       
       




    }

    


    public static void main(String args[])
    {
       FiveChessGame  chessPad=new FiveChessGame("快乐五子棋");
    
    }

    
    

}





class Chess_Black extends Canvas       // 黑色棋子的创建
{

   
    Chess_Black()
    {
        setSize(20,20);
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.fillOval(0,0,14,14);
    }
 
  
    
}


class Chess_White extends Canvas     // 白色棋子的创建
{
    Chess_White()
    {
        setSize(20,20);
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.white);
        g.fillOval(0,0,14,14);
    }

}



class MyDialog extends Frame  implements ActionListener     //  修改比赛时间
{
    Label        label;
    TextField    setTime;
    String       a;
    Button       button;
    boolean      haveValue=false;
    


    MyDialog(String s)
    {
        super(s);
        
                




        label=new Label("分钟"+"\n");
        label.setSize(25,20);
        
        setTime=new TextField("10");
        setTime.addActionListener(this); 
        
        button=new Button("确认");       
        button.addActionListener(this);       
 
        FlowLayout flow=new FlowLayout();
        setLayout(flow);        
        
        setBackground(Color.cyan);

        setBounds(200,100,200,80);        
        setResizable(false);
        setVisible(false);
        validate();

        add(setTime);
        add(label);
        add(button);
        
        haveValue=true;        
 
        addWindowListener(new WindowAdapter()
                              {
                                 public void windowClosing(WindowEvent e)
                                 {
                                    setTime.setText("");
                                    setVisible(false);
                                 }
                              }
                           );

       
 
     }


     public void  actionPerformed(ActionEvent e)
     {
        if( e.getSource()==setTime || e.getSource()==button)
        {
             
             a=setTime.getText();
            
             

        }
     }   

     public String get()
     {
         return a;
     }
     

    

}

 


 

