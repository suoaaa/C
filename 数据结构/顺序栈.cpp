#include<iostream>
using namespace std;
template <class T>
struct stack
{
    int top;
    T* elem;
    int size;
};//Լ��top���ڴ��ջ��Ԫ�أ�top=-1��ʾ��ջ��top=size-1��ʾվ��
template<class T> bool Stack_push(struct stack<T> *s,T item);
template<class T> bool Stack_full(struct stack<T> *s);
template<class T> bool Stack_empty(struct stack<T> *s);
template<class T> bool Stack_pop(struct stack<T> *s,T* item);
template<class T> bool Stack_top(struct stack<T> *s,T* item);
template<class T>
bool Stack_push(struct stack<T> *s,T item)
{
    if(Stack_full(s))    return 0;
    else {
        s->top++;
        s->elem[s->top]=item;
        return 1;
    }
}
template<class T>
bool Stack_full(struct stack<T> *s)
{
    if(s->top>=s->size-1) return 1;
    else return 0;
}
template<class T>
bool Stack_empty(struct stack<T> *s)
{
    if(s->top!=-1) return 1;
    else return 0;
}

template<class T>
bool Stack_pop(struct stack<T> *s,T* item)
{
    if(s->top==-1) return 0;
    else{
        *item=s->elem[s->top--];
        return 1;
    }
}
template<class T>
bool Stack_top(struct stack<T> *s,T* item)
{
     if(s->top==-1) return 0;
    else{
        *item=s->elem[s->top];
        return 1;
    }
}