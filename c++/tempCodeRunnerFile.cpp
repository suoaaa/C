#include<iostream>
using namespace std;
class Ctriangle
{
    public:
    Ctriangle(int o,int p,int q);
    int GetPerimeter();//周长
    int GetArea();//面积
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
int Ctriangle::GetArea()//返回面积
{
    return a*b*c;
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