/*实现TeachBuilding类和DormBuilding类，这两个类继承至Building类，
TeachBuilding类增加代表教学楼功能的char function[20]属性
DormBuilding类增加代表容纳学生总人数的int peoples属性
完成TeachBuilding类和DormBuilding类的display方法
display方法输出类的全部属性。
属性的输出格式参照Building类的dispaly方法。*/
#include <iostream>
#include<cstring>
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
class TeachBuilding:public Building{
public:
	TeachBuilding(char *name,int floor,int room,int area,char *function)
	:Building(name,floor,room,area){
		strcpy(this->function,function);
	}
	void display(){
		cout<<"Name:"<<name<<endl;
		cout<<"Floor:"<<floor<<endl;
		cout<<"Room:"<<room<<endl;
		cout<<"Area:"<<area<<endl;
		cout<<"Function:"<<function<<endl;
	}
protected:
	char function[20];
};
class DormBuilding:public Building{
public:
	DormBuilding(char *name,int floor,int room,int area,int peoples)
	:Building(name,floor,room,area){
		this->peoples=peoples;
	}
	void display(){
		cout<<"Name:"<<name<<endl;
		cout<<"Floor:"<<floor<<endl;
		cout<<"Room:"<<room<<endl;
		cout<<"Area:"<<area<<endl;
		cout<<"Peoples:"<<peoples<<endl;
	}
protected:
	int peoples;
};
Building* Building::createTeachBuilding(char *name,int floor,int room,int area,char *function){
	return  new TeachBuilding(name,floor,room,area,function);
}
Building * Building::creatDormBuilding(char *name,int floor,int room,int area,int peoples){
	return new DormBuilding(name,floor,room,area,peoples);
}