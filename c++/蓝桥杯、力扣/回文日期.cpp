#include <iostream>
using namespace std;
int months[12]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
int flip(int y);              //计算是否为闰年
bool check(int y,int m,int d);//判断日期是否合法
int palin1(int y,int m,int d);//计算回文日期
int palin2(int y,int m,int d);//计算ababbaba回文日期
int main()
{
  int a;
  cin>>a;
  int y=a/10000;
  int m=a/100-y*100;
  int d=a%100;
   cout<<palin1(y,m,d)<<endl<<palin2(y,m,d);
  return 0;
}
int flip(int y)
{
  int ret = 0;
  if(!(y%400))ret=1;
  else if(!(y%100)) ret=0;
  else if(!(y%4)) ret=1;
  return ret;
}
bool check(int y,int m,int d)
{
  bool ret=0;
  if(y>=1000&&y<=9999&&m>=1&&m<=12&&d>=1)
  {
    if(d<=months[m-1]) ret=1;
    else if(m==2&&d==flip(y)+months[1]) ret=1;
  }
  return ret;
}
int palin1(int y,int m,int d)
{
    int month=y%10*10+y%100/10;
    int day=y%1000/100*10+y/1000;
    if(check(y,month,day)&&y<=9999&&(m<month||(m==month&&d<day)))   return y*10000+month*100+day;
        else if(y>9999) return 0;
            else{y++;palin1(y,1,1);}
}
int palin2(int y,int m,int d)
{
    int year=y;
    while(year/100!=year%100) year++;
    int month=year%10*10+year%100/10;
    int day=month;
    if(check(year,month,day)&&y<=9999&&(year>y||(year==y&&month>m)||(year==y&&month==m&&day>d))) 
    {
        cout<<month<<day;
        return year*10000+month*100+day;}
    else if(y>9999) return 0;
    else {year+=101;palin2(year,1,1);}
}