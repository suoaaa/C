/*���������ַ���?s ��?t?�������ҳ� s?�еķǿ��Ӵ�����Ŀ��
��Щ�Ӵ������滻 һ����ͬ�ַ�?�Ժ��� t?�����Ӵ�
������֮�������ҵ� s?�� t?���� ǡ��?ֻ��һ���ַ���ͬ�����ַ����Ե���Ŀ��
�ȷ�˵��"computer"?and?"computation"?ֻ��һ���ַ���ͬ��?'e'/'a'?��������һ�����ַ�������𰸼� 1 ��
���㷵���������������Ĳ�ͬ���ַ�������Ŀ��
һ�� ���ַ���?��һ���ַ������������ַ���*/
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