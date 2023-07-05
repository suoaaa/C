/*有一个整数数组?nums?，和一个查询数组?requests?，其中?requests[i] = [starti, endi]。
第?i?个查询求?nums[starti] + nums[starti + 1] + ... + nums[endi - 1] + nums[endi]?的结果，
starti 和endi数组索引都是 从 0 开始 的。
你可以任意排列 nums?中的数字，请你返回所有查询结果之和的最大值。
由于答案可能会很大，请你将它对?109 + 7?取余?后返回。*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
#include<algorithm>
using namespace std;
class Solution {
public:
    int maxSumRangeQuery(vector<int>& nums, vector<vector<int>>& requests) {
        int n=nums.size();
        long long max_sum=0;
        vector<long long> times(n+1,0);
        //注释掉的方法因为用到重复的++，使得时间复杂度为n?，而未注释掉的方法只用了2n+n的时间复杂度

        // for(int i=0;i<requests.size();i++){
        //     for(int j=requests[i][0];j<=requests[i][1];j++){
        //         times[j]++;
        //     }
        // }
        for (auto &qi: requests) {
            times[qi[0]]++;
            times[qi[1] + 1]--;
        }
        for (int i = 1; i < n; i++)
            times[i] = times[i - 1] + times[i];
        sort(nums.begin(),nums.end());
        sort(times.begin(),times.end()-1);
        for(int i=0;i<n;i++){
            max_sum=(max_sum+nums[i]*times[i])%1000000007;
        }
        return max_sum;
    }
};