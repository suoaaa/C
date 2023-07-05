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
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
#include<queue>
using namespace std;
typedef struct TrieNode {
    vector<TrieNode *> children;
    bool isEnd;
    TrieNode *fail;
    TrieNode() {
        this->children = vector<TrieNode *>(26, nullptr);
        this->isEnd = false;
        this->fail = nullptr;
    }
};

class StreamChecker {
public:
    TrieNode *root;
    TrieNode *temp;
    StreamChecker(vector<string>& words) {
        root = new TrieNode();
        for (string &word : words) {
            TrieNode *cur = root;
            for (int i = 0; i < word.size(); i++) {
                int index = word[i] - 'a';
                if (cur->children[index] == nullptr) {
                    cur->children[index] = new TrieNode();
                }
                cur = cur->children[index];
            }
            cur->isEnd = true;
        }
        root->fail = root;
        queue<TrieNode *> q;
        for (int i = 0; i < 26; i++) {
            if(root->children[i] != nullptr) {
                root->children[i]->fail = root;
                q.emplace(root->children[i]);
            } else {
                root->children[i] = root;
            }
        }
        while (!q.empty()) {
            TrieNode *node = q.front();
            q.pop();
            node->isEnd = node->isEnd || node->fail->isEnd;
            for (int i = 0; i < 26; i++) {
                if(node->children[i] != nullptr) {
                    node->children[i]->fail = node->fail->children[i];
                    q.emplace(node->children[i]);
                } else {
                    node->children[i] = node->fail->children[i];
                }
            }
        }

        temp = root;
    }
    bool query(char letter) {
        temp = temp->children[letter - 'a'];
        return temp->isEnd;
    }
};