/*假设你是球队的经理。对于即将到来的锦标赛，你想组合一支总体得分最高的球队。球队的得分是球队中所有球员的分数 总和 。
然而，球队中的矛盾会限制球员的发挥，所以必须选出一支 没有矛盾 的球队。
如果一名年龄较小球员的分数 严格大于 一名年龄较大的球员，则存在矛盾。
同龄球员之间不会发生矛盾。
给你两个列表 scores 和 ages，其中每组 scores[i] 和 ages[i] 表示第 i 名球员的分数和年龄。请你返回 所有可能的无矛盾球队中得分最高那支的分数 。*/
#include<iostream>
#include<vector>
using namespace std;
class Solution {
public:
    int bestTeamScore(vector<int>& scores, vector<int>& ages) {
        int n=scores.size();
        vector<bool> flag(n,false);
        return s(scores,ages,0,flag,n);
    }
    int s(vector<int>& scores, vector<int>& ages,int i,vector<bool> &flag,int &num){        
        if(i==num) return 0;
        for (int j = 0; j<i; j++)
        {
            if(flag[j]==true)   if((ages[j]<ages[i]&&scores[j]>scores[i])||(ages[j]>ages[i]&&scores[j]<scores[i]))
                return s(scores,ages,i+1,flag,num);
        }
        int now1=s(scores,ages,i+1,flag,num);
        flag[i]=true;
        int now2=scores[i]+s(scores,ages,i+1,flag,num);
        flag[i]=false;
        return max(now1,now2);
    }
    int max(int a,int b){return a>b?a:b;};
};