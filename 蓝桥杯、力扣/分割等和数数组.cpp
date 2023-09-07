/*请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。*/
#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;
class Solution {
public:
    bool canPartition(vector<int>& nums) {
        int sum=0,n=nums.size(),maxNum=0;
        for(int i=0;i<nums.size();i++)
            {
                sum+=nums[i];
                maxNum=maxNum>nums[i]?maxNum:nums[i];
            }
        if(sum%2!=0||n<2||maxNum*2>sum) return false;
        int target=sum/2;
        vector<int> dp(target + 1, 0);
        dp[0] = true;
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            for (int j = target; j >= num; --j) {
                dp[j] |= dp[j - num];
            }
        }
        return dp[target];
    }
};