/*给你一个下标从 0?开始的整数数组?nums?，判断是否存在?两个?长度为?2?的子数组且它们的?和?相等。
注意，这两个子数组起始位置的下标必须?不相同?。
如果这样的子数组存在，请返回?true，否则返回?false?。
子数组 是一个数组中一段连续非空的元素组成的序列。*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
class Solution {
public:
    bool findSubarrays(vector<int>& nums) {
        int num=nums.size();
        vector<int> sum(num-1);
        for(int i=0;i<num-1;i++){
            sum[i]=nums[i]+nums[i+1];
        }
        for (int i = 0; i < num-2; i++){
            for (int j = i+1; j < num-1; j++){
                if(sum[i]==sum[j]) return true;
            }
        }
        return false;
    }
};