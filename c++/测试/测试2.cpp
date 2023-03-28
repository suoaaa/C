/*顺子日期指的就是在日期的 yyyymmdd 表示法中，
存在任意连续的三位数是一个顺子的日期。
小明想知道在整个 2022 年份中，一共有多少个顺子日期?*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
int main(){
    int months[12]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int month_index=0;
    int day=0;
    long long date=21;
    string s;

    s+=to_string(date);cout<<s<<endl;
    return 0;
}