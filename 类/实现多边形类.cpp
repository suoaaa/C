/*请写出三角形、四边形、圆形三个派生类，构造函数分别传入三边/四边/半径的长度（不用检查是否符合三角形、矩形、圆的条件，没有异常输出）
重写出求周长的函数（Circumstance函数）。
然后实现基类的Create函数，这里重载的三个Create函数，分别生成三角形、四边形、圆形的对象。*/
#include<iostream>
using namespace std;
class ShapeFactory
{
public:
	ShapeFactory(){};
	virtual ~ShapeFactory(){};
	virtual float Circumstance(){return 0;};//周长重载
	ShapeFactory *Create(float a,float b,float c);
	ShapeFactory *Create(float a,float b,float c,float d);
	ShapeFactory *Create(float r);
};
class Triangle:public ShapeFactory
{
public:
	Triangle(float a,float b,float c):ShapeFactory()
	{
		this->a=a;this->b=b;this->c=c;
	};
	~Triangle(){};
	float Circumstance(){return a+b+c;};
private:
	float a;float b;float c;//三边长
};
class Quadrangle:public ShapeFactory
{
public:
	Quadrangle(float a,float b,float c,float d):ShapeFactory()
	{
		this->a=a;this->b=b;this->c=c;this->d=d;
	};
	~Quadrangle(){};
	float Circumstance(){return a+b+c+d;};
private:
	float a;float b;float c;float d;//四边长
};
class Circle:public ShapeFactory
{
public:
	Circle(float r):ShapeFactory()
	{
		this->r=r;
	};
	~Circle(){};
	float Circumstance(){return 2*3.14*r;};
private:
	float r;//半径
};
ShapeFactory * ShapeFactory::Create(float a,float b,float c)
{
	
	ShapeFactory *p=new Triangle(a,b,c);
	return p;
}
ShapeFactory * ShapeFactory::Create(float a,float b,float c,float d)
{
	
	ShapeFactory *p=new Quadrangle(a,b,c,d);
	return p;
}
ShapeFactory * ShapeFactory::Create(float r)
{
	ShapeFactory *p=new Circle(r);
	return p;
}
