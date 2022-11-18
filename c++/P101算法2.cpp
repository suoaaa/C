//循环队列只设rear和quelen指示队尾元素和队中元素的个数
//给出循环队列的队满条件，给出入队和出队算法，出队时给出队头元素
#include<iostream>
#include<cstring>
using namespace std;
#define queuesize 100
typedef struct queue//队列结构体
{           int rear;   int quelen;     char* data[100];
}queue;      queue *q;
bool full(queue *q) //相等时满返回true，不满返回false
{   return q->quelen==queuesize;    }
void enter(queue *q,char *a)//入队
{   if(full(q)) cout<<"队满，插入失败";
    else {
        q->data[q->rear]=a;
        q->rear=(1+q->rear)%queuesize;
        q->quelen++;
    }}
char* delet(queue *q)//出队
{   int i=0;//记录出队数据的位置
    if(q->quelen==0)
        cout<<"队空，无元素出队";
    i=(q->rear-q->quelen+1)%queuesize;
    q->quelen--;
    return (q->data[i]);}