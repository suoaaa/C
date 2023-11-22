/*����������ӵľ������ڼ��������Ľ��������������һ֧����÷���ߵ���ӡ���ӵĵ÷��������������Ա�ķ��� �ܺ� ��
Ȼ��������е�ì�ܻ�������Ա�ķ��ӣ����Ա���ѡ��һ֧ û��ì�� ����ӡ�
���һ�������С��Ա�ķ��� �ϸ���� һ������ϴ����Ա�������ì�ܡ�
ͬ����Ա֮�䲻�ᷢ��ì�ܡ�
���������б� scores �� ages������ÿ�� scores[i] �� ages[i] ��ʾ�� i ����Ա�ķ��������䡣���㷵�� ���п��ܵ���ì������е÷������֧�ķ��� ��*/
#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;
class Solution {
public:
    int bestTeamScore(vector<int>& scores, vector<int>& ages) {
        int n = scores.size();
        vector<pair<int, int>> people;
        for (int i = 0; i < n; ++i) {
            people.push_back({scores[i], ages[i]});
        }
        sort(people.begin(), people.end());
        vector<int> dp(n, 0);
        int res = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = i - 1; j >= 0; --j) {
                if (people[j].second <= people[i].second) {
                    dp[i] = max(dp[i], dp[j]);
                }
            }
            dp[i] += people[i].first;
            res = max(res, dp[i]);
        }
        return res;
    }
};