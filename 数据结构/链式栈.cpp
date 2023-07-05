#include<iostream>
using namespace std;
template <class T>
struct stacknode
{
    T elem;
    stacknode* next;
};
template <class T>
struct stack
{
    struct stacknode<T> *top;
};
template<class T> bool Stack_push(struct stack<T> *s,T item);
template<class T> bool Stack_pop(struct stack<T> *s,T* item);
template<class T> bool Stack_top(struct stack<T> *s,T* item);
template<class T>
bool Stack_push(struct stack<T> *s,T item)
{
    struct stacknode<T> *np;
    np=new stacknode <T>;
    if(!np) return 0;//�൱���ڴ�����ջ�������
    else{
        np->next=s->next;
        s->top=np;
        np->elem=item;
        return 1;
    }
}
template<class T>
bool Stack_pop(struct stack<T> *s,T* item)
{
    if(!s->top) return 0;//û����һ���ڵ�˵��ջ��
    else{
        *item=s->top->elem;
        struct stacknode<T> *np=s->top;
        s->top=np->next;
        delete np;
        return 1;
    }
}
template<class T>
bool Stack_top(struct stack<T> *s,T* item)
{
     if(!s->top) return 0;
    else{
        *item=s->top->elem;
        return 1;
    }
}