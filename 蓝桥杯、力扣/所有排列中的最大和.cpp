/*��һ����������?nums?����һ����ѯ����?requests?������?requests[i] = [starti, endi]��
��?i?����ѯ��?nums[starti] + nums[starti + 1] + ... + nums[endi - 1] + nums[endi]?�Ľ����
starti ��endi������������ �� 0 ��ʼ �ġ�
������������� nums?�е����֣����㷵�����в�ѯ���֮�͵����ֵ��
���ڴ𰸿��ܻ�ܴ����㽫����?109 + 7?ȡ��?�󷵻ء�*/
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
        //ע�͵��ķ�����Ϊ�õ��ظ���++��ʹ��ʱ�临�Ӷ�Ϊn?����δע�͵��ķ���ֻ����2n+n��ʱ�临�Ӷ�

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