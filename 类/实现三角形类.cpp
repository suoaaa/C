/*实现一个三角形类 Ctriangle 
该类有一个GetPerimeter方法返回三角形的周长;
GetArea方法返回三角行的面积;
该类还提供一个display方法显示三角形的三边长度;
输入三条边的长度（不用考虑三条边不能构成三角形的情况）;
展示三角形的三边长度以及周长和面积
Ctriangle类的使用如下，在你的代码中除了实现Ctriangle类还需引入一下代码。*/
#include<iostream>
#include<cmath>
using namespace std;
class Ctriangle
{
    public:
    Ctriangle(int o,int p,int q);
    int GetPerimeter();//周长
    double GetArea();//面积
    void display();//展示周长
    private:
    int a;int b;int c;
};
Ctriangle::Ctriangle(int o,int p,int q)
{
    a=o;
    b=p;
    c=q;
}
int Ctriangle::GetPerimeter()//返回周长
{
    return a+b+c;
}
double Ctriangle::GetArea()//返回面积
{
    double p=(a+b+c)/2.0;
    double area=sqrt(p*(p-a)*(p-b)*(p-c));
    return area;
}
void Ctriangle::display()//展示边长
{
    cout<<"Ctriangle:a="<<a<<",b="<<b<<",c="<<c<<endl;
}
int main(){
	double a,b,c;
	cin>>a>>b>>c;
	Ctriangle T(a,b,c);
	T.display();
	cout<<"Perimeter:"<<T.GetPerimeter()<<endl;
	cout<<"Area:"<<T.GetArea()<<endl;
	return 0;	 
}
/*输出格式
输入：3 4 5回车
输出：
Ctriangle:a=3,b=4,c=5回车
Perimeter:12回车
Area:6回车
*/