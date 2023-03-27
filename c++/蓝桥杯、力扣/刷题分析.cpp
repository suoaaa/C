/*他计划周一至周五每天 做 a 道题目, 周六和周日每天做 b 道题目。
请你计算, 按照计划他将在第几天实现做题数大于等于 n 题?*/
#include<iostream>
using namespace std;
int main(){
    long long a,b,n;
    cin>>a>>b>>n;
    long long week=5*a+2*b;
    long long day=n/week*7;
    n%=(week);
    if(5*a<=n){
        day=day+5+(n-5*a+b-1)/b;
    }else day=day+(n+a-1)/a;
    cout<<day;
    return 0;
}