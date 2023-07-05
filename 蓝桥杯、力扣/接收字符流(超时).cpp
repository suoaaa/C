/*设计一个算法：接收一个字符流，并检查这些字符的后缀是否是字符串数组 words 中的一个字符串。

例如，words = ["abc", "xyz"] 且字符流中逐个依次加入 4 个字符 'a'、'x'、'y' 和 'z' ，
你所设计的算法应当可以检测到?"axyz" 的后缀 "xyz" 与?words 中的字符串 "xyz" 匹配。

按下述要求实现 StreamChecker 类：
StreamChecker(String[] words) ：构造函数，用字符串数组?words 初始化数据结构。
boolean query(char letter)：从字符流中接收一个新字符，
如果字符流中的任一非空后缀能匹配 words 中的某一字符串，返回 true ；否则，返回 false。*/

/**
 * Your StreamChecker object will be instantiated and called as such:
 * StreamChecker* obj = new StreamChecker(words);
 * bool param_1 = obj->query(letter);
 */
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
class StreamChecker {
public:
    vector<string> words;
    vector<int> flag;
    vector<int> before;
    StreamChecker(){};
    StreamChecker(vector<string>& words) {
        this->words=vector<string>(words);
        flag=vector<int>(words.size(),0);
        before=vector<int>(words.size(),0);
        int temp=0;
        int p=0;
        for(int i=0;i<words.size();i++){
            for(int j=words[i].length()-2,k=words[i].length()-1,temp=0;j>=0&&k-temp>0;j--){
                if(words[i].at(j)==words[i].at(k)){
                    int m=j-1,temp=1;
                    for(;m>=0&&k-temp>0&&temp!=0;m--){
                        if(words[i].at(m)==words[i].at(k-temp)){temp++;}
                        else temp=0;
                    }
                    if(temp!=0){before[i]=temp;break;}
                }
            }
        }
    }
    bool query(char letter) {
        bool ret=false;
        for(int i=0;i<words.size();i++){
            if(words[i].at(flag[i])==letter){
                flag[i]++;
                }   else {
                    int j=flag[i]-2,k=flag[i]-1,temp=0;
                    for(;j>=0&&k-temp>=0;j--){
                        if(words[i].at(j)==words[i].at(k-temp)) temp++;
                        else temp=0; 
                } if(letter==words[i].at(temp))    flag[i]=temp+1;
                    else if(letter==words[i].at(0))flag[i]=1;
                    else flag[i]=0;
                }
            if(flag[i]==words[i].length()) {
                flag[i]=before[i];
                ret=true;
                }
            }
        return ret;
    }
};
