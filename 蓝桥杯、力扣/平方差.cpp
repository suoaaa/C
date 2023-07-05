/*给定 L, R，问 L ≤ x ≤ R 中有多少个数 x 满足存在整数 y,z 使得 x = y*y-z*z 
【输入格式】
输入一行包含两个整数 L, R，用一个空格分隔。
【输出格式】
输出一行包含一个整数满足题目给定条件的 x 的数量。*/ 
#include<bits/stdc++.h>
using namespace std;
int main(){
	int l,r,num=0;
	cin>>l>>r;
	long tmp=0;
	long y,z;
	for(float i=l;i<=r;i++){
		tmp=sqrt(i);
		if(tmp*tmp==i) {
			num++;
			continue;
		}
		z=1;
		while(z<=tmp){
			y=i/z;
			if(y*z==i&&(y-z)%2==0&&y!=z){
				num++;
				break;
			}z++;
		}
	}
	cout<<num;
	return 0;
} 
