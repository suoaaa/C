/*����Ϊ����⣬ֻ��Ҫ���������ڴ�����ʹ�������佫������������ɡ�
�����ҵ���С������ X ͬʱ���㣺
X ��2019 ����������
X ��ÿһλ���ֶ���������*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
int main(){
    long long x=2019;
    bool flag=true;
    int i=1,j=0;
    while(flag){
        x=(long long)2019*i;
        // cout<<i;
        while(x>0){
            j=x%10;
            if(j%2==1) {x/=10;}
            else {i++;break;}
        }
        if(x==0) {flag=false;break;}
    }
    cout<<i*2019;
}