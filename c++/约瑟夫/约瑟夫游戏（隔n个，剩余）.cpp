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
			// cout<<list->data;
			// list=list->next;
			// for(int i=1;i<K;i++)
			// {
			// cout<<','<<list->data;
			// list=list->next;
			// }
			// cout<<"\n";
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
		// cout<<first->data;
		// first=first->next;
		// for(int i=1;i<N;i++)
		// {
		// 	cout<<','<<first->data;
		// 	first=first->next;
		// }
		// cout<<"\n\n";
	 return first; 
}