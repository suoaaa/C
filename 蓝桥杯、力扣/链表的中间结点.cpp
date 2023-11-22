//给你单链表的头结点 head ，请你找出并返回链表的中间结点。
//如果有两个中间结点，则返回第二个中间结点。
#include <iostream>
using namespace std;
//Definition for singly-linked list.
struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};

class Solution {
public:
    ListNode* middleNode(ListNode* head) {
        ListNode *p1=head->next;
        ListNode *p2=head->next;
        while(true){
            if(p1!=nullptr&&p1->next!=nullptr) {
                p1=p1->next->next;
                p2=p2->next;
            }else return p2;
        }
    }
};