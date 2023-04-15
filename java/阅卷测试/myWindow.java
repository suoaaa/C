package 阅卷测试;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;

public class myWindow {
    JFrame win=new JFrame("简易阅卷系统");              //构建窗口以及为窗口命名
    JTextField  stem = new JTextField(40);           //题目
    int i=1;                                                  //作为记录，目前显示的第几题
    JComboBox < String > cBox=new JComboBox< String >();
    JLabel ti=new JLabel();                                     //倒计时显示
    int remainedTime=60*8*1000;                                 //剩余时间
    
    JButton nextbt = new JButton("下个题目");               //菜单按钮
    JButton lastbt = new JButton("上个题目");
    JButton practiceAgainbt = new JButton("重新开始");
    JButton startbt = new JButton("开始");
    JButton finishbt = new JButton("结束答题");

    ButtonGroup g=new ButtonGroup();                            //三个选项作为整体加入Buttongroup g
    JRadioButton Ba=new JRadioButton("A");                 //构建单选作为选项
    JRadioButton Bb=new JRadioButton("B");
    JRadioButton Bc=new JRadioButton("C");

    JTextField t=new JTextField(20);            //填空题填空处
    JPanel p0 = new JPanel();                           //选择题号
    JPanel p1 = new JPanel();                           //题目行
    JPanel p2 = new JPanel();                           //填空题答案行
    JPanel pa = new JPanel();                           //选项A行
    JPanel pb = new JPanel();                           //选项B行 
    JPanel pc = new JPanel();                           //选项C行 
    JPanel p5 = new JPanel();                           //界面控制按钮行
    JLabel la,lb,lc;                                    //记录ABC选项具体内容

    questions q;                                        //questions类作为成员储存题目信息
    Timer endTimer=new Timer();                         //用于计时，8分钟内答题
    boolean b=false;                                    //检测是否打印过答题情况

