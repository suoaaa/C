/*给你一个正整数数组 arr（可能存在重复的元素），请你返回可在?一次交换（交换两数字 arr[i] 和 arr[j] 的位置）后
得到的、按字典序排列小于 arr 的最大排列。如果无法这么操作，就请返回原数组。

输入：arr = [3,2,1]
输出：[3,1,2]
解释：321>312且是最大的比321小的序列*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<set>
#include<queue>
#include<algorithm>
using namespace std;
class Solution {
public:
    vector<int> prevPermOpt1(vector<int>& arr) {
        int n=arr.size();
        int i=n-2,j=n-1;
        while(i>=0&&arr[i]<=arr[i+1]) i--;
        if(i<0) return arr;
        while(arr[i]<=arr[j]) j--;
        while(arr[j]==arr[j-1]) j--;
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
        return arr;
    }
};