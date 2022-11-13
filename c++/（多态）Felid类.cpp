引入头文件Felid.h,它的内容如下：
#include <iostream>
using namespace std;
class Felid{
public:
	Felid(){
		cout<<"Felid constructor..."<<endl;
	}
	~Felid(){
		cout<<"Felid destructor..."<<endl;
	}
	virtual void sound()const{
		cout<<"Felid sound!"<<endl;
	}
	static Felid* createCat();
	static Felid* createLeopard();
};

将Felid类作为基类，派生出Cat类和Leopard类，

完成Cat类和Leopard类的构造函数和析构函数，参照Felid类的构造函数和析构函数输出语句。
例如Cat类的构造函数输出：
Cat constructor...

实现Cat类和Leopard类的sound函数，Cat类的叫声为Miaow!,Leopard类的叫声为Howl!

另外必须实现createCat和createLeopard函数，它们的实现必须在Cat类和Leopard类的定义之后
它们的实现如下；
Felid * Felid::createCat(){return new Cat();}
Felid *Felid::createLeopard(){return new Leopard();}