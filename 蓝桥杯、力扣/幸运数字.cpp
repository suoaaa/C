/*小蓝认为如果一个数含有偶数个数位，并且前面一半的数位之和等于后面
一半的数位之和，则这个数是他的幸运数字。例如 2314 是一个幸运数字，因为
它有 4 个数位，并且 2 + 3 = 1 + 4 。现在请你帮他计算从 1 至 100000000 之间
共有多少个不同的幸运数字。*/                                                     //2413014
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
