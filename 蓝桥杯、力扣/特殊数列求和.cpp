/*给定数列 1,1,1,3,5,9,17,?从第4项开始，每项都是前3 项的和。
求第 20190324 项的最后 4 位数字。*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
int main(){
    int arr[4]={1,1,1,3};
    int i=0;
    for(int i=5;i<=20190324;i++){
        arr[(i-1)%4]=(arr[(i-2)%4]+arr[(i-3)%4]+arr[(i-4)%4])%10000;
    }
    cout<<arr[(20190324-1)%4];
}