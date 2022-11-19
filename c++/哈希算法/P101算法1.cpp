#include<iostream>
using namespace std;
//L为表长为n的递增有序表，x为插入的元素
//利用二分查找的递归思想，0<=bottle<=top<n
//未查找到合适的位置则递归，查找到进行插入操作
void add(int* L,int x,int bottle,int top)
{
    if(x>=L[(bottle+top)/2]&&x<=L[(bottle+top)/2+1])
    {
        int i=0;
        int n=sizeof(L)-sizeof(int);
        int *temp=new int [n+1];
        for(;i<=(bottle+top)/2;i++) temp[i]=L[i];
        temp[i]=x;
        for(;i<n;i++)   temp[i+1]=L[i];
    }
    else if(x<=L[(bottle+top)/2]) add(L,x,bottle,(bottle+top)/2);
        else add(L,x,(bottle+top)/2,top);
}