//本程序由王克独立编程完成
//本程序完成1，4功能
#include<iostream>
#include<stdlib.h>
using namespace std;
#define fname "stuScores.txt"
typedef struct score
{
	int id;
	char name[50];
	float chinese;
	float math;
	float english;
    float sco;
}score;
FILE* open(void);
void nopass(void);
void rank_list(void);
int main()
{
    cout<<"本程序链表实现"<<endl<<"本程序由王克独立编程完成";
    cout<<endl<<"实现了1，4二个功能"<<endl;
    cout<<"**********************菜单*********************"<<endl<<endl;
    cout<<"1.统计平均分的不及格人数并打印不及格学生名单"<<endl;
    cout<<"4.按总分成绩由高到低排出成绩名次"<<endl;
    cout<<"按0退出"<<endl;
    cout<<"请输入："<<endl;
    int p=100;
    while (p)
    {
    	cin>>p;
        switch(p)
        {
            case 0:exit(0);break;
            case 1:nopass();break;
            case 4:rank_list();break;
        }
    }
}
FILE *open()
{
    FILE *fp=fopen(fname,"r");
    int n;
    if(fp==NULL)
    {
        cout<<"文件打开失败";
        fclose(fp);
        cin.get();
        exit(0);
    }
    else
    {
        return fp;
    }
}
void nopass(void)
{
    score list[20];
    FILE *fp=open();
    int i=0;
    int all=0;
    int ave=0;
    while(!feof(fp))
    {
        fscanf(fp,"%d %s %f %f %f",&list[i].id,list[i].name,&list[i].chinese,&list[i].math,&list[i].english);
        list[i].sco=list[i].chinese+list[i].math+list[i].english;
        all=all+list[i].sco;
        i++;
    }
    ave=all/i;
    all=0;
    for(int j=0;j<i;j++)
    {
        if(list[i].sco<ave) 
        {
            cout<<list[i].id<<"    "<<list[i].name<<endl;
            all++;
        }
    }
    cout<<"共有"<<all<<"人不及格";
}
void rank_list(void)
{
	score list[20];
    FILE *fp=open();
    int i=0;
    int all=0;
    while(!feof(fp))
    {
        fscanf(fp,"%d %s %f %f %f",&list[i].id,list[i].name,&list[i].chinese,&list[i].math,&list[i].english);
        list[i].sco=list[i].chinese+list[i].math+list[i].english;
        all=all+list[i].sco;
        i++;
    }
    int k=0;
    score tem;
    for(int j=0;j<i;j++)
    {
        for(k=0;k<i-j-1;k++)
        {
            if(list[k].sco<list[k+1].sco)
            {
                tem=list[k];
                list[k]=list[k+1];
                list[k+1]=tem;
            }
        }
    }
    for(int j=0;j<i;j++)
    {
        cout<<"第"<<i+1<<"名为";
    	cout<<list[i].id<<"  "<<list[i].name<<"  "<<list[i].sco;
        cout<<endl;
    }
}