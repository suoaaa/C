package 课程设计.allCode;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;

public class InputWindow extends JFrame implements ActionListener {
    StringBuffer ip;
    JTextField jf=new JTextField();
    JButton jb=new JButton("确认");
    JPanel jp=new JPanel();
    public InputWindow(StringBuffer ip){
        super("输入链接的服务器地址");
        this.setSize(200,150);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setAlwaysOnTop(true);

        jp.setLayout(new GridLayout(3,1));
        jp.add(new JLabel("输入需要连接的服务器地址："));
        jp.add(jf);
        jp.add(jb);
        this.add(jp);
        this.setVisible(true);
        jb.addActionListener(this);

        this.ip=ip;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ip.append(jf.getText());
        if(ip.isEmpty()){
            try {
                ip.append(InetAddress.getLocalHost().getHostAddress());
                this.dispose();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }else        this.dispose();
    }
}
