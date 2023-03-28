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
        int a=0,b=0;
        int i,j,temp;
        for(i=0,j=0;i<m;i++){
            for(temp=j;temp<n;temp++){
                if(str1[i]==str2[temp]) {
                    j=temp;
                    b1[i]=true;
                    a++;
                    break;
                }
            }
        }
        for(i=0,j=0;i<n;i++){
            for(temp=j;temp<m;temp++){
                if(str2[i]==str1[temp]) {
                    j=temp;
                    b2[i]=true;
                    b++;
                    break;
                }
            }
        }
        vector<string> str(2);
        if(a>=b)    {
            str[0]=str1;
            str[1]=str2;
        }else {
            str[0]=str2;
            str[1]=str1;
        }
        m=str[0].length();n=str[1].length();
        i=0;j=0;
        while(j<n&&i<m){
            while(b1[i]==false&&j<n&&i<m){
                ret+=str[0][i];
                i++;
            }
            while(str[1][j]!=str[0][i]&&j<n&&i<m){
                ret+=str[1][j];
                j++;
            }
            
            while(str[1][j]==str[0][i]&&j<n&&i<m){
                ret+=str[i][j];
                i++;
                j++;
            }
            while(b1[i]==true&&j<n&&i<m){
                ret+=str[0][i];
                i++;
            }
        }
        return ret;
    }
};
int main()
{
    string str1="bbbaaaba";
    string str2="bbababbb";
    Solution s;
    string str=s.shortestCommonSupersequence(str1,str2);
    cout<<str;
    return 0;
}