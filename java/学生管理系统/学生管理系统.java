package ѧ������ϵͳ;

import java.util.Scanner;

public class ѧ������ϵͳ {
    public static void main(String[] args) {
        int flag=0;
        int i=0;
        Student []st=new Student[5];
        for (int j = 0; j < st.length; j++) {
            st[j]=new Student();
        }
        System.out.println(st[0].flag);
        Scanner sc=new Scanner(System.in);
        menu();
        while (flag < 6) {
            switch (flag) {
                case 1:
                    System.out.println("������ѧ����ѧ�ţ������������Լ���ͥסַ���м��ÿո����");
                    addStudent(st);
                    break;
                case 2:
                	System.out.println("ѡ��ɾ��ѧ�ŵ���������ѧ�Ű�1����ѧ��ɾ������2��������ɾ����");
                    i=sc.nextInt();
                    switch(i){
                        case 1:
                        	System.out.println("������ɾ��ѧ����ѧ�ţ�");
                        	delStudent(st,sc.nextInt());
                            break;
                        case 2:
                        	System.out.println("������ɾ��ѧ����������");
                            delStudent(st,sc.next());
                            break;
                    }
                    break;
                case 3:
                	System.out.println("ѡ�����ѧ�ŵ���������ѧ�Ű�1����ѧ�Ų�ѯ���޸ģ���2����������ѯ���޸ģ�");
                	i=sc.nextInt();
                	switch(i){
                    	case 1:
                        	System.out.println("�������޸�ѧ����ѧ�ţ�");
                	        changeStudent(st,sc.nextInt());
                    	    break;
                    	case 2:
                    	    System.out.println("�������޸�ѧ����������");
                        	changeStudent(st,sc.next());
                        	break;
                	}
                    break;
                case 4:
                	System.out.println("ѡ���ѯѧ������������ѧ�Ű�1����ѧ�Ų�ѯ����2����������ѯ��");
                	i=sc.nextInt();
                	switch(i){
                    	case 1:
                        	System.out.println("�������޸�ѧ����ѧ�ţ�");
                        	searchStudent(st,sc.nextInt());
                        	break;
                    	case 2:
                        	System.out.println("�������޸�ѧ����������");
                        	searchStudent(st,sc.next());
                        	break;
                	}
                	break;
                case 5:
                	showAllStudent(st);
                    break;
                default:
                    break;
            }
            menu();
            flag=sc.nextInt();
        }
        sc.close();
    }

    public static void menu() {
        System.out.println("\t \t" + "��ӭʹ��ѧ������ϵͳ");
        System.out.println("1.���ѧ��");
        System.out.println("2.ɾ��ѧ��");
        System.out.println("3.�޸�ѧ��");
        System.out.println("4.��ѯѧ��");
        System.out.println("5.չʾѧ��");
        System.out.println("6.�˳�ϵͳ");
        System.out.println("����������ѡ��");
    }

    public static void addStudent(Student []st) {
        Scanner sc=new Scanner(System.in);
        int id=sc.nextInt();
        String name=sc.next();
        int age=sc.nextInt();
        String home=sc.next();
        int i=0;
        boolean flag=true;
        if(searchStudent(st, id)!=-1){
            System.out.println("���ʧ�ܣ�ѧ���ظ�");
                flag=false;
        }
        while(flag){
            if(i==st.length){
                System.out.println("���ʧ�ܣ�ѧ����������");
                flag=false;
            }else if(st[i].flag==false){
                st[i]=new Student(id, age, name, home);
                System.out.println("��ӳɹ�");
                flag=false;
            	}
            i++;
        }
    }
    public static void delStudent(Student []st,int id){
    	int ret=searchStudent(st, id);
        if(ret==-1){
            System.out.println("ɾ��ʧ�ܣ����޴���");
        }else{
            st[ret].flag=false;
            System.out.println("ɾ���ɹ�");
        }
    }
    public static void delStudent(Student []st,String name){
    	int ret=searchStudent(st, name);
        if(ret==-1){
            System.out.println("ɾ��ʧ�ܣ����޴���");
        }else{
            st[ret].flag=false;
            System.out.println("ɾ���ɹ�");
        }
    }
    public static void changeStudent(Student []st,int id){
        int ret=searchStudent(st, id);
        if(ret==-1){
            System.out.println("����ʧ�ܣ����޴���");
        }else{
            st[ret].shouStu();
            System.out.println("��ȷ���Ƿ���Ĵ�ѧ����Ϣ��������1��������0");
            Scanner sc=new Scanner(System.in);
            int flag=sc.nextInt();
            if(flag!=1) {sc.close();return;}
            System.out.println("��������ĺ�ѧ����ѧ�ţ������������Լ���ͥסַ���м��ÿո����������ͬ����һͬ����");
			st[ret].setAge(sc.nextInt());
            st[ret].setName(sc.next());
            st[ret].setAge(sc.nextInt());
            st[ret].setHome(sc.next());
            System.out.println("���ĳɹ��ɹ�");
            st[ret].shouStu();
        }
    }
    public static void changeStudent(Student []st,String name){
        int ret=searchStudent(st, name);
        if(ret==-1){
            System.out.println("����ʧ�ܣ����޴���");
        }else{
            st[ret].shouStu();
            System.out.println("��ȷ���Ƿ���Ĵ�ѧ����Ϣ��������1��������0");
            Scanner sc=new Scanner(System.in);
            int flag=sc.nextInt();
            if(flag!=1) {sc.close();return;}
            System.out.println("��������ĺ�ѧ����ѧ�ţ������������Լ���ͥסַ���м��ÿո����������ͬ����һͬ����");
			st[ret].setAge(sc.nextInt());
            st[ret].setName(sc.next());
            st[ret].setAge(sc.nextInt());
            st[ret].setHome(sc.next());
            sc.close();
            System.out.println("���ĳɹ��ɹ�");
            st[ret].shouStu();
        }
    }
    public static int searchStudent(Student []st,int id) {
        int ret=-1;
        for(int i=0;i<st.length;i++){
            if(st[i].flag==true){
                if(st[i].id==id){
                    ret=i;
                    break;
                }
                
            }
        }
        return ret;
    }
    public static int searchStudent(Student []st,String name) {
        int ret=-1;
        for(int i=0;i<st.length;i++){
            if(st[i].flag==true){
                if(name.equals(st[i].getName())){
                    ret=i;
                    break;
                }
            }
        }
        return ret;
    }
    public static void showAllStudent(Student st[]) {
        System.out.println("����չʾȫ��ѧ����Ϣ");
        System.out.println("    ѧ��"+"\t\t����"+"\t����"+"\t��ͥסַ");
		for(int i=0;i<st.length;i++){
            if(st[i].flag==true){
                st[i].shouStu();
            }
        }
    }
}
