引入Building.h头文件，它的内容如下：
#include <iostream>
using namespace std;
class Building{
public:
	Building(char *name,int floor,int room,int area){
		strcpy(this->name,name);
		this->floor = floor;
		this->room = room;
		this->area = area;
	}
	virtual void display(){
		cout<<"Name:"<<name<<endl;
		cout<<"Floor:"<<floor<<endl;
		cout<<"Room:"<<room<<endl;
		cout<<"Area:"<<area<<endl;
	}
	Building * createTeachBuilding(char *name,int floor,int room,int area,char *function);
	Building * creatDormBuilding(char *name,int floor,int room,int area,int peoples);
protected:
	char name[20];
	int floor;
	int room;
	float area;

};

实现TeachBuilding类和DormBuilding类，这两个类继承至Building类，
除了Building的属性,TeachBuilding类增加代表教学楼功能的char function[20]属性
DormBuilding类增加代表容纳学生总人数的int peoples属性

完成TeachBuilding类和DormBuilding类的display方法，display方法输出类的全部属性。属性的输出格式参照Building类的dispaly方法。

必须实现Building类的createTeachBuilding和creatDormBuilding方法，它们的实现如下：
Building* Building::createTeachBuilding(char *name,int floor,int room,int area,char *function){
	return  new TeachBuilding(name,floor,room,area,function);
}

Building * Building::creatDormBuilding(char *name,int floor,int room,int area,int peoples){
	return new DormBuilding(name,floor,room,area,peoples);
}
它们的实现必选在TeachBuilding类和DormBuilding类的定义之后。

