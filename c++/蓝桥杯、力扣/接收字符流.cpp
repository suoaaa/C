/*���һ���㷨������һ���ַ������������Щ�ַ��ĺ�׺�Ƿ����ַ������� words �е�һ���ַ�����

���磬words = ["abc", "xyz"] ���ַ�����������μ��� 4 ���ַ� 'a'��'x'��'y' �� 'z' ��
������Ƶ��㷨Ӧ�����Լ�⵽?"axyz" �ĺ�׺ "xyz" ��?words �е��ַ��� "xyz" ƥ�䡣

������Ҫ��ʵ�� StreamChecker �ࣺ
StreamChecker(String[] words) �����캯�������ַ�������?words ��ʼ�����ݽṹ��
boolean query(char letter)�����ַ����н���һ�����ַ���
����ַ����е���һ�ǿպ�׺��ƥ�� words �е�ĳһ�ַ��������� true �����򣬷��� false��*/
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
        for(int i=0;i<words.size();i++){
            for(int j=0,k=1,m=;j<words[i].length()&&m<words[i].length();j++){
                if()
            }
        }
    }
    bool query(char letter) {
        for(int i=0;i<words.size();i++){
            if(words[i].at(flag[i])==letter){
                flag[i]++;
                }   else flag[i]=0;
            if(flag[i]==words[i].length()) {
                flag[i]=before[i];
                return true;
                }
            }
        return false;
    }
};

/**
 * Your StreamChecker object will be instantiated and called as such:
 * StreamChecker* obj = new StreamChecker(words);
 * bool param_1 = obj->query(letter);
 */