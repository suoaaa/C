/*С������λ�к��� 2��0��1��9 �����ֺܸ���Ȥ��������ǰ�� 0����
�� 1 �� 40 �������������� 1��2��9��10 �� 32��39 �� 40���� 28 �������ǵĺ��� 574��
���ʣ��� 1 �� n �У��������������ĺ��Ƕ��٣�*/
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