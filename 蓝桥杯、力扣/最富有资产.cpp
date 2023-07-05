/*给你一个 m x n 的整数网格 accounts,其中accounts[i][j]是第i????????????位客户在第j家银行
托管的资产数量。返回最富有客户所拥有的 资产总量 。
客户的 资产总量 就是他们在各家银行托管的资产数量之和。
最富有客户就是 资产总量 最大的客户。*/
#include<iostream>
#include<vector>
using namespace std;
class Solution {
public:
    int maximumWealth(vector<vector<int>>& accounts) {
        vector<int> ret(accounts.size());
        int max=0;
        for(int i=0;i<accounts.size();i++){
            ret[i]=accounts[i][0];
            for(int j=1;j<accounts[i].size();j++){
                ret[i]+=accounts[i][j];
            }
            max=max>ret[i]? max:ret[i];
        }
        return max;
    }
};
int main()
{
    vector<vector<int>> accounts;
    accounts.resize(3);
    for(int i=0;i<3;i++) {
        accounts[i].resize(4);
        for(int j=0;j<4;j++)
        accounts[i][j]=1;
        }
        Solution s;
        cout<<accounts[0].size();
            cout<<s.maximumWealth(accounts);
    return 0;

}