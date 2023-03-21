/*n 座城市和一些连接这些城市的道路 roads 共同组成一个基础设施网络。每个 roads[i] = [ai, bi] 都表示在城市 ai 和 bi 之间有一条双向道路。
两座不同城市构成的 城市对 的 网络秩 定义为：与这两座城市 直接 相连的道路总数。如果存在一条道路直接连接这两座城市，则这条道路只计算 一次 。
整个基础设施网络的 最大网络秩 是所有不同城市对中的 最大网络秩 。
给你整数 n 和数组 roads，返回整个基础设施网络的 最大网络秩 。*/
#include <iostream>
#include <stack>
#include <vector>
using namespace std;
class Solution
{
public:
    int maximalNetworkRank(int n, vector<vector<int>> &roads)
    {
        if(roads.size()!=0){
            vector<int> num(n);
            vector<vector<int>> road(n);
            int max1 = 0, max2 = 0,max3=0,max=0;
            int index1, index2,index3;
            for (int i = 0; i < n; i++)
            {
                road[i].resize(n);
            }
            for (int i = 0; i < roads.size(); i++)
            {
                num[roads[i][0]]++;
                num[roads[i][1]]++;
                road[roads[i][0]][roads[i][1]] = 1;
                road[roads[i][1]][roads[i][0]] = 1;
            }
            for (int i = 0; i < n; i++)
            {
                index1 = max1 > num[i] ? index1 : i;
                max1 = max1 > num[i] ? max1 : num[i];
                if (max2 <= max1)
                {
                    int temp = max1;
                    max1 = max2;
                    max2 = temp;
                    temp = index1;
                    index1 = index2;
                    index2 = temp;
                }
                if (max3 <= max2)
                {
                    int temp = max2;
                    max2 = max3;
                    max3 = temp;
                    temp = index2;
                    index2 = index3;
                    index3 = temp;
                }
            }
            cout<<index1<<index2;
            int x=max1 + max2 - road[index1][index2];
            int y=max1+max3-road[index1][index3];
            int z=max2+max3-road[index2][index3];
            return (x>y?x:y)>z?(x>y?x:y):z;
        }else return 0;
    }
};