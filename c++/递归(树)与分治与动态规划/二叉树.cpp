#include<iostream>
using namespace std;
// 用先序序列和中序序列构建二叉树，采用二叉链表存储。编写递归算法，交换二叉树的左右子树，
// 输出新二叉树按先序遍历得到的结果。

// 提交格式：实现void solve(int n, int *preOrder, int *inOrder, int *outOrder)函数。
// 函数参数为序列长度n、先序序列preOrder、中序序列inOrder和输出序列outOrder。1<=n<=1000000，树的深度<=2000。
// 请不要printf输出任何内容。

// 输入样例1：
// n=5,preOrder={1,2,3,4,5},inOrder={3,2,4,1,5}
// 输出样例1：
// outOrder={1,5,2,4,3}
int c=1;
int p=0;
int i=0;
int o=0;
typedef struct Tree
{
	int data;
	Tree* left;
	Tree* right;
}Tree;
Tree *creattree(int n)
{
	Tree *root = new Tree;	//二叉树的根结点
	if (n <= 0)	return NULL;
	else {
		root->data = c++;
        int m=(n-1)/2;
        if(m%2) m+=1;
		root->left = creattree(m);	
		root->right = creattree(n - 1 - m);	
		return root;	//返回二叉树的根
	}
}
void Pre(Tree *head,int *preOrder)
{
    if(head)
    {
        preOrder[p++]=head->data;
        Pre(head->left,preOrder);
        Pre(head->right,preOrder);
    }
}
void in(Tree *head,int *inOrder)
{
    if(head)
    {
        in(head->left,inOrder);
        inOrder[i++]=head->data;
        in(head->right,inOrder);
    }
}
void out(Tree *head,int *outOrder)
{
    if(head)
    {
        outOrder[p++]=head->data;
        Pre(head->right,outOrder);
        Pre(head->left,outOrder);
    }
}
void solve(int n, int *preOrder, int *inOrder, int *outOrder)
{
    if(n==1)
    {   preOrder[0]=1;
        inOrder[0]=1;
        outOrder[0]=1;    
    }
    else{
        Tree *head=creattree(n);
        Pre(head,preOrder);
        in(head,inOrder);
        out(head,outOrder);
    }
}
int main ()
{
    int n=5;
    int *preOrder=new int [n];
    int *inOrder=new int [n];
    int *outOrder=new int [n];
    solve( n, preOrder, inOrder, outOrder);
    cout<<n;
    return 0;
}