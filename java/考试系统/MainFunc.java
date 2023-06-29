import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class MainFunc {
    public static void main(String[] args) {
        String rootpath, name=null;
        File file=new File("");
        rootpath=file.getAbsolutePath();
        JFileChooser jfc=new JFileChooser(rootpath);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setMultiSelectionEnabled(true);
        jfc.showDialog(new JLabel(), "选择题库");
        jfc.setVisible(true);
        if(jfc.getSelectedFile()!=null) {
            rootpath=jfc.getSelectedFile().getAbsolutePath();
            name=rootpath.substring(rootpath.lastIndexOf('\\'));
            rootpath=rootpath.substring(0, rootpath.lastIndexOf('\\'));
        }
        new MyWindow(new Questions(rootpath,name));
    }
}
        //选择题库进行考试，默认即不选择文件为题库.txt
        //实参应为题库文件所在文件夹,以及题库的名称
        //也可以利用题库1进行测试