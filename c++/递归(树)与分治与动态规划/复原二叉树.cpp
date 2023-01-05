#include<stdio.h>
#include<stdlib.h>
#define MAXNUM 50
#define MAX(a,b) ((a)>(b)?(a):(b))
typedef char DataType;
typedef struct BiTreeNode
{
    DataType data;
    struct BiTreeNode *lchild,*rchild;
}BiTreeNode,*BiTree;
typedef BiTree* BiTreePtr;
// void createTree
void createTree(BiTreePtr T);//先序创建二叉??
void PreOrder(BiTreePtr T);//先序遍历
void InOrder(BiTreePtr T);//中序遍历
void LaOrder(BiTreePtr T);//后序遍历算法
void LevelOrder(BiTreePtr T);//层次遍历
void PreInOrd(char preord[],char inord[],int i,int j,int m,int h,BiTreePtr T);//通过先序和中序建立二叉树
void Cinput(char preord[],char inord[],int *j,int *h);
int main(){
    BiTree T = NULL;
    char preord[MAXNUM];
    char inord[MAXNUM];
    int j = 0;
    int h = 0;
    Cinput(preord,inord,&j,&h);
    // char preord[7] = {'a','b','c','d','e','f','g'};
    // char inord[7] = {'c','b','d','a','e','g','f'};
    // createTree(&T);
    // printf("end\n");
    // PreOrder(&T);
    // printf("\n");
    // InOrder(&T);
    // printf("\n");
    // LaOrder(&T);
    // printf("\n");
    // LevelOrder(&T);
    PreInOrd(preord,inord,0,j-1,0,h-1,&T);
    LaOrder(&T);
    //LevelOrder(&T);
}
void createTree(BiTreePtr T){//以先序遍历创??
    DataType data;
    scanf("%c",&data);
    if (data == '#')
    {
        //此时为空节点
        *T = NULL;
    }else{
        *T = (BiTree)malloc(sizeof(BiTreeNode));
        (*T)->data = data;
        //创建左右子树，逻辑一致进行递归
        createTree(&((*T)->lchild));
        createTree(&((*T)->rchild));
    }  
}
void PreOrder(BiTreePtr T){//先序遍历
    if ((*T) == NULL)
    {
        return;
    }else{
        //先办??
        printf("%c",(*T)->data);
        //处理左孩??
        PreOrder(&((*T)->lchild));
        //处理右孩??
        PreOrder(&((*T)->rchild));
    }   
}
void InOrder(BiTreePtr T){
    if ((*T) == NULL)
    {
        return;
    }else{
        //处理左孩??
        InOrder(&((*T)->lchild));
        //访问结点??
        printf("%c",(*T)->data);
        //处理右孩??
        InOrder(&((*T)->rchild));
    }   
}
void LaOrder(BiTreePtr T){
    if ((*T) == NULL)
    {
        return;
    }else{
        //处理左孩??
        LaOrder(&((*T)->lchild));
        //处理右孩??
        LaOrder(&((*T)->rchild));
        //访问结点??
        printf("%c",(*T)->data);
    }   
}
void LevelOrder(BiTreePtr T){
    BiTreeNode Queue[MAXNUM];
    int front,rear;
    if ((*T) == NULL) return;
    front = -1,rear = 0;
    Queue[rear] = **T;
    while (front != rear)
    {
        //双亲出队
        front++;
        printf("%c",Queue[front].data);//双亲出队
        //孩子出队
        if (Queue[front].lchild != NULL)
        {
            rear++;
            Queue[rear] = *(Queue[front].lchild);
        }
        if (Queue[front].rchild != NULL)
        {
            rear++;
            Queue[rear] = *(Queue[front].rchild);
        }
    }
    printf("\n");
}
void PreInOrd(char preord[],char inord[],int i,int j,int k,int h,BiTreePtr T){
    /*先序序列中从i到j，中序序列中从k到h*/
    if (j - i == h - k)
    {
       int m;
        (*T) = (BiTree)malloc(sizeof(BiTreeNode));
        (*T)->data = preord[i];//确定了根结点，把他保存到树结点中
        m = k;
        while (inord[m] != preord[i] && m <= h) m++;//在中序中确定根结点的位置
        if (m > h){
          free(*T);
          *T = NULL;
          printf("error");
          return;
        }
        if (m == k) (*T)->lchild = NULL;//若根节点在中序最左边，则树无左子??
        else PreInOrd(preord,inord,i+1,i+m-k,k,m-1,&((*T)->lchild));//否则创建左子??
        if (m == h) (*T)->rchild = NULL;//若根节点在中序最右边，则树无右子??
        else PreInOrd(preord,inord,i+m-k+1,j,m+1,h,&((*T)->rchild)); //否则创建右子??
    }else printf("error");
}
int CountLeaf(BiTreePtr T){//先序遍历统计叶子结点
    if ((*T) == NULL)
    {
        return 0;
    }else if ((*T)->lchild == NULL && (*T)->lchild == NULL)   return 1;//先判叶子结点   
    return CountLeaf(&((*T)->lchild))+CountLeaf(&((*T)->rchild));
}
int BitreeDepth(BiTreePtr T){
    if ((*T) == NULL) return 0;
    else if ((*T)->lchild == NULL && (*T)->lchild == NULL) return 1;
    return 1+MAX(BitreeDepth(&((*T)->lchild)),BitreeDepth(&((*T)->rchild)));
}
void Cinput(char preord[],char inord[],int *j,int *h){
    char ch1;
    int i = 0;
    int m = 0;
    ch1 = getchar();
    while (ch1 != '\n')
    {
        preord[i] = ch1;
        i++;
        ch1 = getchar();
    }
    *j = i;
    char ch2;
    ch2 =getchar();
    while (ch2 != '\n')
    {
        inord[m] = ch2;
        m++;
        ch2 = getchar();
    }
    *h = m;
}