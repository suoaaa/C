/*��ѭ������ʵ�֣�N���˿�ͬ��һ�Ҵ���ֻ�н����ֳ˿�Ͷ�뺣�У������˲����������ѡ�
���Σ����ֻ��ͬ�����ְ취������N����Χ��һȦ(��1��2��3...N�ֱ���)���ɱ��Ϊ1���˿�ʼ�����α�����������M�ˣ������Ͷ����У�
Ȼ���ٴ�������һ��������������M�ˣ��ٽ����ӵ����У����ѭ���ؽ��У�ֱ��ʣ��K���˿�Ϊֹ����˳��������������´󺣵ĳ˿͵ı�š�
��������ֵΪ��˳�����´󺣳˿ͱ�ŵ����顣
�벻Ҫprintf����κ����ݡ�
��������1��
9 3 2 �Ÿ��ˣ�����3���ԣ�ʣ�� 
�������1��
3 6 9 4 8 5 2
��������2��
12 5 6
�������2��
5 10 3 9 4 12*/
#include <iostream>
using namespace std;
typedef struct Node
{
	int data;
	struct Node* next;
}Node;
Node* CreateList(int n)//n�ǳ���
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
int *solve(int A,int M,int L)//A M LΪ�˿����������(interval),ʣ������
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
	scanf("%d %d %d",&N,&K,&M);//������
	int *leaved=solve(N,K,M);
	cout<<endl; 
	 for(int i=0;i<N-M;i++)
	 {
	 cout<<leaved[i]<<' ';
	 }
	return 0;
}
