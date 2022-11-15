/*实现Rectangle类和Circle类，派生类都具有float Area()方法，返回派生对象的面积。
Rectangle类为矩形对象，拥有长和宽属性。
Circle类为圆形，有有半径属性。
完成Rectangle类和Circle类的构造方法和float Area()方法返回对象的面积
最终两个类的使用方法如下：
Rectangle rect(1,2,3,4);
rect.Area();     //12
Circle c(5,6,7);
c.Area();      //153.86*/
#include <iostream>
using namespace std;
class Point{
public:
	Point(float xx, float yy){
		x = xx;
		y =yy;
	}
private:
	float x;
	float y;
};
class Rectangle :public Point{
public:
	Rectangle(float xx,float yy,float w,float h);
	float Area();
private:
	float width;
	float high;
};
float Rectangle::Area(){return width*high;};
Rectangle::Rectangle(float xx,float yy,float w,float h)
	:Point(xx,yy){high=h;width=w;}
class Circle:public Point{
public:
	Circle(float xx,float yy,float r);
	float Area();
private:
	float radius;
};
float Circle::Area(){return 3.14*radius*radius;};
Circle::Circle(float xx,float yy,float r)
    :Point(xx,yy){radius=r;};
int main()
{
        Rectangle rect(1,2,3,4);
cout<<rect.Area()<<endl;     //12
Circle c(5,6,7);
cout<<c.Area()<<endl;      //153.86*/
return 0;
}