/*����һ�� m x n ���������� accounts,����accounts[i][j]�ǵ�i????????????λ�ͻ��ڵ�j������
�йܵ��ʲ�������������пͻ���ӵ�е� �ʲ����� ��
�ͻ��� �ʲ����� ���������ڸ��������йܵ��ʲ�����֮�͡�
��пͻ����� �ʲ����� ���Ŀͻ���*/
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