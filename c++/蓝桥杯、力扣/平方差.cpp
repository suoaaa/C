/*���� L, R���� L �� x �� R ���ж��ٸ��� x ����������� y,z ʹ�� x = y*y-z*z 
�������ʽ��
����һ�а����������� L, R����һ���ո�ָ���
�������ʽ��
���һ�а���һ������������Ŀ���������� x ��������*/ 
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
