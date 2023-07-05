/*小明对数位中含有 2、0、1、9 的数字很感兴趣（不包括前导 0），
在 1 到 40 中这样的数包括 1、2、9、10 至 32、39 和 40，共 28 个，他们的和是 574。
请问，在 1 到 n 中，所有这样的数的和是多少？*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
int main(){
    int n=0;
    cin>>n;
    int sum=0;
    int num=0;
    for(int i=1;i<=n;i++){
        num=i;
        while(num!=0){
            if(num%10==2||num%10==0||num%10==1||num%10==29) {
                sum++;
                break;
            }else {
                num/=10;
            }
        }
    }
    cout<<sum;
}