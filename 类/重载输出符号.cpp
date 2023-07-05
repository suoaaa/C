//你需要实现运算符 << 的重载， 输出日期的格式参见display方法。
#include<iostream>
using namespace std;
class Date{
public:
	Date(int y=1996,int m=1,int d=1){
		day = d;
		month = m;
		year = y;
		if (m>12 || m<1)
		{
			month=1;
		}
		if (d>days(y,m))
		{
			cout<<"Invalid day!"<<endl;
			day=1;
		}
	};
	int days(int y,int m);//返回指定年月有多少天
	void display(){
		cout<<year<<"-"<<month<<"-"<<day<<endl;}
    friend ostream& operator <<(ostream& ,Date&);
private:
	int year;
	int month;
	int day;
};
ostream& operator <<(ostream &output,Date &c)
{
	output<<c.year<<"-"<<c.month<<"-"<<c.day<<endl;
	return output;
}
int Date::days(int y,int m)
{
	int d=0;
	if(m<0||m>12) 
    {   d=-1;}
    switch(m)
    {
        case 1:d=31;break;
        case 2:
            if(!(y%100))
            {
                if(!(y%400)) d=29;
                else d=28;
            }
            else
            {
                if(!(y%4)) d=29;
                else d=28;
            }
            break;
        case 3:d=31;break;
        case 4:d=30;break;
        case 5:d=31;break;
        case 6:d=30;break;
        case 7:d=31;break;
        case 8:d=31;break;
        case 9:d=30;break;
        case 10:d=31;break;
        case 11:d=30;break;
        case 12:d=31;break;
        default:break;
    }
	return d;
}
int main(){
    int y,m,d;
	cin>>y>>m>>d;
	Date dt(y,m,d);
	cout<<dt;
	 return 0;
}
// 例如,输入：
// 2013 2 1回车
// 输出：
// 2013-2-1回车
