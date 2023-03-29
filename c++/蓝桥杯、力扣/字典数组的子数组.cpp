/*给你一个整数 n，请返回长度为 n 、仅由元音 (a, e, i, o, u) 组成且按 
字典序排列 的字符串数量。字符串 s 按 字典序排列 需要满足：
对于所有有效的 i，s[i] 在字母表中的位置总是与 s[i+1] 相同或在 s[i+1] 之前。*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
#include<algorithm>
using namespace std;
class Solution {
public:
	int num;
    char []sym;
	Solution(){
        num=0;
    	sym={'a','e','i','o','u'};
    }
    int countVowelStrings(int n) {
        
    }
};