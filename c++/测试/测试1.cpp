#include<stdio.h>
#include<stdlib.h>
#include<string.h>

#define MaxArraySize 20

typedef struct BinTreeNode* BinTree;

struct BinTreeNode
{
	int element;
	BinTree leftchild;
	BinTree rightchild;
};

BinTree CreateTree_Pre(char* preOrder, char* midOrder, int n)
{
	BinTree tree = (BinTree)malloc(sizeof(struct BinTreeNode));
	tree->element = preOrder[0];
	tree->leftchild = NULL;
	tree->rightchild = NULL;
	if (n == 1)       //ֻʣ��һ�����ʱ��ֱ�ӷ���
		return tree;
	
	int  midPtr, leftLength, rightLength;
	midPtr = 0;
	while (midOrder[++midPtr] != preOrder[0])    //�ҳ���������и��ڵ��λ��
		;
	leftLength = midPtr;        //�����������
	rightLength = n - leftLength - 1;       //�����������
	
	//͵�������δ�����µ�����
	tree->leftchild = CreateTree_Pre(&preOrder[1], midOrder, leftLength);     //ע������������ԭ�����е�λ��
	tree->rightchild = CreateTree_Pre(&preOrder[leftLength + 1], &midOrder[leftLength + 1], rightLength);
	return tree;
}

void PrintPostOrder(BinTree t)  //��ӡ����������
{
	if (t)
{
	PrintPostOrder(t->leftchild);
	PrintPostOrder(t->rightchild);
	printf("%c", t->element);
}}

int main()
{
	BinTree t = (BinTree)malloc(sizeof(struct BinTreeNode));
	char pre[MaxArraySize], mid[MaxArraySize];
	printf("������������������");
	gets(pre);
	printf("������������������");
	gets(mid);
	printf("����������Ϊ��");
	t = CreateTree_Pre(pre, mid, strlen(pre));
	PrintPostOrder(t);
	return 0;
}
