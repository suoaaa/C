/*С����һ�����Ⱦ�Ϊ n �ҽ��������ַ� 0 �� 9 ��ɵ��ַ������±�� 0 ��n - 1��
����Խ���������һ������ n λ��ʮ�������� num��С�����Դ� num ��ѡ��һ���������Ӵ������Ӵ����з�ת��
��෴תһ�Ρ�С����Ҫ��ѡ�����Ӵ����з�ת���ٷ���ԭλ�ô��õ����µ����� numnew �������� numnew < num��
�������������һ���ж����ֲ�ͬ���Ӵ�ѡ�񷽰���ֻҪ�����Ӵ��� num �е�
λ�ò���ȫ��ͬ���Ǿ������ǲ�ͬ�ķ�����
ע�⣬��������ǰ����Ĵ��ڣ������ֵ����λ������ 0 �����ǺϷ��ġ�
�������ʽ��
����һ�а���һ������Ϊ n ���ַ�����ʾ num�������������ַ� 0 �� 9�������������±�����Ϊ 0 �� n - 1��
�������ʽ��
���һ�а���һ��������ʾ�𰸡�*/ 
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
