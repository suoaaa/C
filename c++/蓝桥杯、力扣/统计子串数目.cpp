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
    bool isUniqueSubstr(string s,string t){
        for(int i=0;i<t.length()-s.length();i++){
            int no=0;
            int j=0;
            while(no<2&&j<s.length()){
                if(t[i+j]!=s[j]) no++;
                j++;
            }
            if(no==1&&j==s.length()) {cout<<s;return true;}
        }
        return false;
    }
    int countSubstrings(string s, string t) {
        int num=0;
        for(int i=0;i<s.length();i++){
            for(int j=s.length();j>=i;j--){
                if(isUniqueSubstr(s.substr(i,j-i),t)){
                    num++;
                }
            }
        }
        return num;
    }
};