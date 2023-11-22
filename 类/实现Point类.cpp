/*定义一个点类Point,并定义成员函数double Distance(const & Point) 求两点的距离。
输入两个点的坐标，
创建两个点， 然后调用Point类的Distance方法输出两个点的距离。

Point 类的使用方式如下：*/
#include<iostream>
#include<cmath>
using namespace std;
class Point
{
    public:
    Point(double i,double j);
    double Distance(const Point other_Point);
    private:
    double a;
    double b;
};
Point::Point(double i,double j)
{
    a=i;b=j;
}
double Point::Distance(const Point other_Point)
{
    double i=a-other_Point.a;
    double j=b-other_Point.b;
    double Dist =sqrt(i*i+j*j);
    return Dist;
}
int main(){
    double a,b,c,d;
	cin>>a>>b>>c>>d;
	Point A(a,b),B(c,d);
	cout<<A.Distance(B)<<endl;
	return 0;
}
/*如输入：
1 2 3 4回车
输出：
2.82843*/