package �ľ����;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;

public class myWindow {
    JFrame win=new JFrame("�����ľ�ϵͳ");              //���������Լ�Ϊ��������
    JTextField  stem = new JTextField(40);           //��Ŀ
    int i=1;                                                  //��Ϊ��¼��Ŀǰ��ʾ�ĵڼ���
    JComboBox < String > cBox=new JComboBox< String >();
    JLabel ti=new JLabel();                                     //����ʱ��ʾ
    int remainedTime=60*8*1000;                                 //ʣ��ʱ��
    
    JButton nextbt = new JButton("�¸���Ŀ");               //�˵���ť
    JButton lastbt = new JButton("�ϸ���Ŀ");               //�˵���ť
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

    questions q;                                        //questions����Ϊ��Ա������Ŀ��Ϣ
    Timer endTimer=new Timer();
    boolean b=false;

    myWindow(questions q){                              //UI��ʼ���������Ӷ԰�ť�ļ���
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
        win.setSize(400,450);               //���ô��ڴ�С
        win.setLocationRelativeTo(null);                //��������Ļ�м���ʾ
        win.setVisible(true);
        win.setLayout(new GridLayout(7,1,10,10));

        startbt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                startCounting();
                win.remove(startbt);
                win.add(p0);    win.add(p1);    win.add(p2); win.add(pa);win.add(pb);     win.add(pc); win.add(p5);
                fillQuestion();
                update();
            }});

        lastbt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(remainedTime>=1)     fillMyAnswer();
                i--;
                fillQuestion();
                update();
            }});

        nextbt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(remainedTime>=1)     fillMyAnswer();
                if(g.isSelected(Ba.getModel())||g.isSelected(Bb.getModel())||g.isSelected(Bc.getModel())||i>6||remainedTime<1) {
                    i++;
                    fillQuestion();
                    update();
                }else {
                    JFrame error=new JFrame("error");
                    error.setSize(250,150);                   //���ô��ڴ�С
                    error.setLocationRelativeTo(null);                    //��������Ļ�м���ʾ
                    error.setVisible(false);
                    error.setLayout(new GridLayout(3,1,10,10));
                    error.add(new JLabel("����ѡ��һ����"));
                    error.setVisible(true);
                }
            }});

        practiceAgainbt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                endTimer.cancel();
                startCounting();
                i=1;
                t.setText("");
                q.getNewQuestions();
                fillQuestion();
                update();
            }});

        cBox.addItemListener(new ItemListener()  {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    i=cBox.getSelectedIndex()+1;
                    fillQuestion();
                    update();
                } 
            }});
        
        finishbt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                remainedTime=0;
                if(b==true) return;
                if(remainedTime>0)  fillMyAnswer();
                JFrame fin=new JFrame("����");
                fin.setSize(250,150);                   //���ô��ڴ�С
                fin.setLocationRelativeTo(null);                //��������Ļ�м���ʾ
                fin.setVisible(false);
                fin.setLayout(new GridLayout(3,1,10,10));
                q.input();
                fin.add(new JLabel("��ĵ÷���"+q.getScore()));
                fin.add(new JLabel("����Դ��Ծ�.txt�в鿴�����Ծ�"));
                fin.setVisible(true);
            }});
    }

    public void fillMyAnswer(){
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

    public void fillQuestion(){
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
            error.setSize(150,100);               //���ô��ڴ�С
            error.setLocationRelativeTo(null);                //��������Ļ�м���ʾ
            error.setVisible(true);
            error.setLayout(new GridLayout(1,1,10,10));
            error.add(new JLabel("û����һ��/��һ���ˣ�"));
        }else{
            cBox.setSelectedIndex(i-1);
        }
    }

    public void update(){
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

    public void startCounting(){
        remainedTime=60*8*1000;
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
}