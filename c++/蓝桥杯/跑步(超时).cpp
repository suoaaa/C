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
  road_all+=2;
  while(!(y==2020&&m==10&&d==1))
  {
    run_day();
    if(d==1||w==0) road_all+=2;
    else road_all+=1;
  }
  cout<<road_all;
  return 0;
}
int flip()
{
  int ret = 0;
  if(!(y%400))ret=1;
  else if(!(y%100)) ret=0;
  else if(!(y%4)) ret=1;
  return ret;
}
int run_day()
{
  d++;
  if(m==2&&d>months[1]+flip())
  {
    d=1;m++;
  }
  else if(d>months[m-1])
  {
    d=1;m++;
    if(m>12) {m=1;y++;}
  }
  w=(w+1)%7;
}