#include <iostream>
using namespace std;
#define MAX(x,y) (x)>(y)?(x):(y)
int flip();

int months[12]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
int w=5;                    //初始时周6下标为5
int road_all=0;             //记录总里程
int y=2000,m=1,d=1;
int main()
{
  int all_day=0;
  all_day=365*20+31+28+31+30+31+30+31+31+30+1+6;//6是闰二月天数
  w=31-2;
  road_all=all_day+12*20+10+all_day/7+1;//除7加一是周一的天数，前边是月初的天数
  while(!(y==2020&&m==10))
  {

    m++;
    if(m==13) {y++;m=1;}
    w=(w+months[(m+11)%12]+flip())%7;
    if(w==0) road_all--;
  }
  cout<<road_all;//8879
  return 0;
}
int flip()
{
  int ret = 0;
  if(m!=3) ret=0;
  else
  {
    if(!(y%400))ret+=1;
    else if(!(y%100)) ret+=0;
    else if(!(y%4)) ret+=1;
  }
  return ret;
}