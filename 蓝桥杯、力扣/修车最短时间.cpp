/*给你一个整数数组 ranks ，表示一些机械工的 能力值 。ranksi 是第 i 位机械工的能力值。
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
#include<string.h>
#include <vector>
using namespace std;
class Solution {
public:
    int cars;
    int size;

    long long repairCars(vector<int>& ranks, int cars) {
        size =ranks.size();
        this->cars=cars;
        int min=100,max=0;
        for(int i=0;i<size;i++){
            if(ranks[i]>max) max=ranks[i];
            if(ranks[i]<min) min=ranks[i];
        }
        long long timeMax,timeMin;
        timeMax = ((long long)(max))*cars*cars;
        timeMin =(long long) min*cars*cars;
        timeMin=0;
        int num=0;
        long long time=timeMin+timeMax;
        time/=2;
        //  printf("num:%d,timeMax:%d,timeMin:%d;\n",num,timeMax,timeMin);
        while(true){
            num=0;
            time=timeMin+timeMax;
            time/=2;
            for(int i=0;i<ranks.size();i++){
                int j=0;
                while((long long)(ranks[i])*j*j<=time){
                    j++;
                }
                num+=j-1;
            }
            // printf("num:%d,timeMax:%d,timeMin:%d;\n",num,timeMax,timeMin);

            if(num>=cars) timeMax=time;
            else if (timeMax==timeMin+1&&num<cars) timeMin++;
            else if(num<cars) timeMin=time;
            if (timeMax==timeMin) return time+1;
        }
    }
};
int main(){
    Solution s;
    vector<int> ranks;
    ranks.resize(4);
    for(int i=0;i<4;i++){
        ranks[i]=i+1;
    }
    cout<<s.repairCars(ranks,10);

}

