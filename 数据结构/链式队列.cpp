#include<iostream>
using namespace std;
template <class T>
struct entry_node
{
    T elem;
    entry_node<T> *next;
};
template <class T>
struct entry
{
    struct entry_node<T> *rear;//��β
    struct entry_node<T> *front;//��ͷ
};
template <class T> bool entry_full(struct entry<T> e);
template <class T> bool entry_empty(struct entry<T> e);
