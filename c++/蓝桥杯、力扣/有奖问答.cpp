/*小蓝正在参与一个现场问答的节目。活动中一共有 30 道题目，每题只有答对和答错两种情况，
每答对一题得 10 分，答错一题分数归零。小蓝可以在任意时刻结束答题并获得目前分数对应的奖项，
之后不能再答任何题目。最高奖项需要 100 分，所以到达 100 分时小蓝会直接停止答题。
已知小蓝最终实际获得了 70 分对应的奖项，请问小蓝所有可能的答题情况有多少种?*/
#include<bits/stdc++.h>
using namespace std;
int fun(int n){
		int ret=0;
		for(int i=1;i<n;i++){		
		}
} 

int main(){
	int sum=2048;//1022972		//1027849
	float tmp=0,r1,l1;
	for(int i=19;i<27;i++){
		tmp+=pow(2,i-8);
		for(int r=0,l=i-18;r>=0&&l>=0;r++,l--){
			if(r>=1) r1=pow(2,r-1);
			else r1=pow(2,r);
			if(l>=1) l1=pow(2,l-1);
			else l1=pow(2,l);
			tmp-=r1*l1;
		}
		sum+=tmp;
	}
	cout<<sum;
	return 0;
} 
