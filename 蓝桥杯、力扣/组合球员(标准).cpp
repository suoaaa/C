/*假设你是球队的经理。对于即将到来的锦标赛，你想组合一支总体得分最高的球队。球队的得分是球队中所有球员的分数 总和 。
然而，球队中的矛盾会限制球员的发挥，所以必须选出一支 没有矛盾 的球队。
如果一名年龄较小球员的分数 严格大于 一名年龄较大的球员，则存在矛盾。
同龄球员之间不会发生矛盾。
给你两个列表 scores 和 ages，其中每组 scores[i] 和 ages[i] 表示第 i 名球员的分数和年龄。请你返回 所有可能的无矛盾球队中得分最高那支的分数 。*/
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