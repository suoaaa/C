#include<iostream>
#include<cstring>
using namespace std;
enum Status{success,fail,range_error};
template<class T>
struct link_list
{
    T data;
    link_list *next;
    int list_long;
};
template<class T>   link_list<T> *List_Init(struct link_list<T> *L,int n);          //��ʼ������
template<class T>   link_list<T> *List_Init(struct link_list<T> *L,int n,T *data);  //��ʼ������
template<class T>   void List_Destory(struct link_list<T> *L);                      //�ݻ�����
template<class T>   void List_printf(struct link_list<T> *L);                       //��ӡ���������
template<class T>   bool List_Empty(struct link_list<T> *L);                        //�ж������Ƿ�Ϊ�գ���Ϊfalse��δ��true
template<class T>   int List_Size(struct link_list<T> *L);                          //����������        
template<class T>   void List_Retrival(struct link_list<T> *L, int pos, T elem);    //posλ�õĽڵ����ݸ���
template<class T>   void List_Retrival(struct link_list<T> *L, int pos, T *elem);   //��posλ�ýڵ����ݸ���Ϊelem�е�Ԫ��
template<class T>   link_list<T> *List_Locate(struct link_list<T> *L, T elem);      //��λelem�ڽڵ��е�λ��
template<class T>   bool List_Insert(struct link_list<T> *L, int pos, T elem);      //��posλ��֮�����һ������Ϊelem�Ľڵ�
template<class T>   bool List_delete(struct link_list<T> *L, int pos);              //ɾ��pos���
template<class T>   link_list<T> *List_Prior(struct link_list<T> *L, int pos, T * elem);   //����posλ��֮ǰ�Ľڵ��ַ
template<class T>   link_list<T> *List_Next(struct link_list<T> *L, int pos, T *elem);     //����posλ��֮��Ľڵ��ַ

template<class T>
link_list<T> *List_Init(struct link_list<T> *L,int n)
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
template<class T>
void List_printf(struct link_list<T> *L)
{
    link_list<T> *p=L->next;
    for(int i=0;i<L->list_long;i++)
    {
        cout<<p->data<<' ';
        p=p->next;
    }
}
template<class T>
bool List_Empty(struct link_list<T> *L)
{
    if(L)return true;
    	else return false;
}
template<class T>
int List_Size(struct link_list<T> *L)
{
    return L->list_long;
}
template<class T>
void List_Retrival(struct link_list<T> *L, int pos, T elem)
{
    link_list<T> *p=L->next;
    int i=1;
    while(i<pos&&i<L->list_long) {i++;p=p->next;}
    p->data=elem;
}
template<class T>
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
template<class T>
link_list<T> *List_Locate(struct link_list<T> *L, T elem,int *pos)
{
    link_list<T> *re=NULL;
    pos=0;
    link_list<T> *p=L->next;
    for(int i=1;i<=L->list_long;i++)
    {
        if(p->data!=elem)
        	{i++;p=p->next;continue;}
        else {pos=i;re=p;break;}
    }
    return re;
}
template<class T>
bool List_Insert(struct link_list<T> *L, int pos, T elem)
{
    link_list<T> *p=L->next;
    bool re=false;
    if(pos<=L->list_long)
    {
        int i=1;
        while(i<pos){i++;p=p->next;}
        link_list<T> *q=new link_list<T>;
        q->next=p->next;
        p->next=q;
        q->data=elem;
        re=true;
    }
    return re;
}
template<class T>
bool List_delete(struct link_list<T> *L, int pos)
{
    link_list<T> *p=L->next;
    bool re=false;
    if(pos<=L->list_long)
    {
        int i=1;
        while(i<pos-1){i++;p=p->next;}
        link_list<T> *q=p->next;
        p->next=q->next;
        delete q;
        re=true;
    }
    return re;
}
template<class T>
link_list<T> *List_Prior(struct link_list<T> *L, int pos, T *elem)
{
    if(pos>L->list_long) {return NULL;}
    else{
        link_list<T> *p=L->next;
        int i=1;
        while(i<pos-1){i++;p=p->next;}
        *elem=p->data;
        return p;
    }
}
template<class T>
link_list<T> *List_Next(struct link_list<T> *L, int pos, T *elem)
{
    if(pos>=L->list_long) {return NULL;}
    else{
        link_list<T> *p=L->next;
        int i=1;
        while(i<pos+1){i++;p=p->next;}
        *elem=p->data;
        return p;
    }
}
template<class T>
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
int main()
{
    link_list<int> *L;
    link_list<int> *p;
    link_list<int> *q;
    int *a=new int;
    int *b=new int;
    int data[5]={1,2,3,4,5};
    L=List_Init(L,5,data);
    p=List_Prior(L,3,a);
    q=List_Next(L,3,b);
    List_printf(L);
    cout<<endl<<*a<<' '<<*b;
    return 0;
}