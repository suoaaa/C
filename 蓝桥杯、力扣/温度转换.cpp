/*����һ���������뵽��λС���ķǸ������� celsius ����ʾ�¶ȣ��� ���϶ȣ�Celsius��Ϊ��λ��

����Ҫ�����϶�ת��Ϊ ���϶ȣ�Kelvin���� ���϶ȣ�Fahrenheit������������ ans = [kelvin, fahrenheit] ����ʽ���ؽ����

�������� ans ����ʵ�ʴ������� 10-5 �Ļ���Ϊ��ȷ�𰸡�

���϶� = ���϶� + 273.15
���϶� = ���϶� * 1.80 + 32.00*/
#include<iostream>
#include<vector>
using namespace std;
class Solution {
public:
    vector<double> convertTemperature(double celsius) {
        vector<double> ans(2);
        double kelvin=celsius+273.15;
        double fahrenheit=celsius*1.80+32.00;
        ans[0] = kelvin;
        ans[1]=fahrenheit;
        return ans;
    }
};