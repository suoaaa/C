#include<stdio.h>
#define DateType char
#define MAXSIZE 10
typedef struct CSNode           
{
	DateType data;
	struct CSNode *fird;  //��һ������        
	struct CSNode *next;  //�ú��ӵĵ�һ���ֵ�
}CSNode,CTree;            //����Ϊ��ʱ������ָ��ֱ�ָ��������Һ���

typedef struct SeqQueue
{
	CSNode* data[MAXSIZE];       //�ṹ������
	int front,rear;             //�����±�
}SeqQueue;

CTree* Transform(SeqQueue *q)//��ɭ��ת��Ϊ������
{   CTree *t=NULL,*p=t;
    if(q&&q->front!=q->rear)
    {   
        int i=1;
        t=q->data[0];
        while(i<MAXSIZE&&q->data[i])//����һ�����Ľڵ㵱���ϸ��ڵ���Һ���
        {    p->next=q->data[i];i++;    }
    }
    return t;
}