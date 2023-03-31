/* 给你一个下标从 0 开始、严格递增 的整数数组 nums 和一个正整数 diff 。如果满足下述全部条件，则三元组 (i, j, k) 就是一个 算术三元组 ：
i < j < k ，
nums[j] - nums[i] == diff 且
nums[k] - nums[j] == diff
返回不同 算术三元组 的数目。*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<set>
#include<queue>
#include<algorithm>
using namespace std;
class Solution {
public:
    int arithmeticTriplets(vector<int>& nums, int diff) {
        int n=nums.size();
        int num=0;
        for(int i=1;i<n-1;i++){
            for(int j=i-1;j>0;j--){
                if(nums[i]-nums[j]==diff) {
                    for(int k=i+1;k<n;k++){
                        if(nums[k]-nums[i]==diff) num++;
                    }
                }
            }
        }
    }
};