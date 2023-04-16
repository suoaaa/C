package �ľ����;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class MyWindow {
    JFrame win=new JFrame("�����ľ�ϵͳ");              //���������Լ�Ϊ��������
    JFrame smallJFrame=new JFrame();                            //С��ʾ��
    JTextField  stem = new JTextField(40);              //��Ŀ
    int i=1;                                                  //��Ϊ��¼��Ŀǰ��ʾ�ĵڼ���
    JComboBox < String > cBox=new JComboBox< String >();
    JLabel ti=new JLabel();                                     //����ʱ��ʾ
    int remainedTime=60*8*1000;                                 //ʣ��ʱ��
    
    JButton nextbt = new JButton("�¸���Ŀ");               //�˵���ť
    JButton lastbt = new JButton("�ϸ���Ŀ");
    JButton practiceAgainbt = new JButton("���¿�ʼ");
    JButton startbt = new JButton("��ʼ");
    JButton finishbt = new JButton("��������");

    ButtonGroup g=new ButtonGroup();                            //����ѡ����Ϊ�������Buttongroup g
    JRadioButton Ba=new JRadioButton("A");                 //������ѡ��Ϊѡ��
    JRadioButton Bb=new JRadioButton("B");
    JRadioButton Bc=new JRadioButton("C");

    JTextField t=new JTextField(20);            //�������մ�
    JPanel p0 = new JPanel();                           //ѡ�����
    JPanel p1 = new JPanel();                           //��Ŀ��
    JPanel p2 = new JPanel();                           //��������
    JPanel pa = new JPanel();                           //ѡ��A��
    JPanel pb = new JPanel();                           //ѡ��B�� 
    JPanel pc = new JPanel();                           //ѡ��C�� 
    JPanel p5 = new JPanel();                           //������ư�ť��
    JLabel la,lb,lc;                                    //��¼ABCѡ���������

    Questions q;                                        //questions����Ϊ��Ա������Ŀ��Ϣ
    Timer endTimer=new Timer();                         //���ڼ�ʱ��8�����ڴ���
    boolean b=false;                                    //����Ƿ��ӡ���������

    MyWindow(Questions q){                              //UI��ʼ���������Ӷ԰�ť�ļ���
        this.q=q;
        for(int i=0;i<8;i++) cBox.addItem(""+(i+1));

        p2.setVisible(false);
        p2.setSize(30, 30);
        g.add(Ba);      g.add(Bb);      g.add(Bc);
        p0.add(new JLabel("��ǰ���"));         p0.add(cBox);   
        p0.add(new JLabel("                 ����ʱ��"));       p0.add(ti);
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

        startbt.addActionListener(new ActionListener() {                //��ʼ��ť��ʼ������Ԫ������win���ڳ�Ա����ʼ��ʱ
            public void actionPerformed(ActionEvent e){
                startCounting();
                win.remove(startbt);
                win.add(p0);    win.add(p1);    win.add(p2); win.add(pa);win.add(pb);     win.add(pc); win.add(p5);
                fillQuestion();
            }});

        lastbt.addActionListener(new ActionListener() {                 //��һ�ⰴť�����������
            public void actionPerformed(ActionEvent e){
                if(remainedTime>=1)     fillMyAnswer();
                i--;
                fillQuestion();
            }});

        nextbt.addActionListener(new ActionListener() {                 //��һ�ⰴť����������⣬����Ƿ����
            public void actionPerformed(ActionEvent e){
                if(remainedTime>=1)     fillMyAnswer();
                if(g.isSelected(Ba.getModel())||g.isSelected(Bb.getModel())||g.isSelected(Bc.getModel())||i>6||remainedTime<1) {
                    i++;
                    fillQuestion();
                }else {
                    getSmallFrame("error");                         //����������ʾ��
                    smallJFrame.add(new JLabel("����ѡ��һ����"));
                }
            }});

        practiceAgainbt.addActionListener(new ActionListener() {         //���´��⣬���¼�ʱ
            public void actionPerformed(ActionEvent e){
                endTimer.cancel();
                startCounting();
                i=1;
                t.setText("");
                q.getNewQuestions();
                fillQuestion();
            }});

        cBox.addItemListener(new ItemListener()  {                          //�����������ѡ��
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    if(b==true||i>6||g.isSelected(Ba.getModel())||g.isSelected(Bb.getModel())||g.isSelected(Bc.getModel())){
                        i=cBox.getSelectedIndex()+1;
                        fillQuestion();
                    }
                } 
            }});
        
        finishbt.addActionListener(new ActionListener() {                   //�������⣬��ӡ�������
            public void actionPerformed(ActionEvent e){
                remainedTime=0;
                fillMyAnswer();
                if(b==true) return;
                if(remainedTime>0)  fillMyAnswer();
                getSmallFrame("����");
                smallJFrame.add(new JLabel("��ĵ÷���"+q.getScore()));
                smallJFrame.add(new JLabel("����Դ��Ծ�.txt�в鿴�����Ծ�"));
                smallJFrame.setVisible(true);
                q.input();                                                  //questions��Ա��������ӡ�������
            }});
    }

    public void fillMyAnswer(){                                             //��ȡ�û���д��ѡ��Ĵ�
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

    public void fillQuestion(){                                             //���´�ӡ��Ŀ��Ϣ,���Ҹ��´���
        if(i>=1&i<=6){                                                      //����JPanel��setVisible��Ա�������������ѡ�����ʾ
            pa.setVisible(true);    
            pb.setVisible(true);
            pc.setVisible(true);
            p2.setVisible(false);
            stem.setText(""+i+"��"+q.question[i-1]);
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
            getSmallFrame("error");
            smallJFrame.add(new JLabel("û����һ��/��һ���ˣ�"));
        }else{
            cBox.setSelectedIndex(i-1);
        }
        update();
    }

    public void update(){                                             //����UI
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

    public void startCounting(){                                      //��ʱ��ʱ�䵽ʱ�Զ�����
        remainedTime=60*8*1000;                                       //��Ҫ�ǿ��ƾ�ͷ��ʱ�����ţ�����ʱ����
        b=false;
        endTimer=new Timer();
        endTimer.schedule(new TimerTask() {
            public void run(){
                if(remainedTime<=0) return;
                ti.setText(""+remainedTime/1000+"s���룩");
                remainedTime-=500;
                p0.updateUI();
                if(remainedTime<=500) {                    
                    endTimer.cancel();
                    finishbt.doClick(1);
                }
            }
        }, 0,500);
    }

    public void getSmallFrame(String title){                             //�½�С����ʾ���ڳ�ʼ��
        if(smallJFrame.isActive()||smallJFrame.isVisible()) return;
        smallJFrame=new JFrame(title);
        smallJFrame.setSize(250,150);
        smallJFrame.setLocationRelativeTo(null);
        smallJFrame.setVisible(false);
        smallJFrame.setAlwaysOnTop(true);
        smallJFrame.setLayout(new GridLayout(3,1,10,10));
        smallJFrame.setVisible(true);
    }
}