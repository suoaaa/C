#include <iostream>
using namespace std;
#define MAX(x,y) (x)>(y)?(x):(y)
int flip();
int run_day();

int months[12]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
int w=5;                    //初始时周6下标为5
int road_all=0;             //记录总里程
int y=2000,m=1,d=1;
int main()
{
  int all_day=0;
  all_day=365*20+31+28+31+30+31+30+31+31+30+1+8+10;
  w=31-2;
  road_all=all_day+12*19+10+all_day/7;
  while(!(y==2020&&m==10))
  {

    m++;
    if(m==13) {y++;m=1;}
    w=(w+months[(m+11)%12]+flip())%7;
    if(w==0) road_all--;
  }
  cout<<road_all;
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