#include<iostream>
#include<cstring>
Status List_Init(SqListPtr L,int n);
void List_Destory(SqListPtr L);
void List_Clear(SqListPtr L);
bool List_Empty(SqListPtr L);
int List_Size(SqListPtr L);
Status List_Retrival(SqListPtr L, int pos, ElemType *elem);
Status List_Locate(SqListPtr L, ElemType elem, int *pos);
Status List_Insert(SqListPtr L, int pos, ElemType elem);
Status List_delete(SqListPtr L, int pos);
Status List_Prior(SqListPtr L, int pos, ElemType * elem);
Status List_Next(SqListPtr L, int pos, ElemType *elem);
template<class T>
typedef T SqListPtr;
enum Status{success,fail,range_error};
Status List_Init(SqListPtr *L,int n)
{
    Status status=success;
    if(L)status=fail;
    else{L=new T[n](0);}
    return status;
}
void List_Destory(SqListPtr L)
{
    Status status=success;
    if(L=NULL)return status;
    else {delete(L);return status;}
}
void List_Clear(SqListPtr L)
{
    memset(L,0,sizeof(L));
    
}
bool List_Empty(SqListPtr L)
{
    if(L)return true;
    	else return false;
}
int List_Size(SqListPtr L)
{
    return sizeof(L)/sizeof(SqListPtr);
}
Status List_Retrival(SqListPtr L, int pos, ElemType *elem)
{
    
}
Status List_Locate(SqListPtr L, ElemType elem, int *pos);
Status List_Insert(SqListPtr L, int pos, ElemType elem);
Status List_delete(SqListPtr L, int pos);
Status List_Prior(SqListPtr L, int pos, ElemType * elem);
Status List_Next(SqListPtr L, int pos, ElemType *elem);


