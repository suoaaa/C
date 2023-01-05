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
void createTree(BiTreePtr T);//���򴴽�����??
void PreOrder(BiTreePtr T);//�������
void InOrder(BiTreePtr T);//�������
void LaOrder(BiTreePtr T);//��������㷨
void LevelOrder(BiTreePtr T);//��α���
void PreInOrd(char preord[],char inord[],int i,int j,int m,int h,BiTreePtr T);//ͨ�������������������
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
void createTree(BiTreePtr T){//�����������??
    DataType data;
    scanf("%c",&data);
    if (data == '#')
    {
        //��ʱΪ�սڵ�
        *T = NULL;
    }else{
        *T = (BiTree)malloc(sizeof(BiTreeNode));
        (*T)->data = data;
        //���������������߼�һ�½��еݹ�
        createTree(&((*T)->lchild));
        createTree(&((*T)->rchild));
    }  
}
void PreOrder(BiTreePtr T){//�������
    if ((*T) == NULL)
    {
        return;
    }else{
        //�Ȱ�??
        printf("%c",(*T)->data);
        //������??
        PreOrder(&((*T)->lchild));
        //�����Һ�??
        PreOrder(&((*T)->rchild));
    }   
}
void InOrder(BiTreePtr T){
    if ((*T) == NULL)
    {
        return;
    }else{
        //������??
        InOrder(&((*T)->lchild));
        //���ʽ��??
        printf("%c",(*T)->data);
        //�����Һ�??
        InOrder(&((*T)->rchild));
    }   
}
void LaOrder(BiTreePtr T){
    if ((*T) == NULL)
    {
        return;
    }else{
        //������??
        LaOrder(&((*T)->lchild));
        //�����Һ�??
        LaOrder(&((*T)->rchild));
        //���ʽ��??
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
        //˫�׳���
        front++;
        printf("%c",Queue[front].data);//˫�׳���
        //���ӳ���
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
    /*���������д�i��j�����������д�k��h*/
    if (j - i == h - k)
    {
       int m;
        (*T) = (BiTree)malloc(sizeof(BiTreeNode));
        (*T)->data = preord[i];//ȷ���˸���㣬�������浽�������
        m = k;
        while (inord[m] != preord[i] && m <= h) m++;//��������ȷ��������λ��
        if (m > h){
          free(*T);
          *T = NULL;
          printf("error");
          return;
        }
        if (m == k) (*T)->lchild = NULL;//�����ڵ�����������ߣ�����������??
        else PreInOrd(preord,inord,i+1,i+m-k,k,m-1,&((*T)->lchild));//���򴴽�����??
        if (m == h) (*T)->rchild = NULL;//�����ڵ����������ұߣ�����������??
        else PreInOrd(preord,inord,i+m-k+1,j,m+1,h,&((*T)->rchild)); //���򴴽�����??
    }else printf("error");
}
int CountLeaf(BiTreePtr T){//�������ͳ��Ҷ�ӽ��
    if ((*T) == NULL)
    {
        return 0;
    }else if ((*T)->lchild == NULL && (*T)->lchild == NULL)   return 1;//����Ҷ�ӽ��   
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