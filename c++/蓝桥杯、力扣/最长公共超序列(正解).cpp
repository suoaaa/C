/*给出两个字符串?str1 和?str2，返回同时以?str1?和?str2?作为子序列的最短字符串。如果答案不止一个，则可以返回满足条件的任意一个答案。

（如果从字符串 T 中删除一些字符（也可能不删除，并且选出的这些字符可以位于 T 中的?任意位置），可以得到字符串 S，那么?S 就是?T 的子序列）*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
#include<algorithm>
using namespace std;
class Solution {
public:
    string shortestCommonSupersequence(string str1, string str2) {
        //找最长公共子序列+双指针
        int len1=str1.size(),len2=str2.size();
        int dp[len1+1][len2+1];
        memset(dp,0,sizeof(dp));
        for(int i=1;i<=len1;++i)
        {
            for(int j=1;j<=len2;++j)
            {
                if(str1[i-1]==str2[j-1])
                {
                    dp[i][j]=dp[i-1][j-1]+1;
                }
                else
                {
                    dp[i][j]=max(dp[i][j-1],dp[i-1][j]);
                }
            }
        }
        //回溯找答案
        string ans;
        int x=len1,y=len2;
        while(x!=0||y!=0)
        {
            if(x==0)
            {
                ans+=str2[y-1];
                y--;
                continue;
            }
            if(y==0)
            {
                ans+=str1[x-1];
                x--;
                continue;
            }
            if(str1[x-1]==str2[y-1])
            {
                ans+=str1[x-1];
                x--;
                y--;
            }
            else
            {
                if(dp[x-1][y]>dp[x][y-1])
                {
                    ans+=str1[x-1];
                    x--;
                }
                else
                {
                    ans+=str2[y-1];
                    y--;
                }
            }
        }
        reverse(ans.begin(),ans.end());
        return ans;
    }
};