/*set_value函数：设置节点值
get_value函数：获取节点值
get_prev函数：获取上一个节点
get_next函数：获取下一个节点
append函数：在当前节点之后追加新节点*/
/*test1函数的输出应为：1 2 3
test2函数的输出应为：1.1 2.2 3.3
test3函数的输出应为：a b c*/
#include <iostream>
using namespace std;
template<class T>
class node
{
public:
    node(const T a){value=a;};
    node(){};
    void append(node* p){p->next=this->next;this->next=p;};
    void set_value(const T a){value=a;};
    T get_value(){return value;};
    node* get_next(){return next;};
private:
    T value;
    node* next;
};
void test1()
{
    node<int> *ptr;
    node<int> node1,node2,node3;
    node1.set_value(1);
    node2.set_value(2);
    node3.set_value(3);
    node1.append(&node2);
    node2.append(&node3);
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
    node1.append(&node2);
    node2.append(&node3);
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
    node1.append(&node2);
    node2.append(&node3);
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