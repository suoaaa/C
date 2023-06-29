import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyWindow extends JFrame implements ActionListener {
    JFrame smallJFrame=new JFrame();                            //小提示框
    JTextField  stem = new JTextField(40);              //题目
    int i=1;                                                    //作为记录，目前显示的第几题
    JComboBox < String > cBox= new JComboBox<>();
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

    Questions q;                                        //questions类作为成员储存题目信息
    Timer endTimer=new Timer();                         //用于计时，8分钟内答题
    boolean b=false;                                    //检测是否打印过答题情况

    MyWindow(Questions q){                              //UI初始化并且增加对按钮的监听
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


        this.setSize(400,450);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.add(startbt,BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        startbt.addActionListener(this);
        lastbt.addActionListener(this);
        nextbt.addActionListener(this);
        practiceAgainbt.addActionListener(this);
        finishbt.addActionListener(this);

        cBox.addItemListener(new ItemListener()  {                          //监听下拉题号选择
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    if(Ba.isSelected()||Bb.isSelected()||Bc.isSelected()||b||!t.getText().equals("")){
                        fillMyAnswer();
                        i=cBox.getSelectedIndex()+1;
                        fillQuestion();
                    }else {
                        cBox.setSelectedIndex(i-1);
                        getSmallFrame("error");                         //弹出错误提示框
                        smallJFrame.add(new JLabel("请先选择一个答案"));
                    }
                }
            }});

    }

    public void fillMyAnswer(){                                             //获取用户填写或选择的答案
        if(!b){
            if (i >= 1 & i <= 6) {
                if (g.isSelected(Ba.getModel())) q.myAnswer[i - 1] = "A";
                if (g.isSelected(Bb.getModel())) q.myAnswer[i - 1] = "B";
                if (g.isSelected(Bc.getModel())) q.myAnswer[i - 1] = "C";
                g.clearSelection();
            }
            if (i == 7 || i == 8) {
                q.myAnswer[i - 1] = t.getText();
            }
        }
    }

    public void fillQuestion(){                                             //重新打印题目信息,并且更新窗口
        if(i>=1&&i<=6){                                                      //利用JPanel的setVisible成员方法控制填空与选项的显示
            pa.setVisible(true);
            pb.setVisible(true);
            pc.setVisible(true);
            p2.setVisible(false);
            stem.setText(""+i+"、"+q.question[i-1]);
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
        update();
    }

    public void update(){                                             //更新UI
        g.clearSelection();
        if(q.myAnswer[i-1]!=null&&i<=6) {
            g.setSelected(Ba.getModel(), q.myAnswer[i - 1].equals( "A"));
            g.setSelected(Bb.getModel(), q.myAnswer[i - 1].equals( "B"));
            g.setSelected(Bc.getModel(), q.myAnswer[i - 1].equals( "C"));
        }
        if(i>=7)    t.setText(q.myAnswer[i-1]);
        p0.updateUI();
        p1.updateUI();
        p2.updateUI();
        pa.updateUI();
        pb.updateUI();
        pc.updateUI();
        p5.updateUI();
        this.repaint();
    }

    public void startCounting(){                                      //计时器时间到时自动交卷
        remainedTime=60*8*1000;                                       //主要是控制卷头的时间流逝，归零时交卷
        b=false;
        endTimer=new Timer();
        endTimer.schedule(new TimerTask() {
            public void run(){
                if(remainedTime<0) return;
                ti.setText(""+remainedTime/1000+"s（秒）");
                remainedTime-=500;
                p0.updateUI();
                if(remainedTime<=500) {
                    finishbt.doClick(1);
                    endTimer.cancel();
                }
            }
        }, 0,500);
    }

    public void getSmallFrame(String title){                             //新建小的提示窗口初始化
        if(smallJFrame.isActive()||smallJFrame.isVisible()) return;
        smallJFrame=new JFrame(title);
        smallJFrame.setSize(250,150);
        smallJFrame.setLocationRelativeTo(null);
        smallJFrame.setVisible(false);
        smallJFrame.setAlwaysOnTop(true);
        smallJFrame.setLayout(new GridLayout(3,1,10,10));
        smallJFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {                                    //监听实现方法
        if(e.getSource()==nextbt){                                                  //下一题按钮
            if(i>=8) {
                i=8;
                getSmallFrame("error");
                smallJFrame.add(new JLabel("没有下一题了！"));
            }else cBox.setSelectedIndex(i);
        } else if (e.getSource()==lastbt) {                                         //上一题按钮
            if(i<=1){
                i=1;
                getSmallFrame("error");
                smallJFrame.add(new JLabel("没有上一题了！"));
            }else cBox.setSelectedIndex(i-2);
        } else if (e.getSource()==practiceAgainbt){                                 //重新开始按钮
            endTimer.cancel();
            cBox.setSelectedIndex(0);
            startCounting();
            b=false;
            t.setText("");
            q.getNewQuestions();
            fillQuestion();
        } else if (e.getSource()==finishbt){                                        //结束答题按钮
            if(b) return;
            fillMyAnswer();
            b=true;
            endTimer.cancel();
            getSmallFrame("结束");
            smallJFrame.add(new JLabel("你的得分是" + q.getScore()));
            smallJFrame.add(new JLabel("你可以从试卷.txt中查看本次试卷"));
            smallJFrame.setVisible(true);
            q.input();                                                  //questions成员方法，打印答题情况
        } else if (e.getSource()==startbt){                                         //开始按钮
            startCounting();
            this.remove(startbt);
            this.setLayout(new GridLayout(7,1,10,10));
            this.add(p0);
            this.add(p1);
            this.add(p2);
            this.add(pa);
            this.add(pb);
            this.add(pc);
            this.add(p5);
            fillQuestion();
        }
    }
}