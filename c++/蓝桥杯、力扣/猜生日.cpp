/*�����ֲ���ڣ�2012��3��12 �գ�
����˵�������ҳ�����������������ƴ��һ�� 8 λ�����¡��ղ�����λǰ�� 0��
���ÿ��Ա�������ꡢ�¡�����������
�������룬�ֲ��䵽�����ٸ�����ʾ������ 6�³����ġ���*/
//19550604
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
int main(){
    int year=2011;
    int day=2;
    long date=0;
    bool flag=true;
    while(flag){
        date=year*10000+600+day;
        if(date%12==0&&date%2012==0){
            flag=false;
            break;
        }else {
            day+=2;
            if(day>31) {
                day=2;
                year--;
            }
        }
    }
    cout<<date;
}