package ѧ������ϵͳ;

import java.util.Scanner;

public class ѧ������ϵͳ {
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
                System.out.println("��������ӵ�ѧ��������");
                int num=sc.nextInt();
                for (int i = 0; i < num; i++) {
                    System.out.println("�������"+i+1+"λѧ����ѧ�ţ������������Լ���ͥסַ���м��ÿո����");
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
        System.out.println("/t" + "��ӭʹ��ѧ������ϵͳ");
        System.out.println("1.���ѧ��");
        System.out.println("2.ɾ��ѧ��");
        System.out.println("3.�޸�ѧ��");
        System.out.println("4.��ѯѧ��");
        System.out.println("5.�˳�ϵͳ");
        System.out.println("����������ѡ��");
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
                System.out.println("���ʧ�ܣ�ѧ���ظ�");
                break;
            }
        } 
        st[i]=new Student(id, age, name, home);
        sc.close();
    }
}
