/*小蓝有一个长度均为 n 且仅由数字字符 0 ～ 9 组成的字符串，下标从 0 到n - 1，
你可以将其视作是一个具有 n 位的十进制数字 num，小蓝可以从 num 中选出一段连续的子串并将子串进行反转，
最多反转一次。小蓝想要将选出的子串进行反转后再放入原位置处得到的新的数字 numnew 满足条件 numnew < num，
请你帮他计算下一共有多少种不同的子串选择方案，只要两个子串在 num 中的
位置不完全相同我们就视作是不同的方案。
注意，我们允许前导零的存在，即数字的最高位可以是 0 ，这是合法的。
【输入格式】
输入一行包含一个长度为 n 的字符串表示 num（仅包含数字字符 0 ～ 9），从左至右下标依次为 0 ～ n - 1。
【输出格式】
输出一行包含一个整数表示答案。*/ 
#include<bits/stdc++.h>
using namespace std;
string s;
bool fun(int l,int r,int n){
//	cout<<l<<' '<<r;
	if(l>=r||l<0||r>=n) return false;
	for(int i=0;i<=(r-l)/2&&l+i<=r-i;i++){
		if(s[l+i]>s[r-i]) return true;
		if(s[l+i]<s[r-i]) return false;
		if(s[l+i]==s[r-i]) return fun(l+i+1,r-i-1,n);
	}
}
int main(){
	int num=0;
	cin>>s;
	int n=s.size();
//	cout<<n;
	for(int i=0;i<n-1;i++){
		for(int j=i+1;j<n;j++){
			if(fun(i,j,n)==true) {
				num++;
//				cout<<i<<' '<<j<<endl;
			}
		}
	}
	cout<<num;
	return 0;
} 
