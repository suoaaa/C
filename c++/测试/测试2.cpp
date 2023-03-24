/*���һ���㷨������һ���ַ������������Щ�ַ��ĺ�׺�Ƿ����ַ������� words �е�һ���ַ�����

���磬words = ["abc", "xyz"] ���ַ�����������μ��� 4 ���ַ� 'a'��'x'��'y' �� 'z' ��
������Ƶ��㷨Ӧ�����Լ�⵽?"axyz" �ĺ�׺ "xyz" ��?words �е��ַ��� "xyz" ƥ�䡣

������Ҫ��ʵ�� StreamChecker �ࣺ
StreamChecker(String[] words) �����캯�������ַ�������?words ��ʼ�����ݽṹ��
boolean query(char letter)�����ַ����н���һ�����ַ���
����ַ����е���һ�ǿպ�׺��ƥ�� words �е�ĳһ�ַ��������� true �����򣬷��� false��*/

/**
 * Your StreamChecker object will be instantiated and called as such:
 * StreamChecker* obj = new StreamChecker(words);
 * bool param_1 = obj->query(letter);
 */
#include <stdio.h>
#include <iostream>
#include <string.h>
#include <vector>
using namespace std;
class StreamChecker{
public:
    vector<string> words;
    vector<int> flag;
    vector<vector<int>> before;
    StreamChecker(){};
    StreamChecker(vector<string> &words){
        this->words = vector<string>(words);
        flag = vector<int>(words.size(), 0);
        before.reserve(words.size());
        int temp = 0;
        int p = 0;
        for (int i = 0; i < words.size(); i++){
            before[i].resize(words[i].length(),0);
            for (int t = 0; words[i].length() - 1 - t > 0; t++){
                for (int j = words[i].length() - 2 - t, k = words[i].length() - 1 - t, temp = 0; j >= 0 && k - temp > 0; j--){
                    if (words[i].at(j) == words[i].at(k)){
                        int m = j - 1, temp = 1;
                        for (; m >= 0 && k - temp > 0 && temp != 0; m--){
                            if (words[i].at(m) == words[i].at(k - temp)){
                                temp++;
                            }
                            else
                                temp = 0;
                        }
                        if (temp != 0){
                            before[i][words[i].length() - 1 - t] = temp;
                            break;
                        }
                    }
                }
            }
        }
    }
    bool query(char letter){
        bool ret = false;
        for (int i = 0; i < words.size(); i++){
            if (words[i].at(flag[i]) == letter){
                flag[i]++;
            }
            else{
                flag[i] = before[i][flag[i]];
            }
            if (flag[i] == words[i].length()){
                flag[i] = before[i][words[i].length() - 1];
                ret = true;
            }
        }
        return ret;
    }
};
