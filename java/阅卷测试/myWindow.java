package 阅卷测试;

import javax.swing.*;

public class myWindow implements ActionListener{
    JFrame win=new JFrame("简易阅卷系统");          //构建窗口以及为窗口命名
    JRadioButton A=new JRadioButton("A");           //构建单选作为选项
    JRadioButton B=new JRadioButton("B");
    JRadioButton C=new JRadioButton("C");
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    JPanel p4 = new JPanel();
    JPanel p5 = new JPanel();
    JLabel la,lb,lc,ld;
    JButton b1,b2,b3;

    myWindow(){  }
}