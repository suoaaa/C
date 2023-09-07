/*
给你一个整数数组 ranks ，表示一些机械工的 能力值 。ranksi 是第 i 位机械工的能力值。
能力值为 r 的机械工可以在 r * n2 分钟内修好 n 辆车。同时给你一个整数 cars ，表示总共需要修理的汽车数目。
请你返回修理所有汽车 最少 需要多少时间。
注意：所有机械工可以同时修理汽车。
示例 1：
输入：ranks = [4,2,3,1], cars = 10
输出：16
解释：
- 第一位机械工修 2 辆车，需要 4 * 2 * 2 = 16 分钟。
- 第二位机械工修 2 辆车，需要 2 * 2 * 2 = 8 分钟。
- 第三位机械工修 2 辆车，需要 3 * 2 * 2 = 12 分钟。
- 第四位机械工修 4 辆车，需要 1 * 4 * 4 = 16 分钟。
16 分钟是修理完所有车需要的最少时间。*/
#include<iostream>
#include <vector>
using namespace std;
class Solution {
public:
    long long repairCars(vector<int>& ranks, int cars) {
        
    }



    int max(vector<int> ranks){
        int num=0;
        for (int i=0;i<ranks.size()-1;i++){
            if (ranks[i]>=num){
                num=ranks[i];
            }
        }
        return num;
    }
    void sort(vector<int> ranks){
        int temp;
        for (int i=0;i<ranks.size()-1;i++){
            for(int j=i+1;j<ranks.size()-i;j++){
                if(ranks[i]<ranks[j]){
                    temp=ranks[i];
                    ranks[i]=ranks[j];
                    ranks[j]=temp;
                }
            }
        }
    }
};