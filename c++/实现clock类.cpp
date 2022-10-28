/*实现构造函数Clock(int h,int m, int s)
构造函数设置时、分、秒。设置前先判断传入的时分秒是否合法，如果不合法将其设置为0
例如 Clock c(25,61,-1);
    c.ShowTime();
输： Now:0:0:0*/
/*实现SetAlarm 成员函数，设置闹钟设置时同样要判断传入的时分秒是否合法
但闹钟的时分秒可以为负数，表示关闭闹钟
实现run成员函数，将现在的时间秒数加一 ，然后检查闹钟时间是否到 
如果闹钟触发，则输出"Plink!plink!plink!..."
例如：
Clock c(2,3,4);
c.SetAlarm(2,3,5);
c.run();
输出： Plink!plink!plink!...回车*/
#include <iostream>
using namespace std;
class Clock{
public:
      Clock(int h,int m, int s);
	  void SetAlarm(int h,int m,int s);
	  void run();
	  void ShowTime()
      {
		cout<<"Now:"<<hour<<":"<<minute<<":"<<second<<endl;
	  }
private:
	int hour;   //时
	int minute;  //分
	int second;  //秒

	int Ahour;   //时（闹钟）
	int Aminute;  //分（闹钟）
	int Asecond;   //秒（闹钟）
};

Clock::Clock(int h,int m, int s)
{
    if(h>=0&&h<24) {hour=h;}
    else {hour=0;}
    if(m>=0&&m<60) {minute=m;}
    else {minute=0;}
    if(s>=0&&s<60) {second=s;}
    else {second=0;}
}
void Clock::SetAlarm(int h,int m, int s)
{

    if(h<24) {Ahour=h;}
	else {Ahour=0;}
 	if(m<60) {Aminute=m;}
	else {Aminute=0;}
	if(s<60) {Asecond=s;}
	else {Asecond=0;}
}

void Clock::run(){
    second++;
    if(second==60)
    {
        second=0;
        minute++;
        if(minute==60)
        {
            minute=0;
            hour++;
            if(hour==24)
            {
                hour=0;
            }
        }
    }
	if(Asecond==second&&Aminute==minute&&Ahour==hour)
	{
    	cout<<"Plink!plink!plink!...\n";
    }    
}
int main ()
{
    int h,m,s;
    cin>>h>>m>>s;
    Clock c(h,m,s);
    c.ShowTime();
    cin>>h>>m>>s;
    c.SetAlarm(h,m,s);
    c.run();
    return 0;
}