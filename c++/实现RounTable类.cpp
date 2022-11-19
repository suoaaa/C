/*定义一个Table类和Circle类，
Table类有高度high属性，Circle类有半径 radius属性，类型都为float。
Circle类有GetArea()方法，返回圆的面积。
Table类有GetHigh()方法，返回Table的高度。
实现一个RoundTable类，它由Table类和Circle类共同派生出，并拥有color属性
同时实现 char* GetColor方法，返回color的值。*/
#include<iostream>
using namespace std;
#define pi 3.14
class Table
{
private:
    float high;
public:
    Table(float h){high=h;};
    float GetHigh(){return high;};
    void SetHigh(float h){high=h;};
};
class Circle
{
private:
    float radius;
public:
    Circle(float r){radius=r;};
    float GetArea(){return pi*radius*radius;};
    void SetRadius(float r){radius=r;};
};
class RoundTable:public Circle,public Table
{
public:
    RoundTable(float r,float h,char *c):Circle(r),Table(h)  {color=c;};
    char* GetColor(){return color;};
private:
    char *color;
};
int main(){
    float radius,high;
	char color[20];
	cin>>radius>>high>>color;
	RoundTable RT(radius,high,color);
	cout<<"Area:"<<RT.GetArea()<<endl;
	cout<<"High:"<<RT.GetHigh()<<endl;
	cout<<"Color:"<<RT.GetColor()<<endl;
	return 0;
}
