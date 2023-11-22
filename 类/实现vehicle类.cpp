/*
将Vehicle类作为基类，派生出Car类，Truck类和Boat类。

完成Car类，Truck类和Boat类的构造函数和析构函数，参照Vehicle的构造函数和析构函数输出语句，
如Car类的构造函数输出：
Car constructor...回车

实现这三个派生类的display方法，输出This is a ***!
例如Car类的display方法输出：This is a car!回车

另外在你的代码中必须加入Vehicle类的createCar()、createTruck()和createBoat()方法的实现，
且这三个方法的实现必须在Car类，Truck类和Boat类的定义之后，实现如下：*/
#include <iostream>
using namespace std;
class Vehicle{
public:
	Vehicle()   {  cout<<"Vehicle constructor..."<<endl;    }
	~Vehicle()  {	cout<<"Vehicle destructor..."<<endl;	}
	virtual void display() const =0;
	static Vehicle * createCar();
	static Vehicle * createTruck();
	static Vehicle * createBoat();
};
class Car:public Vehicle{
public:
	Car()   {   cout<<"Car constructor..."<<endl;   }
	~Car()  {   cout<<"Car destructor..."<<endl;    }
	virtual void display()const    {   cout<<"This is a car!"<<endl;   }
};
class Truck:public Vehicle{
public:
	Truck()    {   cout<<"Truck constructor..."<<endl;  }
	~Truck()   {   cout<<"Truck destructor..."<<endl;  }
	virtual void display()const    {	cout << "This is a truck!"<<endl; }
};
class Boat:public Vehicle{
public:
	Boat()      {   cout<<"Boat constructor..."<<endl;  }
	~Boat()     {   cout<<"Boat destructor..."<<endl;   }
	virtual void display()const    {   cout << "This is a boat!"<<endl;  }
};
Vehicle * Vehicle::createCar(){return new Car();}
Vehicle * Vehicle::createTruck(){return new Truck();}
Vehicle * Vehicle::createBoat(){return new Boat();}
int main()
{
    Vehicle *a=new Vehicle();
    Vehicle *b=a->createCar();
    Vehicle *c=a->createBoat();
    Vehicle *d=a->createTruck();
    b->display();c->display();d->display();
    return 0;
}