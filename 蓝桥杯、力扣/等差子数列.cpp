/*给你一个由 n 个整数组成的数组 nums，和两个由 m 个整数组成的数组 l 和 r，
后两个数组表示 m 组范围查询，其中第 i 个查询对应范围 [l[i], r[i]] 。
所有数组的下标都是 从 0 开始 的。
返回 boolean 元素构成的答案列表 answer 。
如果子数组 nums[l[i]], nums[l[i]+1], ... , nums[r[i]] 可以 重新排列 形成 等差数列 ，answer[i] 的值就是 true；否则answer[i] 的值就是 false 。
*/
#include <iostream>
#include<vector>
#include<algorithm>
using namespace std;
class Solution {
public:
    vector<bool> checkArithmeticSubarrays(vector<int>& nums, vector<int>& l, vector<int>& r) {
        vector<int> temp;
        int n=l.size();
        int k,d;
        vector<bool> answer(n,true);
        for(int i=0;i<n;i++){
            temp.assign(nums.data()+l[i],nums.data()+r[i]+1);
            // cout<<temp[0]<<" "<<temp.back()<<endl;
            sort(temp.begin(),temp.end());
            k=r[i]-l[i];
            d=temp.at(1)-temp.at(0);
            for(int j=1;j<k;j++){
                if(temp.at(j+1)-temp.at(j)!=d) {
                    answer[i]=false;
                    break;
                }
            }
        }
        return answer;
    }
};