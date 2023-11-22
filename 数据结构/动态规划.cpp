/*ʵ��Ҫ��:ʵ��Ҫ��:
������������: int BestValue(int[] row,int [] col, int n):
��������:int[]row,�洢n�����������;int[]col,�洢n�����������,int nΪ�������
�������:��������ֵΪ������ľ���������С�˷��������������������error*/
#include <cstring>
#include <iostream>
using namespace std;
int dp[200][200];
int BestValue(int row[], int col[], int n) {
    if(row<=0 || col<=0 || n <= 0)
    {   printf("error");    return 0;   }
    for (int i = 0; i < n; i++)
        if(row[i] <= 0 || col[i] <= 0)
        {   printf("error");    return 0;   }
    for (int i = 1; i < n; i++)
        if (col[i - 1] != row[i]) 
        {   printf("error");    return 0;   }
    memset(dp, 10000, sizeof(dp));
    for (int i = 0; i < n; i++)
        dp[i][i] = 0;
    for (int l = 1; l < n; l++)
        for (int i = 0; i + l < n; i++)
            for (int k = 0; k < l; k++)
                dp[i][i + l] = min(dp[i][i + l], dp[i][i + k] + dp[i + k + 1][i + l] + row[i] * col[i + k] * col[i + l]);
    return dp[0][n - 1];
}
int main()
{
    int a[20],b[20],i;
    cin>>i;
    for(int j=0;j<i;j++) cin>>a[j]>>b[j];
    i=BestValue(a,b,i);
    if(i) cout<<i;
    return 0;
}