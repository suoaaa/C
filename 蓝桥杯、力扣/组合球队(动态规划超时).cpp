/*����������ӵľ������ڼ��������Ľ��������������һ֧����÷���ߵ���ӡ���ӵĵ÷��������������Ա�ķ��� �ܺ� ��
Ȼ��������е�ì�ܻ�������Ա�ķ��ӣ����Ա���ѡ��һ֧ û��ì�� ����ӡ�
���һ�������С��Ա�ķ��� �ϸ���� һ������ϴ����Ա�������ì�ܡ�
ͬ����Ա֮�䲻�ᷢ��ì�ܡ�
���������б� scores �� ages������ÿ�� scores[i] �� ages[i] ��ʾ�� i ����Ա�ķ��������䡣���㷵�� ���п��ܵ���ì������е÷������֧�ķ��� ��*/
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