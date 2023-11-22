#include<iostream>
#include<vector>
using namespace std;
class Solution {
public:
    vector<int> runningSum(vector<int>& nums) {
        vector<int> out(nums.size());
        out[0]=nums[0];
        for(int i=1;i<nums.size();i++)	out[i]=out[i-1]+nums[i];
        return out;
    }
};