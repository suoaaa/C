/*˳������ָ�ľ��������ڵ� yyyymmdd ��ʾ���У�
����������������λ����һ��˳�ӵ����ڡ�
С����֪�������� 2022 ����У�һ���ж��ٸ�˳������?*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
int main(){
    int months[12]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int month_index=0;
    int day=1,sum=0;
    string date;
    while(month_index<12){
        date=to_string(2022)+
        	(month_index+1>9?to_string(month_index+1):to_string(0)+to_string(month_index+1))+
            (day>9?to_string(day):to_string(0)+to_string(day));
        for(int i=2;i<6;i++){
                if(date[i]+date[i+2]==2*date[i+1]&&date[i+1]-date[i]==1)
                {sum++;break;}
        }
        day++;
        if(day>months[month_index]) {
            day=1;
            month_index++;
        }
    }
    cout<<sum;
}