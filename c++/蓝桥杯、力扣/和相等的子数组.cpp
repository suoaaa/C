/*����һ���±�� 0?��ʼ����������?nums?���ж��Ƿ����?����?����Ϊ?2?�������������ǵ�?��?��ȡ�
ע�⣬��������������ʼλ�õ��±����?����ͬ?��
�����������������ڣ��뷵��?true�����򷵻�?false?��
������ ��һ��������һ�������ǿյ�Ԫ����ɵ����С�*/
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