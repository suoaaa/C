/*С����Ϊ���һ��������ż������λ������ǰ��һ�����λ֮�͵��ں���
һ�����λ֮�ͣ���������������������֡����� 2314 ��һ���������֣���Ϊ
���� 4 ����λ������ 2 + 3 = 1 + 4 ������������������ 1 �� 100000000 ֮��
���ж��ٸ���ͬ���������֡�*/                                                     //2413014
#include<bits/stdc++.h>
using namespace std;
int main(){
	string str;
	int num=0;
	int sum1=0,sum2=0,tmp=0;
	for(int i=1;i<10000;i++){
		sum1=0;
		tmp=i;
		while(tmp!=0){
			sum1+=tmp%10;
			tmp/=10;
		}
		for(int j=0;j<=i;j++){
			sum2=0;
			tmp=j;
			while(tmp!=0){
			sum2+=tmp%10;
			tmp/=10;
			}		
			if(sum1==sum2) num++;
		}

	}
	cout<<num;
	return 0;
} 
