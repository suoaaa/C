/*�������� 1,1,1,3,5,9,17,?�ӵ�4�ʼ��ÿ���ǰ3 ��ĺ͡�
��� 20190324 ������ 4 λ���֡�*/
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