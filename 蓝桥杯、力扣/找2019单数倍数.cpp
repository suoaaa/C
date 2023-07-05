/*本题为填空题，只需要算出结果后，在代码中使用输出语句将所填结果输出即可。
请你找到最小的整数 X 同时满足：
X 是2019 的整倍数；
X 的每一位数字都是奇数。*/
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