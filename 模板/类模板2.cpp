/*编写一个表示链表节点的模板类node，实现链表节点的以下功能：
set_value函数：设置节点值
get_value函数：获取节点值
get_prev函数：获取上一个节点
get_next函数：获取下一个节点
insert函数：在当前节点的位置插入新节点（当前节点后移）

例如，下列程序中，
test1函数的输出应为：1 2 3
test2函数的输出应为：1.1 2.2 3.3
test3函数的输出应为：a b c*/
#include <iostream>
using namespace std;
template<class T>
class node
{
public:
    node(const T a) {   value=a;prev=NULL;next=NULL;};
    node()  {prev=NULL;next=NULL;};
    void set_value(const T a)   {   value=a;};
    T get_value()   {   return value;};
    node* get_prev()    {   return this->get_prev;};
    node* get_next()    {   return this->next;};
    void insert(node *p)   
    {
        if(this->prev!=NULL)
        {this->prev->next=p;p->prev=this->prev;}
        this->prev=p;
        p->next=this;
    }
private: 
    node* next;
    node* prev;
    T value;
};
void test1()
{
    node<int> *ptr;
    node<int> node1,node2,node3;
    node1.set_value(1);
    node2.set_value(2);
    node3.set_value(3);
    node3.insert(&node2);
    node2.insert(&node1);
    for(ptr=&node1 ; ; ptr=ptr->get_next())
    {
        cout << ptr->get_value() << " ";
        if(ptr->get_next()==NULL) break;
    }
}
void test2()
{
    node<float> *ptr;
    node<float> node1,node2,node3;
    node1.set_value(1.1);
    node2.set_value(2.2);
    node3.set_value(3.3);
    node3.insert(&node2);
    node2.insert(&node1);
    for(ptr=&node1 ; ; ptr=ptr->get_next())
    {
        cout << ptr->get_value() << " ";
        if(ptr->get_next()==NULL) break;
    }
}
void test3()
{
    node<char> *ptr;
    node<char> node1,node2,node3;
    node1.set_value('a');
    node2.set_value('b');
    node3.set_value('c');
    node3.insert(&node2);
    node2.insert(&node1);
    for(ptr=&node1 ; ; ptr=ptr->get_next())
    {
        cout << ptr->get_value() << " ";
        if(ptr->get_next()==NULL) break;
    }
}
int main( )
{
    int type;
    cin >> type;
    switch(type)
    {
        case 1: test1();    break;
        case 2: test2();    break;
        case 3: test3();    break;
    }
    return 0;
}


