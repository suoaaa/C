package 学生管理系统;

import java.util.Scanner;

public class 学生管理系统 {
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
                    System.out.println("请输入学生的学号，姓名，年龄以及家庭住址，中间用空格隔开");
                    addStudent(st);
                    break;
                case 2:
                	System.out.println("选择删除学号的姓名或者学号按1按照学号删除，按2按照姓名删除：");
                    i=sc.nextInt();
                    switch(i){
                        case 1:
                        	System.out.println("请输入删除学生的学号：");
                        	delStudent(st,sc.nextInt());
                            break;
                        case 2:
                        	System.out.println("请输入删除学生的姓名：");
                            delStudent(st,sc.next());
                            break;
                    }
                    break;
                case 3:
                	System.out.println("选择更改学号的姓名或者学号按1按照学号查询并修改，按2按照姓名查询并修改：");
                	i=sc.nextInt();
                	switch(i){
                    	case 1:
                        	System.out.println("请输入修改学生的学号：");
                	        changeStudent(st,sc.nextInt());
                    	    break;
                    	case 2:
                    	    System.out.println("请输入修改学生的姓名：");
                        	changeStudent(st,sc.next());
                        	break;
                	}
                    break;
                case 4:
                	System.out.println("选择查询学生的姓名或者学号按1按照学号查询，按2按照姓名查询：");
                	i=sc.nextInt();
                	switch(i){
                    	case 1:
                        	System.out.println("请输入修改学生的学号：");
                        	searchStudent(st,sc.nextInt());
                        	break;
                    	case 2:
                        	System.out.println("请输入修改学生的姓名：");
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
        System.out.println("\t \t" + "欢迎使用学生管理系统");
        System.out.println("1.添加学生");
        System.out.println("2.删除学生");
        System.out.println("3.修改学生");
        System.out.println("4.查询学生");
        System.out.println("5.展示学生");
        System.out.println("6.退出系统");
        System.out.println("请输入您的选择");
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
            System.out.println("添加失败，学号重复");
                flag=false;
        }
        while(flag){
            if(i==st.length){
                System.out.println("添加失败，学生名单已满");
                flag=false;
            }else if(st[i].flag==false){
                st[i]=new Student(id, age, name, home);
                System.out.println("添加成功");
                flag=false;
            	}
            i++;
        }
    }
    public static void delStudent(Student []st,int id){
    	int ret=searchStudent(st, id);
        if(ret==-1){
            System.out.println("删除失败，查无此人");
        }else{
            st[ret].flag=false;
            System.out.println("删除成功");
        }
    }
    public static void delStudent(Student []st,String name){
    	int ret=searchStudent(st, name);
        if(ret==-1){
            System.out.println("删除失败，查无此人");
        }else{
            st[ret].flag=false;
            System.out.println("删除成功");
        }
    }
    public static void changeStudent(Student []st,int id){
        int ret=searchStudent(st, id);
        if(ret==-1){
            System.out.println("更改失败，查无此人");
        }else{
            st[ret].shouStu();
            System.out.println("请确认是否更改此学生信息：是输入1，否输入0");
            Scanner sc=new Scanner(System.in);
            int flag=sc.nextInt();
            if(flag!=1) {sc.close();return;}
            System.out.println("请输入更改后学生的学号，姓名，年龄以及家庭住址，中间用空格隔开，如相同数据一同输入");
			st[ret].setAge(sc.nextInt());
            st[ret].setName(sc.next());
            st[ret].setAge(sc.nextInt());
            st[ret].setHome(sc.next());
            System.out.println("更改成功成功");
            st[ret].shouStu();
        }
    }
    public static void changeStudent(Student []st,String name){
        int ret=searchStudent(st, name);
        if(ret==-1){
            System.out.println("更改失败，查无此人");
        }else{
            st[ret].shouStu();
            System.out.println("请确认是否更改此学生信息：是输入1，否输入0");
            Scanner sc=new Scanner(System.in);
            int flag=sc.nextInt();
            if(flag!=1) {sc.close();return;}
            System.out.println("请输入更改后学生的学号，姓名，年龄以及家庭住址，中间用空格隔开，如相同数据一同输入");
			st[ret].setAge(sc.nextInt());
            st[ret].setName(sc.next());
            st[ret].setAge(sc.nextInt());
            st[ret].setHome(sc.next());
            sc.close();
            System.out.println("更改成功成功");
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
        System.out.println("以下展示全部学生信息");
        System.out.println("    学号"+"\t\t姓名"+"\t年龄"+"\t家庭住址");
		for(int i=0;i<st.length;i++){
            if(st[i].flag==true){
                st[i].shouStu();
            }
        }
    }
}
