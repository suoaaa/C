/*С������һ����̯����ϡ���̯�Ϲ��� n ���ϣ�ÿ���ϵ�����Ϊ Ai ��
С�������˵ã������԰��κι�������ȫ���ص����ݣ�����ÿ����ֻ����һ����
С��ϣ���򵽵Ĺϵ������ĺ�ǡ��Ϊ m ��
����С������Ҫ�����ٸ��ϲ���������ǡ��Ϊ m �Ĺϡ����
��������С�����޷��õ�����ǡ��Ϊ m �Ĺϣ������ -1 ��
�������ʽ��
����ĵ�һ�а����������� n, m����һ���ո�ָ����ֱ��ʾ�ϵĸ�����С�����򵽵Ĺϵ���������
�ڶ��а��� n ������ Ai����������֮��ʹ��һ���ո�ָ����ֱ��ʾÿ���ϵ�������*/ 
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
