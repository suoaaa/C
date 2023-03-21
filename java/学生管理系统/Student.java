package 学生管理系统;

public class Student {
   int id;
   int age;
   String name;
   String home;
   boolean flag;

   Student(){flag=false;};
   Student(int id,int age,String name,String home){
    this.id=id;
    this.name=name;
    this.age=age;
    this.home=home;
    flag=true;
   }
public int getId() {
    return id;
}
public void setId(int id) {
    this.id = id;
}
public int getAge() {
    return age;
}
public void setAge(int age) {
    this.age = age;
}
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public String getHome() {
    return home;
}
public void setHome(String home) {
    this.home = home;
}
public void shouStu() {
    System.out.println("     "+this.id+"\t\t "+this.name+"\t"+this.age+"\t"+this.home);
} 
   
}
