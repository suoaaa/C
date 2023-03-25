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
