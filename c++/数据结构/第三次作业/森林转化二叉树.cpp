#include<stdio.h>
#define DateType char
#define MAXSIZE 10
typedef struct CSNode           
{
	DateType data;
	struct CSNode *fird;  //第一个孩子        
	struct CSNode *next;  //该孩子的第一个兄弟
}CSNode,CTree;            //当作为树时，两个指针分别指向的是左右孩子

typedef struct SeqQueue
{
	CSNode* data[MAXSIZE];       //结构体数组
	int front,rear;             //数组下标
}SeqQueue;

CTree* Transform(SeqQueue *q)//将森林转化为二叉树
{   CTree *t=NULL,*p=t;
    if(q&&q->front!=q->rear)
    {   
        int i=1;
        t=q->data[0];
        while(i<MAXSIZE&&q->data[i])//把下一颗树的节点当作上个节点的右孩子
        {    p->next=q->data[i];i++;    }
    }
    return t;
}