/*���ƻ���һ������ÿ�� �� a ����Ŀ, ����������ÿ���� b ����Ŀ��
�������, ���ռƻ������ڵڼ���ʵ�����������ڵ��� n ��?*/
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