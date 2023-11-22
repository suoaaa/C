/*给你一个整数 n，请返回长度为 n 、仅由元音 (a, e, i, o, u) 组成且按 
字典序排列 的字符串数量。字符串 s 按 字典序排列 需要满足：
对于所有有效的 i，s[i] 在字母表中的位置总是与 s[i+1] 相同或在 s[i+1] 之前。*/
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