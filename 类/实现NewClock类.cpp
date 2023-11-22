/*要求实现NewClock类，它继承至Clock类，NewClock类以12小时制显示时间。
实现NewClock类的showTime方法，在显示时间时需要显示当前是上午还是下午，
上午(0-11)用“AM”表示,下午(12-23)用“PM”表示。
NewClock类构造函数输入的时间仍以24小时表示。
例如：
NewClock nc(23,4,5);
nc.showtime()
输出：
Now:11:4:5PM回车
*/
#include <iostream>
#include <cstring>
using namespace std;
class Clock{
public:
	//Clock(){};
	Clock(int h,int m,int s){
	hour =(h>23? 0:h);
	minute = (m>59?0:m);
	second = (s>59?0:m);
	};
	virtual void run(){
		second = second+1;
		if (second>59)
		{
			second =0;
			minute+=1;
		}
		if (minute>59)
		{
			minute =0;
			hour+=1;
		}
		if (hour>23)
		{
			hour =0;
		}
	}
	virtual void showTime(){
		cout<<"Now:"<<hour<<":"<<minute<<":"<<second<<endl;
	}
	int getHour(){return hour;}
	int getMinute(){return minute;}
	int getSecond(){return second;}
	Clock *createNewClock(int h,int m,int s);
private:
	int hour;
	int minute;
	int second;
};
class NewClock:public Clock{
public:
	NewClock(int h,int m,int s):Clock(h,m,s)
	{
		real_hour =(h>23? 0:h);
		correct();
		minute = (m>59?0:m);
		second = (s>59?0:m);
	};
	void showTime();
	void run();
private:
	void correct()
	{
		if(real_hour>=12)
		{
			hour=real_hour-12;
		}
		else {hour=real_hour;};
	};
	int hour;
	int minute;
	int real_hour;
	int second;
};
void NewClock::run()
{
	second = second+1;
	if (second>59)
	{
		second =0;
		minute+=1;
	}
	if (minute>59)
	{
		minute =0;
		real_hour+=1;
		hour+=1;
	}
	if (real_hour>23)
	{
		real_hour =0;
	}
	correct();
}
void NewClock::showTime()
{
	if(real_hour>=12)
	{
		cout<<"Now:"<<hour<<":"<<minute<<":"<<second<<"PM"<<endl;
	}
	else cout<<"Now:"<<hour<<":"<<minute<<":"<<second<<"AM"<<endl;
}
Clock* Clock::createNewClock(int h,int m,int s){
	return new NewClock(h,m,s);
}
int main()
{
  int type;cin>>type;
  Clock C(1,1,1);
  Clock *NC = C.createNewClock(23,59,59);
  Clock *NC1 = C.createNewClock(11,59,59);
  switch(type){
  case 1:NC->showTime();break;
  case 2:NC->run();NC->showTime();break;
  case 3: NC1->run();NC1->showTime();}return 0;
}