/*由Shape类派生出Rectangle类和Circle类,Rectangle类有长宽属性，Circle类有半径属性

实现派生类Rectangle和Circle的GetArea()和GetPerimeter()函数，

Rectangle类的GetArea返回长方形的面积，GetPerimeter返回长方形的周长
Circle类的GetArea返回圆形的面积，GetPerimeter返回圆形的周长。

Rectangle类有构造函数Rectangle(double l,double w)
Circle类有构造函数Circle(double r)
*/
#include <iostream>
using namespace std;
#define Pi 3.14
class Shape{
public:
	Shape(){}
	~Shape(){}
	virtual double GetArea()=0;
	virtual double GetPerimeter()=0;
	static Shape* createRectangle(double length,double width);
	static Shape* createCircle(double radius);
};
//构建长方形类
class Rectangle:public Shape{
public:
	Rectangle(double l,double w);
	double GetArea();
	double GetPerimeter();
private:
	double length;
	double width;
};
//构建圆类
class Circle:public Shape{
public:
	Circle(double r);
	double GetArea();
	double GetPerimeter();
private:
	double radius;
};
Rectangle::Rectangle(double l,double w)
{
	length=l;
	width=w;
}
double Rectangle::GetArea()
{
	return length*width;
}
double Rectangle::GetPerimeter()
{
	return 2*(length+width);
}
Circle::Circle(double r)
{
	radius=r;
}
double Circle::GetArea()
{
	return Pi*radius*radius;
}
double Circle::GetPerimeter()
{
	return 2*Pi*radius;
}
Shape * Shape::createRectangle(double l,double w){return new Rectangle(l,w);}
Shape * Shape::createCircle(double r){return new Circle(r);}