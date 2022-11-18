/*设有一组关键字{9,01,23,14,55,20,84,27}，采用哈希函数：H（key）=key mod 7 ，表长为10，
用开放地址法的二次探测再散列方法Hi=(H(key)+di) mod 10(di=1,4,9,…,)解决冲突。
要求：对该关键字序列构造哈希表，并计算查找成功的平均查找长度。*/
#include<iostream>
#include<cstring>
using namespace std;
int n=0;
//思想:初始化查找平均长度为1，构建哈希表每当遇到冲突的时候用ser[n]记录冲突次数就是查找次数
int get_key(int key)
{return key%7;}
int collide(int hash[],int Key,int ser[])
{   int i=1;
    ser[n]=2;
    while(hash[(Key+i*i)%10]!=0)
    {   i++;    ser[n]+=1;  }
    n++;    return (Key+i*i)%10;}
void creat_hash(int hash[],int data[],int ser[])
{   int m=8;
    int Key=0;
    for(int i=0;i<m;i++)
    {        
        Key=get_key(data[i]);
        if(hash[Key]==0) hash[Key]=data[i];
        else hash[collide(hash,Key,ser)]=data[i];
    }}
int main()
{
    int hash[10]={0,0,0,0,0,0,0,0,0,0};
    int ser[8]={1,1,1,1,1,1,1,1};//查找平均长度
    int key[8]={9,01,23,14,55,20,84,27};//关键字集合
    double add_collide=0;
    creat_hash(hash,key,ser);
    for(int i=0;i<8;i++)
    {   add_collide+=ser[i];}    
    for(int i=0;i<10;i++)
    {   cout<<i<<"\t";} cout<<endl;
    for(int i=0;i<10;i++)
    {   if(hash[i]!=0) cout<<hash[i];
        cout<<"\t";}
    cout<<endl<<"查找成功的平均长度为:"<<add_collide/8<<endl;
    return 0;
}