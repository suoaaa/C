/*有 N 堆石头排成一排，第 i 堆中有?stones[i]?块石头。

每次移动（move）需要将连续的?K?堆石头合并为一堆，而这个移动的成本为这?K?堆石头的总数。

找出把所有石头合并成一堆的最低成本。如果不可能，返回 -1 。*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
#include<algorithm>
using namespace std;
class Solution {
    static constexpr int inf = 0x3f3f3f3f;
public:
    int mergeStones(vector<int>& stones, int k) {
        int n = stones.size();
        if ((n - 1) % (k - 1) != 0) {
            return -1;
        }

        vector<vector<vector<int > > > d(n);
        for(int i=0;i<n;i++){
            d[i]=vector<vector<int > >(n);
            for(int j=0;j<n;j++){
                d[i][j]=vector<int>(k+1,inf);
            }
        }
        vector<int> sum(n, 0);

        for (int i = 0, s = 0; i < n; i++) {
            d[i][i][1] = 0;
            s += stones[i];
            sum[i] = s;
        }

        for (int len = 2; len <= n; len++) {
            for (int l = 0; l < n && l + len - 1 < n; l++) {
                int r = l + len - 1;
                for (int t = 2; t <= k; t++) {
                    for (int p = l; p < r; p += k - 1) {
                        d[l][r][t] = min(d[l][r][t], d[l][p][1] + d[p + 1][r][t - 1]);
                    }
                }
                d[l][r][1] = min(d[l][r][1], d[l][r][k] + 
                                sum[r] - (l == 0 ? 0 : sum[l - 1]));
            }
        }
        return d[0][n - 1][1];
    }
};
