/*用循环链表实现：N个乘客同乘一艘船，只有将部分乘客投入海中，其余人才能幸免于难。
无奈，大家只得同意这种办法。于是N个人围成一圈(从1，2，3...N分别编号)。由编号为1的人开始，依次报数，数到第M人，便把他投入大海中，
然后再从他的下一个人数起，数到第M人，再将他扔到大海中，如此循环地进行，直到剩下K个乘客为止。按顺序依次输出被扔下大海的乘客的编号。
函数返回值为按顺序被扔下大海乘客编号的数组。
请不要printf输出任何内容。
输入样例1：
9 3 2 九个人，数到3就仍，剩俩 
输出样例1：
3 6 9 4 8 5 2
输入样例2：
12 5 6
输出样例2：
5 10 3 9 4 12*/
#include <iostream>
using namespace std;
typedef struct Node
{
	int data;
	struct Node* next;
}Node;
Node* CreateList(int n)//n是长度
{
	Node* first = new Node(); // 	 
	first->next=NULL;
	Node* r=NULL;	
	Node* p=NULL;
	r=first;	
	for(int i=1;i<=n;i++){
		p=new Node();
		p->data=i;
		p->next=NULL;
		r->next=p; 			
		r=p;
	} 
	if(n==1)
	{
		first->next=first->next;
	}
	else {r->next=first->next;}
	return first; 
}
int *solve(int A,int M,int L)//A M L为乘客数，间隔数(interval),剩余人数
{	
	int killed=0;
	int *leaved=new int(A-L);	
	int i=1;
	Node *q=CreateList(A);
	Node*p=q->next;
	Node *r=p->next;
	if(M==1)
	{
		while(L!=A)
		{
			leaved[killed++]=p->data;
			p=p->next;
			A--;
			//cout<<leaved[killed-1]<<' ';
		}
	}	
	else
	{
		while(killed!=A-L)
		{
			i=1;
			while(i<M-1)
			{
				i++;
				p=p->next;
				r=r->next;
			}
			leaved[killed]=r->data; 
			cout<<' '<<leaved[killed]<<" 0 "<<r->data<<endl;
			p->next=r->next; 
			p=p->next;
			r=p->next;
			killed++;			
			 cout<<p->data<<' '<<r->data;
		}
	}
	for(i=0;i<A;i++)
	{
		cout<<p->data<<' ';
		p=p->next;
	}
	return leaved;
}
int main ()
{
	int N=0,M=0,K=0;
	scanf("%d %d %d",&N,&K,&M);//总人数
	int *leaved=solve(N,K,M);
	cout<<endl; 
	 for(int i=0;i<N-M;i++)
	 {
	 cout<<leaved[i]<<' ';
	 }
	return 0;
}
