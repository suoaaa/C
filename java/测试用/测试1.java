package ������;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
 
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
 
public class ����1 extends JFrame implements ActionListener{
	JButton open1=null;
	public static void main(String[] args) {
		new ����1();
	}
	public ����1(){
		open1=new JButton("open");
		this.add(open1);
		this.setBounds(400, 200, 100, 100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		open1.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		System.out.println(e.getID());
		System.out.println(e.getModifiers());
		JFileChooser jfc=new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
		jfc.showDialog(new JLabel(), "ѡ��");
		File file=jfc.getSelectedFile();
		if(file.isDirectory()){
			System.out.println("�ļ���:"+file.getAbsolutePath());
		}else if(file.isFile()){
			System.out.println("�ļ�:"+file.getAbsolutePath());
		}
		System.out.println(jfc.getSelectedFile().getName());
	}
}