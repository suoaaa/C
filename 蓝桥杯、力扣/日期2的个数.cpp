/*���������ֻ��ʾ�����գ����ʴӹ�Ԫ 1900 �� 11 �� 11 �յ���Ԫ 99999 �� 12 �� 31 �գ�
һ���ж����������ϰ��� 2�����ж������������յ���λ�а������� 2��*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
int leap(int year){
	if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
	return 1;
	else	return 0;
}
int main(){
    long long sum=0;
    int year=12*10+28+31;
    for(int i=1900;i<=9999;i++){
      if(i%10==2||i/10%10==2||i/100%10==2|i/1000==2)
        sum=sum+365+leap(i);
        else sum=sum+year+leap(i);
    }
    cout<<sum;
    return 0;
}