    myWindow(questions q){                              //UI初始化并且增加对按钮的监听
        this.q=q;
        for(int i=0;i<8;i++) cBox.addItem(""+(i+1));

        p2.setVisible(false);
        p2.setSize(30, 30);
        g.add(Ba);      g.add(Bb);      g.add(Bc);
        p0.add(new JLabel("当前题号"));         p0.add(cBox);   
        p0.add(new JLabel("                 倒计时："));       p0.add(ti);
        p0.setLayout(new FlowLayout(FlowLayout.LEFT));
        p1.add(stem);   p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        p2.add(t);      p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        pa.add(Ba);     pa.setLayout(new FlowLayout(FlowLayout.LEFT));
        pb.add(Bb);     pb.setLayout(new FlowLayout(FlowLayout.LEFT));
        pc.add(Bc);     pc.setLayout(new FlowLayout(FlowLayout.LEFT));
        p5.add(lastbt); p5.add(nextbt); p5.add(practiceAgainbt);    p5.add(finishbt);
        
        win.add(startbt);
        win.setSize(400,450);
        win.setLocationRelativeTo(null);
        win.setVisible(true);
        win.setLayout(new GridLayout(7,1,10,10));

        startbt.addActionListener(new ActionListener() {                //开始按钮初始化，将元素引入win成员并开始计时
            public void actionPerformed(ActionEvent e){
                startCounting();
                win.remove(startbt);
                win.add(p0);    win.add(p1);    win.add(p2); win.add(pa);win.add(pb);     win.add(pc); win.add(p5);
                fillQuestion();
                update();
            }});

        lastbt.addActionListener(new ActionListener() {                 //上一题按钮，题号溢出检测
            public void actionPerformed(ActionEvent e){
                if(remainedTime>=1)     fillMyAnswer();
                i--;
                fillQuestion();
                update();
            }});

        nextbt.addActionListener(new ActionListener() {                 //下一题按钮，题号溢出检测，检测是否答题
            public void actionPerformed(ActionEvent e){
                if(remainedTime>=1)     fillMyAnswer();
                if(g.isSelected(Ba.getModel())||g.isSelected(Bb.getModel())||g.isSelected(Bc.getModel())||i>6||remainedTime<1) {
                    i++;
                    fillQuestion();
                    update();
                }else {
                    JFrame error=new JFrame("error");             //弹出错误提示框
                    error.setSize(250,150);
                    error.setLocationRelativeTo(null);
                    error.setVisible(false);
                    error.setLayout(new GridLayout(3,1,10,10));
                    error.add(new JLabel("请先选择一个答案"));
                    error.setVisible(true);
                }
            }});

        practiceAgainbt.addActionListener(new ActionListener() {         //重新答题，重新计时
            public void actionPerformed(ActionEvent e){
                endTimer.cancel();
                startCounting();
                i=1;
                t.setText("");
                q.getNewQuestions();
                fillQuestion();
                update();
            }});

        cBox.addItemListener(new ItemListener()  {                          //监听下拉题号选择
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    i=cBox.getSelectedIndex()+1;
                    fillQuestion();
                    update();
                } 
            }});
        
        finishbt.addActionListener(new ActionListener() {                   //结束答题，打印答题情况
            public void actionPerformed(ActionEvent e){
                remainedTime=0;
                if(b==true) return;
                if(remainedTime>0)  fillMyAnswer();
                JFrame fin=new JFrame("结束");
                fin.setSize(250,150);
                fin.setLocationRelativeTo(null);
                fin.setVisible(false);
                fin.setLayout(new GridLayout(3,1,10,10));
                q.input();
                fin.add(new JLabel("你的得分是"+q.getScore()));
                fin.add(new JLabel("你可以从试卷.txt中查看本次试卷"));
                fin.setVisible(true);
            }});
    }

    public void fillMyAnswer(){                                             //获取用户填写或选择的答案
        if(i>=1&i<=6){
            if(g.isSelected(Ba.getModel())==true) q.myAnswer[i-1]="A";
            if(g.isSelected(Bb.getModel())==true) q.myAnswer[i-1]="B";
            if(g.isSelected(Bc.getModel())==true) q.myAnswer[i-1]="C";
        }
        if(i==7||i==8){
            q.myAnswer[i-1]=t.getText();
        }
        if(i==9)i=8;
        if(i==0)i=1;
    }

    public void fillQuestion(){                                             //重新打印题目信息
        if(i>=1&i<=6){
            pa.setVisible(true);
            pb.setVisible(true);
            pc.setVisible(true);
            p2.setVisible(false);
            stem.setText(q.question[i-1]);
            Ba.setText(q.A[i-1]);
            Bb.setText(q.B[i-1]);
            Bc.setText(q.C[i-1]);
        }
        if(i==7||i==8){
            pa.setVisible(false);
            pb.setVisible(false);
            pc.setVisible(false);
            p2.setVisible(true);
            stem.setText(q.question[i-1]);
        }
        if(i>8||i<1){
            if (i==0) i=1;
            if  (i==9) i=8;
            JFrame error=new JFrame("error");
            error.setSize(150,100);
            error.setLocationRelativeTo(null);
            error.setVisible(true);
            error.setLayout(new GridLayout(1,1,10,10));
            error.add(new JLabel("没有下一题/上一题了！"));
        }else{
            cBox.setSelectedIndex(i-1);
        }
    }

    public void update(){                                             //更新UI
        g.clearSelection();
        if(i<=8)    switch(q.myAnswer[i-1]){
            case "A" :g.setSelected(Ba.getModel(), true);break;
            case "B" :g.setSelected(Bb.getModel(), true);break;
            case "C" :g.setSelected(Bc.getModel(), true);break;
        }
        if(i<=8)    t.setText(q.myAnswer[i-1]);
        p0.updateUI();
        p1.updateUI();
        p2.updateUI();
        pa.updateUI();
        pb.updateUI();
        pc.updateUI();
        p5.updateUI();
        win.repaint();
    }

    public void startCounting(){                                      //计时器时间到了自动交卷
        remainedTime=60*8*1000;
        b=false;
        endTimer=new Timer();
        endTimer.schedule(new TimerTask() {
            public void run(){
                if(remainedTime<=0) return;
                ti.setText(""+remainedTime/1000+"s（秒）");
                remainedTime-=500;
                p0.updateUI();
                if(remainedTime<=500) {                    
                    endTimer.cancel();
                    finishbt.doClick(1);
                }
            }
        }, 0,500);
    }
}