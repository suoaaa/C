/*你有一个凸的?n?边形，其每个顶点都有一个整数值。给定一个整数数组?values?，其中?values[i]?是第 i 个顶点的值（即 顺时针顺序 ）。
假设将多边形 剖分?为 n - 2?个三角形。
对于每个三角形，该三角形的值是顶点标记的乘积，三角剖分的分数是进行三角剖分后所有 n - 2?个三角形的值之和。
返回 多边形进行三角剖分后可以得到的最低分 。*/
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