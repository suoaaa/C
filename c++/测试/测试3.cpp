#include <cstring>
#include <iostream>
using namespace std;
int dp[1001][1001];
void MatrixChain(int *p, int n, int**m ,int**s)
{
 	for(int i=1; i<= n; i++) m[i][i]=0; //��С�������⣬�������Ϊ1
 	for(int r= 2; r<= n; r++)  //r����������ľ����������2��n
	{
   	for(int i=1;i<=n-r+1; i++){  //����n-r+1�����
 		int j=i+r-1;
        m[i][j]=m[i+1][j]+p[i-1]*p[i]*p[j];//����ÿ�ζ��ӵ�i�������ֿ�
 		s[i][j]=i;
 		for(int k=i+1;k<j;k++){  //����k�����п������
 		int t= m[i][k]+m[k+1][j]+ p[i-1]*p[k]*p[j];
        if(t<m[i][j]) {m[i][j]=t; s[i][j]=k;}}}//ѡ�����˴�����С�����������������ݸ����ڱ��е���Ӧλ��
    }
}