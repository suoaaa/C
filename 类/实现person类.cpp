/*给定PersonFactory接口，要求实现该类，并以PersonFactory为父类派生学生（Student）与老师（Teacher）两个子类。
学生有年级(int grade)，老师有部门（char department[50])。
基类的PrintInfo依次输出编号，姓名，年龄；学生的PrintInfo除了输出基类信息，还要输出年级；
老师的PrintInfo除了输出基类信息，还要输出部门。各个输出项之间用回车换行符分隔。
*/
#include<iostream>
#include<cstring>
using namespace std;
class PersonFactory
{
	int id;
	char name[50];
	int age;
	public:
	PersonFactory(int i = 0, char * nm = 0, int a = 15);//i:id;nm:name;a:age
	virtual ~PersonFactory() {};
	virtual void PrintInfo()
  	{
    	cout << id << endl;
    	cout << name << endl;
    	cout << age << endl;
  	};
  	PersonFactory * CreateStudent(int id, char *name, int age, int grade);
  	PersonFactory * CreateTeacher(int id, char *name, int age, char *department);
};
class Student:public PersonFactory{
public:
	Student(int id, char *name, int age, int  grade)
	:PersonFactory(id,name,age) {this->grade=grade;}
	void PrintInfo();
private:
	int grade;
};
class Teacher:public PersonFactory{
public:
	int id;
	char name[50];
	int age;
	char department[50];
	Teacher(int id, char *name, int age, char *department):PersonFactory(id,name,age)
	{
		strcpy(this->department,department);
	};
	void PrintInfo();
};
void Student::PrintInfo()
{
	PersonFactory::PrintInfo();
	cout << grade << endl;
}
void Teacher::PrintInfo()
{
	PersonFactory::PrintInfo();
	cout << department <<endl;
}
PersonFactory::PersonFactory(int i, char *nm, int a)
{
	id=i;
	if(nm!=NULL)
	{
		strcpy(name,nm);
	}
	age=a;
}
PersonFactory * PersonFactory::CreateStudent(int id, char *name, int age, int  grade)
{
	return new Student(id,name,age,grade);
}
PersonFactory * PersonFactory::CreateTeacher(int id, char *name, int age, char *department)
{
	return new Teacher(id,name,age,department);
}
int main()
{
	PersonFactory vn;
	PersonFactory *ps=NULL,*pt=NULL;
	int id,age,grade;
	char name[50],department[50];
	cin>>id>>name>>age>>department;
	ps=vn.CreateTeacher (id,name,age,department);
	ps->PrintInfo ();
	cin>>id>>name>>age>>grade;
	ps=vn.CreateStudent (id,name,age,grade);
	ps->PrintInfo ();
	return 0;
}