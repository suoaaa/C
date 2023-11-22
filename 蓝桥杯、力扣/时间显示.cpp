/*小蓝要和朋友合作开发一个时间显示的网站。
在服务器上，朋友已经获取了当前的时间，用一个整数表示，值为从 
1970 年 11 月 11 日 00:00:00 到当前时刻经过的毫秒数。
现在，小蓝要在客户端显示出这个时间。不用显示出年月日，只需要显示出时分秒即可，
毫秒也不用显示，直接舍去即可。给定一个用整数表示的时间，请将这个时间对应的时分秒输出。*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
int main(){
    long long n=0;
    cin>>n;
    n/=1000;
    long long day=60*60*24;
    int h,m,s;
    n%=day;
    h=n/60/60;
    m=(n-h*60*60)/60;
    s=n-h*60*60-m*60;
    printf("%0.2d:%0.2d:%0.2d",h,m,s);
    return 0;
}