package 考试系统;

public class MainFunc {
    public static void main(String[] args) {
        new MyWindow(new Questions("java\\阅卷测试"));
        //使用时请按文件路径更改实参，例如笔者题库路径为java\\阅卷测试\\题库.txt
        //实参应为题库.txt文件所在文件夹
        //也可以利用题库1进行测试
    }
}
