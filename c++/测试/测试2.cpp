#include<iostream>
using namespace std;
 template<class T>
struct link_list
{
    T data;
    link_list *next;
    int list_long;
};
template<class T>   link_list<T> *List_Init(struct link_list<T> *L,int n)
{
    if(n<=0)return NULL;
    else
    {
        L=new link_list<T>;
        L->list_long=n;
        L->next=NULL;
        link_list<T> *p,*q;
        p=L;
        for(int i=0;i<L->list_long;i++)
        {
            q=new link_list<T>;
            q->next=NULL;
            p->next=q;
            p=q;
        }
        return L;
    }
}
template <class T>
void List_Destory(struct link_list<T> *L)
{
    link_list<T> *p=L;
    link_list<T> *q=L->next;
    for(int i=0;i<L->list_long;i++)
    {
        delete p;
        p=q;
        q=q->next;
    }
    L=NULL;
}
 
void List_printf(struct link_list<T> *L)
{
    link_list<T> *p=L->next;
    for(int i=0;i<L->list_long;i++)
    {
        cout<<p->data<<' ';
        p=p->next;
    }
}
 
bool List_Empty(struct link_list<T> *L)
{
    if(L)return true;
    	else return false;
}
 
int List_Size(struct link_list<T> *L)
{
    return L->list_long;
}
 
Status List_Retrival(struct link_list<T> *L, int pos, T elem)
{
    link_list<T> *p=L->next;
    int i=1;
    while(i<pos&&i<L->list_long) {i++;p=p->next;}
    p->data=elem;
}
 
void List_Retrival(struct link_list<T> *L, int pos, T *elem)
{
    link_list<T> *p=L->next;
    int i=1;
    while(i<pos&&i<L->list_long) {i++;p=p->next;}
    int n=sizeof(elem)/sizeof(T);
    for(int k=0;k<n&&k+i<L->list_long;k++) 
    {
        p->data=elem[i];
        p=p->next;
    }
}
 
Status List_Locate(struct link_list<T> *L, T elem, int *pos);
 
Status List_Insert(struct link_list<T> *L, int pos, T elem);
 
Status List_delete(struct link_list<T> *L, int pos);
 
Status List_Prior(struct link_list<T> *L, int pos, T * elem);
 
Status List_Next(struct link_list<T> *L, int pos, T *elem);
 
link_list<T> *List_Init(struct link_list<T> *L,int n,T *data)
{
    if(n<=0)return NULL;
    else
    {
        L=new link_list<T>;
        L->list_long=n;
        L->next=NULL;
        link_list<T> *p,*q;
        p=L;
        for(int i=0;i<L->list_long;i++)
        {
            q=new link_list<T>;
            q->next=NULL;
            q->data=data[i];
            p->next=q;
            p=q;
        }
        return L;
    }
}