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
class Date{
public:
      Date(int y ,int m,int d)
      {
            year=y;
            if(m<0||m>12) month=1;
            else month=m;
            if(d>)
      };
      int days(int year,int month){};
      void NewDay();
	void display()
 	{
    	cout<<year<<"-"<<moth<<"-"<<day<<endl;
 	}
private:
      int year; //年
      int month; //月
      int day;  // 日
}



设计一个NewDay()成员函数，将当前Date表示的日期增加一天。
例如：Date d(1991,1,1);
      d.NewDay();
      d.display();
输出：
1991-1-2

设计一个days(int year, int month)成员函数,返回指定年月有多少天。
例如 d.days(1991,1)
返回：
31

在你的代码中必须实现以上三个方法，例如：
#include "CDate.h"
Date::Date(int y=1996,int m=1 ,int d=1){
    ...
}
int  Date::days(int year,int month){
    ...
}
void Date::NewDay(){
  ...
}
