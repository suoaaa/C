/*给定PersonFactory接口，要求实现该类，并以PersonFactory为父类派生学生（Student）与老师（Teacher）两个子类。
学生有年级(int grade)，老师有部门（char department[50])。
基类的PrintInfo依次输出编号，姓名，年龄；学生的PrintInfo除了输出基类信息，还要输出年级；
老师的PrintInfo除了输出基类信息，还要输出部门。各个输出项之间用回车换行符分隔。
*/
class PersonFactory//基类
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
	PersonFactory * CreateStudent(int id, char *name, int age, int  grade);
	PersonFactory * CreateTeacher(int id, char *name, int age, char *department);
};