//实现日期类Date，它能正确表示年、月、日。
// 需要实现构成函数  Date(int y =1996,int m=1,int d=1)
// 在构造函数内依次判断输入的月和日是否合法
// 即月为1~12月中的一个 如果月不合法则输出Invalid month! 然后将月置为1。
// 如果天数不合法，则输出Invalid day! 然后将天数置为1。
// 例如 Date d(1993,13,32);
// 输出：
// Invalid month!回车
// Invalid day!回车
// 再调用 d.display()
// 输出：
// 1993-1-1
#include<iostream>
using namespace std;
class Date{
public:
      Date(int y ,int m,int d);//构造
      int days(int y,int m);//返回指定年月有多少天。
      void NewDay();//将当前Date表示的日期增加一天。
	void display()
 	{
    	cout<<year<<"-"<<month<<"-"<<day<<endl;
 	}
private:
      int year; //年
      int month; //月
      int day;  // 日
};
Date::Date(int y,int m,int d)
{
      year=y;
      if(m<0||m>12) month=1;
      else month=m;
      switch(month)
      {
            case 1:day=31;break;
            case 2:
                  if(!(year%100))
                  {
                        if(!(year%400)) day=29;
                        else day=28;
                  }
                  else
                  {
                        if(!(year%4)) day=29;
                        else day=28;
                  }
                  break;
            case 3:day=31;break;
            case 4:day=30;break;
            case 5:day=31;break;
            case 6:day=30;break;
            case 7:day=31;break;
            case 8:day=31;break;
            case 9:day=30;break;
            case 10:day=31;break;
            case 11:day=30;break;
            case 12:day=31;break;
            default:break;
      }
}

int  Date::days(int y,int m)
{
      int d=0;
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
void Date::NewDay()
{
      day++;
      int this_day=days(year,month);
      if(day>this_day) 
      {
            day=1;
            month++;
            if(month>12)
            {
                  month=1;
                  year++;
            }
      }
}