/*今年的植树节（2012年3月12 日）
叔叔说：“把我出生的年月日连起来拼成一个 8 位数（月、日不足两位前补 0）
正好可以被今天的年、月、日整除！”
他想了想，又补充到：“再给个提示，我是 6月出生的。”*/
//19550604
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
int main(){
    int year=2011;
    int day=2;
    long date=0;
    bool flag=true;
    while(flag){
        date=year*10000+600+day;
        if(date%12==0&&date%2012==0){
            flag=false;
            break;
        }else {
            day+=2;
            if(day>31) {
                day=2;
                year--;
            }
        }
    }
    cout<<date;
}