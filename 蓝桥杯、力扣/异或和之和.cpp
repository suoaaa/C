/*给定一个数组 Ai，分别求其每个子段的异或和，并求出它们的和。
或者说，对于每组满足 1 ≤ L ≤ R ≤ n 的 L, R ，求出数组中第 L 至第 R 个元素的异或和。
然后输出每组 L, R 得到的结果加起来的值。
【输入格式】
输入的第一行包含一个整数 n 。
第二行包含 n 个整数 Ai ，相邻整数之间使用一个空格分隔。
【输出格式】
输出一行包含一个整数表示答案。*/ 
#include<bits/stdc++.h>
using namespace std;
vector<vector<int > > dp;
int main(){
	int n,tmp1=0,tmp2=0;
	int sum=0;
	cin>>n;
	dp=vector<vector<int > >(n);
	for(int i=0;i<n;i++){
		dp[i]=vector<int>(n,0);
		cin>>dp[i][i];
	}
	for(int i=0;i<n;i++){
		for(int len=0;len+i<n;len++){
			int j=len+i;
			if(j!=i)	dp[i][j]=dp[i][j-1]^dp[j][j];		
			sum+=dp[i][j];
		}

	}
	cout<<sum;
	return 0;
} 
