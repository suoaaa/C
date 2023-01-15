#include <cstring>
#include <iostream>
using namespace std;
int dp[1001][1001];
void MatrixChain(int *p, int n, int**m ,int**s)
{
 	for(int i=1; i<= n; i++) m[i][i]=0; //最小的子问题，矩阵个数为1
 	for(int r= 2; r<= n; r++)  //r代表子问题的矩阵个数，从2到n
	{
   	for(int i=1;i<=n-r+1; i++){  //分析n-r+1种情况
 		int j=i+r-1;
        m[i][j]=m[i+1][j]+p[i-1]*p[i]*p[j];//假设每次都从第i个矩阵后分开
 		s[i][j]=i;
 		for(int k=i+1;k<j;k++){  //讨论k的所有可能情况
 		int t= m[i][k]+m[k+1][j]+ p[i-1]*p[k]*p[j];
        if(t<m[i][j]) {m[i][j]=t; s[i][j]=k;}}}//选择数乘次数最小的情况，并将相关数据覆盖于表中的相应位置
    }
}