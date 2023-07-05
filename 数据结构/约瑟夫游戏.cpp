/*约瑟夫游戏的主要问题：
输入：总人数 N 
     剩余人数 K
     间隔人数 M
其中的间隔人数为实际间隔的人数，如果剩余人数为0，则输出0。
比如，输入8 4 3（空格分隔输入）
     输出1,3,6,7（逗号分隔输出）
输入数据错误时，输出ERROR
分析过程如下：
1、初始状态  1 2 3 4 5 6 7 8
2、间隔三个删除， 4,8
3、余下人数大于4，流程继续删除 5,2
4、现在剩下人数4人=K，剩余输出余下的人有：1,3,6,7*/
#include<iostream>
using namespace std;
typedef struct Node
{
	int data;
	struct Node* next;
}Node;
Node* CreateList(int N);//人数N
Node* yourself(int N,int K,int M);//总人数N，剩余人数K，间隔人数M
int main()
{
	int N=0,M=0;int K=0;
	int n=scanf("%d %d %d",&N,&K,&M);
	if(N<1||N<K||n!=3||M<0)
	{
		cout<<"ERROR";
		return 0;
	}
	else
	{
		if(K==0)
		{
			cout<<'0';
			return 0;
		}
		else{
			Node* list=yourself(N,K,M);
			cout<<list->data;
			list=list->next;
			for(int i=1;i<K;i++)
			{
			cout<<','<<list->data;
			list=list->next;
			}
		}
	}
	return 0;
}
Node* yourself(int N,int K,int M)//总人数N，剩余人数K，间隔人数M
{
	Node *list=CreateList(N);
	Node *p=list->next;
	int i=1;
	int n=N;
	while(n!=K)
	{
		while(i!=M)
		{
			list=p;
			p=list->next;
			i++;
		}
		list->next=p->next;
		list=list->next;
		p=list->next;
		i=1;
		n--;
	}
	while((list->data)<(p->data))
	{
		list=list->next;
		p=p->next;
	}
	return p;
}
Node* CreateList(int N)//人数N
{
	Node* first = new Node(); 
	first->next=NULL;
	Node* r=first;	
	Node* p=NULL;	
	r->data=1;
	for(int i=1;i<N;i++){
		p=new Node();
		p->data=i+1;
		p->next=NULL;
		r->next=p;
		r=p;
	} 
	if(N==1)
	{
		first->next=first;
	}
	else
	{
		p->next=first;
	}
	 return first; 
}