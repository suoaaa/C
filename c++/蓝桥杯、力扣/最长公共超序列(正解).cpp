/*���������ַ���?str1 ��?str2������ͬʱ��?str1?��?str2?��Ϊ�����е�����ַ���������𰸲�ֹһ��������Է�����������������һ���𰸡�

��������ַ��� T ��ɾ��һЩ�ַ���Ҳ���ܲ�ɾ��������ѡ������Щ�ַ�����λ�� T �е�?����λ�ã������Եõ��ַ��� S����ô?S ����?T �������У�*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
#include<algorithm>
using namespace std;
class Solution {
public:
    string shortestCommonSupersequence(string str1, string str2) {
        //�������������+˫ָ��
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
        //�����Ҵ�
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