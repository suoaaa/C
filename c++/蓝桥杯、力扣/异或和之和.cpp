/*����һ������ Ai���ֱ�����ÿ���Ӷε����ͣ���������ǵĺ͡�
����˵������ÿ������ 1 �� L �� R �� n �� L, R ����������е� L ���� R ��Ԫ�ص����͡�
Ȼ�����ÿ�� L, R �õ��Ľ����������ֵ��
�������ʽ��
����ĵ�һ�а���һ������ n ��
�ڶ��а��� n ������ Ai ����������֮��ʹ��һ���ո�ָ���
�������ʽ��
���һ�а���һ��������ʾ�𰸡�*/ 
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
