/*给你一条个人信息字符串 s ，可能表示一个 邮箱地址 ，也可能表示一串 电话号码 。返回按如下规则 隐藏 个人信息后的结果：
电子邮件地址：
一个电子邮件地址由以下部分组成：
一个 名字 ，由大小写英文字母组成，后面跟着
一个 '@' 字符，后面跟着
一个 域名 ，由大小写英文字母和一个位于中间的 '.' 字符组成。'.' 不会是域名的第一个或者最后一个字符。
要想隐藏电子邮件地址中的个人信息：
名字 和 域名 部分的大写英文字母应当转换成小写英文字母。
名字 中间的字母（即，除第一个和最后一个字母外）必须用 5 个 "*****" 替换。
电话号码：
一个电话号码应当按下述格式组成：
电话号码可以由 10-13 位数字组成
后 10 位构成 本地号码
前面剩下的 0-3 位，构成 国家代码
利用 {'+', '-', '(', ')', ' '} 这些 分隔字符 按某种形式对上述数字进行分隔
要想隐藏电话号码中的个人信息：
移除所有 分隔字符
隐藏个人信息后的电话号码应该遵从这种格式：
"***-***-XXXX" 如果国家代码为 0 位数字
"+*-***-***-XXXX" 如果国家代码为 1 位数字
"+**-***-***-XXXX" 如果国家代码为 2 位数字
"+***-***-***-XXXX" 如果国家代码为 3 位数字
"XXXX" 是最后 4 位 本地号码。*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<set>
#include<queue>
#include<algorithm>
using namespace std;
class Solution {
public:
    string maskPII(string s) {
        string ret;                             
        if(s.find('@')==-1){                       //"***-***-XXXX" 如果国家代码为 0 位数字         
            reverse(s.begin(),s.end());                           //"+*-***-***-XXXX" 如果国家代码为 1 位数字  
            cout<<s<<endl;
            string s1;
            for(int i=0;i<s.length();i++){
                if(s[i]<='9'&&s[i]>='0') s1+=s[i];
                cout<<s1<<endl;
            }
            ret=s1[0];
            ret+=s1[1];
            ret+=s1[2];
            ret+=s1[3];
            ret+="-***-***";                        //"+**-***-***-XXXX" 如果国家代码为 2 位数字                         
            if(s1.length()>10){                     //"+***-***-***-XXXX" 如果国家代码为 3 位数字
                ret+='-';
                for(int i=10;i<s1.length();i++){    //"XXXX" 是最后 4 位 本地号码。*                        
                    ret+='*';
                }ret+='+';
            }
            reverse(ret.begin(),ret.end());
            return ret;
        }
        int n=s.find('@');
        if(s[0]>='A'&&s[0]<='Z') ret+=char(s[0]-'A'+'a');
            else ret+=s[0];
        ret+="*****";
        if(s[n-1]>='A'&&s[n-1]<='Z') ret+=char(s[n-1]-'A'+'a');
            else ret+=s[n-1];
        for(int i=n;i<s.length();i++){
            if(s[i]>='A'&&s[i]<='Z') ret+=char(s[i]-'A'+'a');
            else ret+=s[i];
        }
        return ret;
    }
};
int main(){
    Solution s;
    cout<<s.maskPII("1(234)567-8901");
    return 0;
}