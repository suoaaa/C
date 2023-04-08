/*小蓝正在一个瓜摊上买瓜。瓜摊上共有 n 个瓜，每个瓜的重量为 Ai 。
小蓝刀功了得，他可以把任何瓜劈成完全等重的两份，不过每个瓜只能劈一刀。
小蓝希望买到的瓜的重量的和恰好为 m 。
请问小蓝至少要劈多少个瓜才能买到重量恰好为 m 的瓜。如果
无论怎样小蓝都无法得到总重恰好为 m 的瓜，请输出 -1 。
【输入格式】
输入的第一行包含两个整数 n, m，用一个空格分隔，分别表示瓜的个数和小蓝想买到的瓜的总重量。
第二行包含 n 个整数 Ai，相邻整数之间使用一个空格分隔，分别表示每个瓜的重量。*/ 
#include<bits/stdc++.h>
using namespace std;
int min(int i,int j,int k){
	if(i>j) return min(j,k);
	else return min(i,k);
}
int n=0;
int m=0;
vector<float> v;
int dp(float sumNow,int index,int num){
	if(sumNow==m) return num;
	if(sumNow>m||index>=n) return n+1;
	if(sumNow+v[index]<=m)	return min(dp(sumNow+v[index],index+1,num),dp(sumNow+v[index]/2,index+1,num+1),dp(sumNow,index+1,num));
	if(sumNow+v[index]/2<=m)	return min(dp(sumNow+v[index]/2,index+1,num+1),dp(sumNow,index+1,num));
	return dp(sumNow,index+1,num);
}
int main(){
	cin>>n>>m;
	v=vector<float> (n);
	for(int i=0;i<n;i++){
		cin>>v[i];
	}
	int j=dp(0.0,0,0);
	if(j==n+1) cout<<-1;
	else cout<<j;
	return 0;
} 
