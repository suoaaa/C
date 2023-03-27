/*给你两个字符串?s 和?t?，请你找出 s?中的非空子串的数目，
这些子串满足替换 一个不同字符?以后，是 t?串的子串
。换言之，请你找到 s?和 t?串中 恰好?只有一个字符不同的子字符串对的数目。
比方说，"computer"?and?"computation"?只有一个字符不同：?'e'/'a'?，所以这一对子字符串会给答案加 1 。
请你返回满足上述条件的不同子字符串对数目。
一个 子字符串?是一个字符串中连续的字符。*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
class Solution {
public:
    int isUniqueSubstr(string s,string t){
        int ret=0;
        for(int i=0;i<t.length();i++){
            int no=0;
            int j=0;
            for(;j<s.length()&&no<2&&i+j<t.length();j++){
                if(t[i+j]!=s[j]) no++;
            }
            if(no==1&&j==s.length()) {ret++;}
        }
        return ret;
    }
    int countSubstrings(string s, string t) {
        int num=0;
        for(int i=0;i<s.length();i++){
            for(int j=s.length();j>i;j--){
                num+=isUniqueSubstr(s.substr(i,j-i),t);
            }
        }
        return num;
    }
};