/*给你一个下标从 0 开始的二维整数数组 flowers ，其中 flowers[i] = [starti, endi] 表示第 i 朵花的 花期 从 starti 到 endi （都 包含）。
同时给你一个下标从 0 开始大小为 n 的整数数组 people ，people[i] 是第 i 个人来看花的时间。
请你返回一个大小为 n 的整数数组 answer ，其中 answer[i]是第 i 个人到达时在花期内花的 数目 。*/
#include<iostream>
#include<bits/stdc++.h>
using namespace std;
class Solution {
public:
    vector<int> fullBloomFlowers(vector<vector<int>>& flowers, vector<int>& people) {
        //初始化答案数组
        int n=people.size(),k=flowers.size();
        vector <int> answer(n);
        map<int,int> m;
        
        for(int i=0;i<k;i++){
            m[flowers[i][0]]++;
            m[flowers[i][1]+1]--;
        }
         vector<int> indices(n);
        iota(indices.begin(), indices.end(), 0);
        sort(indices.begin(), indices.end(), [&](int a, int b) {
            return people[a] < people[b];
        });

        auto it=m.begin();
        int tmp=0;
        for(int i=0;i<n;i++){

            while (it!=m.end() && it->first <= people[indices[i]]) {
                tmp+=it->second;
                it++;
            }
            answer[indices[i]]=tmp;
        }
        return answer;
    }
};