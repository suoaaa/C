/*����һ������ n���뷵�س���Ϊ n ������Ԫ�� (a, e, i, o, u) ����Ұ� 
�ֵ������� ���ַ����������ַ��� s �� �ֵ������� ��Ҫ���㣺
����������Ч�� i��s[i] ����ĸ���е�λ�������� s[i+1] ��ͬ���� s[i+1] ֮ǰ��*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<set>
#include<algorithm>
using namespace std;
class Solution {
public:
    char* sym;
    vector<vector<int>> dp;
	Solution(){
        char s[5]={'a','e','i','o','u'};
    	sym = s;
    }
    int countVowelStrings(int n) {
        vector<int> dp(5, 1);
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < 5; j++) {
                dp[j] += dp[j - 1];
            }
        }
        return accumulate(dp.begin(), dp.end(), 0);
    }
};