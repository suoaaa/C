package �γ����.allCode;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;

public class InputWindow extends JFrame implements ActionListener {
    StringBuffer ip;
    JTextField jf=new JTextField();
    JButton jb=new JButton("ȷ��");
    JPanel jp=new JPanel();
    public InputWindow(StringBuffer ip){
        super("�������ӵķ�������ַ");
        this.setSize(200,150);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setAlwaysOnTop(true);

        jp.setLayout(new GridLayout(3,1));
        jp.add(new JLabel("������Ҫ���ӵķ�������ַ��"));
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
