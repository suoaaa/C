/*���������ַ���?str1 ��?str2������ͬʱ��?str1?��?str2?��Ϊ�����е�����ַ���������𰸲�ֹһ��������Է�����������������һ���𰸡�

��������ַ��� T ��ɾ��һЩ�ַ���Ҳ���ܲ�ɾ��������ѡ������Щ�ַ�����λ�� T �е�?����λ�ã������Եõ��ַ��� S����ô?S ����?T �������У�*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
class Solution {
public:
    string shortestCommonSupersequence(string str1, string str2) {
        int m=str1.length(),n=str2.length();
        string ret;
        vector<bool> b1(m,false),b2(n,false);
        int i,j,temp;
        for(i=0,j=0;i<m;i++){
            for(temp=j;temp<n&&j<n;temp++){
                if(str1[i]==str2[temp]) {
                    j=temp;
                    b1[i]=true;
                    b2[temp]=true;
                    j+=1;
                    break;
                }
            }
        }
        vector<string> str(2);
        str[0]=str1;
        str[1]=str2;
        m=str[0].length();n=str[1].length();
        i=0;j=0;
        int num=0;
        ret+=str1;
        for(int i=0;i<n;i++){
            if(b2[i]!=true) ret+=str[1][i];
        }
        return ret;
    }
};
int main()
{
    string str1="aabbabaa";     //"aabb         a  baa"
    string str2="aabbbbbbaa";   //"aabb  bbb   baa"
    Solution s;
    string str=s.shortestCommonSupersequence(str1,str2);//"bbbaaababbb"
    cout<<str<<endl<<"aabbabbbbaa";
    return 0;
}