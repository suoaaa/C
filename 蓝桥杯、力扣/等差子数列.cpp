/*����һ���� n ��������ɵ����� nums���������� m ��������ɵ����� l �� r��
�����������ʾ m �鷶Χ��ѯ�����е� i ����ѯ��Ӧ��Χ [l[i], r[i]] ��
����������±궼�� �� 0 ��ʼ �ġ�
���� boolean Ԫ�ع��ɵĴ��б� answer ��
��������� nums[l[i]], nums[l[i]+1], ... , nums[r[i]] ���� �������� �γ� �Ȳ����� ��answer[i] ��ֵ���� true������answer[i] ��ֵ���� false ��
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