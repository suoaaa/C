/*如果日历中只显示年月日，请问从公元 1900 年 11 月 11 日到公元 99999 年 12 月 31 日，
一共有多少天日历上包含 2。即有多少天中年月日的数位中包含数字 2。*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
int leap(int year){
	if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
	return 1;
	else	return 0;
}
int main(){
    long long sum=0;
    int year=12*10+28+31;
    for(int i=1900;i<=9999;i++){
      if(i%10==2||i/10%10==2||i/100%10==2|i/1000==2)
        sum=sum+365+leap(i);
        else sum=sum+year+leap(i);
    }
    cout<<sum;
    return 0;
}