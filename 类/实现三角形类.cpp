/*ʵ��һ���������� Ctriangle 
������һ��GetPerimeter�������������ε��ܳ�;
GetArea�������������е����;
���໹�ṩһ��display������ʾ�����ε����߳���;
���������ߵĳ��ȣ����ÿ��������߲��ܹ��������ε������;
չʾ�����ε����߳����Լ��ܳ������
Ctriangle���ʹ�����£�����Ĵ����г���ʵ��Ctriangle�໹������һ�´��롣*/
#include<iostream>
#include<cmath>
using namespace std;
class Ctriangle
{
    public:
    Ctriangle(int o,int p,int q);
    int GetPerimeter();//�ܳ�
    double GetArea();//���
    void display();//չʾ�ܳ�
    private:
    int a;int b;int c;
};
Ctriangle::Ctriangle(int o,int p,int q)
{
    a=o;
    b=p;
    c=q;
}
int Ctriangle::GetPerimeter()//�����ܳ�
{
    return a+b+c;
}
double Ctriangle::GetArea()//�������
{
    double p=(a+b+c)/2.0;
    double area=sqrt(p*(p-a)*(p-b)*(p-c));
    return area;
}
void Ctriangle::display()//չʾ�߳�
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
/*�����ʽ
���룺3 4 5�س�
�����
Ctriangle:a=3,b=4,c=5�س�
Perimeter:12�س�
Area:6�س�
*/