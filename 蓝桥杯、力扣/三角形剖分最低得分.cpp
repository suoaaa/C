/*����һ��͹��?n?���Σ���ÿ�����㶼��һ������ֵ������һ����������?values?������?values[i]?�ǵ� i �������ֵ���� ˳ʱ��˳�� ����
���轫����� �ʷ�?Ϊ n - 2?�������Ρ�
����ÿ�������Σ��������ε�ֵ�Ƕ����ǵĳ˻��������ʷֵķ����ǽ��������ʷֺ����� n - 2?�������ε�ֵ֮�͡�
���� ����ν��������ʷֺ���Եõ�����ͷ� ��*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<set>
#include<queue>
#include<algorithm>
using namespace std;
int minScoreTriangulation(vector<int> values) {
        int n=values.size();
        if(n<3)return 0;
        if(n==3) return values[0]*values[1]*values[2];
        vector<vector<int> > dp(n);
        for(int i=0;i<n;i++) dp[i]=vector<int>(n,0);
        for(int i=0;i<n-3;i++){
            for(int j=2+i;j<n;j++){
                for(int k=i+1;k<j;k++){
                    if(dp[i][j]==0){
                        dp[i][j]=dp[i][k]+dp[k][j]+values[i]*values[k]*values[j];
                    }else {
                        dp[i][j]=min(dp[i][j],dp[i][k]+dp[k][j]+values[i]*values[k]*values[j]);
                    }
                }
            }
        }
        return dp[0][n-1];
    }
    int main(){
        vector<int> n;
        n.push_back(3);
        n.push_back(7);
        n.push_back(4);
        n.push_back(5);
        cout<<minScoreTriangulation(n);
        return 0;
    }