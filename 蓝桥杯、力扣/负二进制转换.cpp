/*给你一个整数 n ，以二进制字符串的形式返回该整数的 负二进制（base -2）表示。
注意，除非字符串就是 "0"，否则返回的字符串中不能含有前导零。*/
#include<stdio.h>
#include<iostream>
#include<stdlib.h>
#include<cstring>
#include<vector>
#include<queue>
#include<algorithm>
#include<bits/stdc++.h>
using namespace std;
class Solution {
public:
    string baseNeg2(int n) {
        if (n == 0 || n == 1) {
            return to_string(n);
        }
        string res;
        while (n != 0) {
            int remainder = n & 1;
            res.push_back('0' + remainder);
            n -= remainder;
            n /= -2;
        }
        reverse(res.begin(), res.end());
        return res;
    }
};