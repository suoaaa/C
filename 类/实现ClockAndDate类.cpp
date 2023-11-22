#include <iostream>
using namespace std;
class Clock{
public:
	Clock(int h,int m,int s){
	hour =(h>23? 0:h);
	minute = (m>59?0:m);
	second = (s>59?0:m);}
	virtual void run(){
		second = second+1;
		if (second>59)
		{   second =0;  minute+=1;}
		if (minute>59)
		{   minute =0;  hour+=1;}
		if (hour>23)
		{   hour =0;}
	};
	virtual void showTime(){
		cout<<"Now:"<<hour<<":"<<minute<<":"<<second<<endl;
	};
	int getHour(){return hour;};
	int getMinute(){return minute;};
	int getSecond(){return second;};
    Clock * createClockWithDate(int h,int m,int s,int year,int month,int day);
protected:
	int hour;
	int minute;
	int second;
};
class Date{
public:
	Date(int y=1996,int m=1,int d=1){
		day =d;
		year =y;
		month =m;
		if (m>12||m<1)
		{m=1;}
		if(d>days(y,m))
        {day = 1;}
	};
	int days(int y,int m);//返回该年该月对应的天数
	void NewDay();//日期增加一天
	void display()
    {   cout<<year<<"-"<<month<<"-"<<day<<endl;}
protected:
	int year;
	int month;
	int day;
};
class ClockWithDate:public Clock,public Date
{
public:
    ClockWithDate(int h,int m,int s,int year,int month,int day)
        :Clock(h,m,s),Date(year,month,day){};
    ~ClockWithDate(){};
    void run()//将现在的时间增加一秒，并且当时间超过23：59：59时，更新日期。
    {
        second = second+1;
		if (second>59)
		{   second =0;  minute+=1;}
		if (minute>59)
		{   minute =0;  hour+=1;}
		if (hour>23)
		{   hour =0;    NewDay();}
    }
    void showTime()//输出当前时间和日期，先时间后日期
    {
        cout<<"Now:"<<hour<<":"<<minute<<":"<<second<<endl;
        cout<<year<<"-"<<month<<"-"<<day<<endl;
    }
};
int Date::days(int y,int m)
{
    int d=31;
    if(m<0||m>12) 
    {   m=1;    }
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
            }break;
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
void Date::NewDay()
{
    day++;
    int d=days(year,month);
    if(day>d)
    {   day=1;  month++;}
    if(month>12)
    {   month=1;    year++;}
}
Clock* Clock::createClockWithDate(int h,int m,int s,int year,int month,int day)
{   return new ClockWithDate(h,m,s,year,month,day);}
int main()
{
    Clock a(2003,5,3);
    Clock *b=a.createClockWithDate(23,59,59,2003,5,3);
    b->showTime();
    b->run();
    b->showTime();
    return 0;
}