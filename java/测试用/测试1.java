package 测试用;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
 
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
 
public class 测试1 {
	public static void main(String[] args) {
		File f=new File("");
		// String s=f.getAbsolutePath().substring(0,f.getAbsolutePath().lastIndexOf("\\"));
		File f2=new File("E:\\个人编程");
		System.out.println(f.getAbsolutePath());
		System.out.println(f2.getAbsolutePath());
		// System.out.println(f.getParentFile().getAbsolutePath());
	}
}