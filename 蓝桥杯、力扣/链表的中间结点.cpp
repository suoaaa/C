//���㵥�����ͷ��� head �������ҳ�������������м��㡣
//����������м��㣬�򷵻صڶ����м��㡣
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