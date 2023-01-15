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
	if (n == 1)       //只剩下一个结点时，直接返回
		return tree;
	
	int  midPtr, leftLength, rightLength;
	midPtr = 0;
	while (midOrder[++midPtr] != preOrder[0])    //找出中序遍历中根节点的位置
		;
	leftLength = midPtr;        //左子树结点数
	rightLength = n - leftLength - 1;       //右子树结点数
	
	//偷懒起见，未创建新的数组
	tree->leftchild = CreateTree_Pre(&preOrder[1], midOrder, leftLength);     //注意左右子树在原数组中的位置
	tree->rightchild = CreateTree_Pre(&preOrder[leftLength + 1], &midOrder[leftLength + 1], rightLength);
	return tree;
}

void PrintPostOrder(BinTree t)  //打印后序遍历结果
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
	printf("请输入先序遍历结果：");
	gets(pre);
	printf("请输入中序遍历结果：");
	gets(mid);
	printf("后序遍历结果为：");
	t = CreateTree_Pre(pre, mid, strlen(pre));
	PrintPostOrder(t);
	return 0;
}
