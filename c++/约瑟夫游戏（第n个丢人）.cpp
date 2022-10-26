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
int length=0; 
int killed=0;
int leaved[100]={0};	
typedef struct Node
{
	int data;
	struct Node* next;
}Node;
Node* CreateList(int n)//n是长度
{
	Node* first = new Node(); // 	 
	first->next=NULL;
	Node* r=NULL;	//节点one 
	Node* p=NULL;	//节点two 
	r=first;
	r->data=1;	
	for(int i=1;i<n;i++){
		p=new Node();
		p->data=i+1;
		p->next=NULL;
		r->next=p; 			
		r=p;				//r现在指向p那么r就可以代表原来的p了，从而实现循环创建！！ 
	} 
	if(n==1)
	{
		first->next=first;
	}
	else {p->next=first;}
	return first; 
}

Node* DeleteData(Node* first,int x,int a)//传入头节点指针和要删除的链表序号 
{	
	Node*p=first;
	Node*r=NULL;		
	int cnt=1; 	
	while(cnt!=x-1)//删掉x，得在x-1处动手 
	{
		p=p->next;
		cnt++;			//cnt就是查 到第几个节点了了，从1开始 
	}
	r=p->next;
	p->next=r->next;			//r就这样被踢出队伍了qwq 
	leaved[killed]=r->data;
	killed++;
	length--;
	return r->next;
} 
int *solve(int A,int M,int L)//A M L为乘客数，间隔数(interval),剩余人数
{	
	length=A; 
	Node* mytable=NULL;	 
	mytable=CreateList(A);	
	if(M==1){
	for(int a=A-L;a>0;a--)
	{
	leaved[a-1]=a;
	}
	return leaved;
	}
	while(length!=L)
	{
	mytable=DeleteData(mytable,M,A);			
	}
	return leaved;
}
//我发现我好像没有对整个流程有一个概念，我是每个步骤框架写完后去验证是否完成目标 
// int main ()
// {
// 	int N=0,M=0;int K=0;
// 	int n=scanf("%d %d %d",&N,&K,&M);//总人数
// 	Node* list=yourself(N,K,M);
// 	cout<<list->data;
// 	list=list->next;
// 	for(int i=1;i<K;i++)
// 	{
// 		cout<<','<<list->data;
// 		list=list->next;
// 	}
// 	return 0;
// }