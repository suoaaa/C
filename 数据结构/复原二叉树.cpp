/*�������������������������������ɶ�������Ȼ��������������������������error��*/
#include<iostream>
#include<cstring>
using namespace std;
#define MAXNUM 50
typedef char DataType;
typedef struct BiTreeNode
{   DataType data;
    struct BiTreeNode *lchild,*rchild;
}BiTreeNode;
int LaOrder(BiTreeNode* T);  //��������㷨
BiTreeNode* PreInOrd(char* preord,char* inord,int i,int j,int m,int h);//ͨ�������������������
int main(){
    BiTreeNode* T = NULL;
    char preord[MAXNUM];
    char inord[MAXNUM];
    int j = 0;
    int h = 0;
    cout<<"�ֱ������������ǰ������������"<<endl;;
    cin>>preord>>inord;
    j = strlen(preord);
    h = strlen(inord);
    try{T=PreInOrd(preord,inord,0,j-1,0,h-1);}
    catch(int flag) {return 0;}
    LaOrder(T);
    return 0;
}
int LaOrder(BiTreeNode* T){
    if (T != NULL)
    {
        LaOrder(T->lchild);//������
        LaOrder(T->rchild);//�����Һ�
        cout<<T->data;//���ʽ��
    }
    return 0;
}
BiTreeNode* PreInOrd(char* preord,char* inord,int i,int j,int k,int h){

    BiTreeNode* T=NULL;
    if(i==j||k==h)
    {
        T=new BiTreeNode;
        T->lchild=NULL;
        T->rchild=NULL;
        T->data=preord[i];
    }
    else if (j - i == h - k)
    {
        int flag=0;
        T = new BiTreeNode;
        T->lchild=NULL;
        T->rchild=NULL;
        T->data='\0';
        T->data = preord[i];//ȷ���˸���㣬�������浽�������
        int temp;
        for(int m=k;m<=h;m++)
            if(preord[m]==inord[k])
            {   temp=m;flag=1;break;   }
        if(flag==0){cout<<"error";throw flag;return NULL;}
        if(temp!=k)
           T->lchild=PreInOrd(preord,inord,i+1,i+temp-k,k,temp-1);
        if(temp!=h)
           T->rchild=PreInOrd(preord,inord,i+temp-k+1,j,temp+1,h);
    }else printf("error");
    return T;
}