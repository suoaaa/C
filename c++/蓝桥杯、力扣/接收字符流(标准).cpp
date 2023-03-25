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