package 学生管理系统;

import java.util.Scanner;

public class 学生管理系统 {
    public static void main(String[] args) {
        int flag=0;
        Student []st;
        Scanner sc=new Scanner(System.in);
        while (flag < 5) {
            switch (flag) {
                case 0:
                    menu();
                    break;
                case 1:
                System.out.println("请输入添加的学生数量：");
                int num=sc.nextInt();
                for (int i = 0; i < num; i++) {
                    System.out.println("请输入第"+i+1+"位学生的学号，姓名，年龄以及家庭住址，中间用空格隔开");
                    addStudent(st);
                }
                    break;
                case 2:
                    delStudent();
                    break;
                case 3:
                    changeStudent();
                    break;
                case 4:
                    searchStudent();
                    break;
                default:
                    break;
            }
            flag=sc.nextInt();
        }
    }

    public static void menu() {
        System.out.println("/t" + "欢迎使用学生管理系统");
        System.out.println("1.添加学生");
        System.out.println("2.删除学生");
        System.out.println("3.修改学生");
        System.out.println("4.查询学生");
        System.out.println("5.退出系统");
        System.out.println("请输入您的选择");
    }

    public static void addStudent(Student []st) {
        Scanner sc=new Scanner(System.in);
        int id=sc.nextInt();
        String name=sc.next();
        int age=sc.nextInt();
        String home=sc.next();
        int i=0;
        while(st[i]!=null){
            if(st[i].id!=id) i++;
            else{
                System.out.println("添加失败，学号重复");
                break;
            }
        } 
        st[i]=new Student(id, age, name, home);
        sc.close();
    }
}
