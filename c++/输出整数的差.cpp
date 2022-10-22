#include<iostream>
#include<cstring>
using namespace std;
void cal(char *a,char *b);
void min(char *big,char *small,int b_n,int s_n);
void min(char *big,char *small,int memory);
int main()
{
    char a[15];
    char b[15];
    int i=0;
    scanf("%s %s",a,b);
    cal (a,b);
    return 0;
}
void cal (char *a,char *b)
{
    int b_n=strlen(a);
    int s_n=strlen(b);
    int i=0;
    if(b_n==s_n)
    {
        while(a[i]==b[i]&&i<b_n)
        {
            i++;
        }
        if(i==b_n)
        {
            printf("0");
        }else
        {
            if(a[i]<b[i])
            {
                cout<<'-';
                min(b,a,b_n);
            }else min(b,a,b_n);
        }
    }
    else 
    {
        if(b_n>s_n)
        {
            min(a,b,b_n,s_n);
        }
        else min(b,a,s_n,b_n);
    }
}
void min(char *big,char *small,int memory)
{
    int out[20]={0};
    int m=strlen(big);
    out[m]='\0';
    int sym=0;
    int j=m;
    for(;j>memory-1;j--)
    {
        out[j]=int(big[j]-small[j])+sym;
        if(out[j]<0) 
        {
            sym=-1;
            out[j]+=10;
        }
        else sym=0;
    }
    for(j=0;j<m-1;j++)
    {
        cout<<out[j];
        //cout<<' ';
    }
}
void min(char *big,char *small,int b_n,int s_n)
{
    int out[20]={0};
    out[b_n]='\0';
    int sym=0;
    int i=b_n;
    while(s_n>-1)
    {
        out[i]=int(big[i]-small[i])+sym;
        if(out[i]<0)
        {
            sym=-1;
            out[i]+=10;
        }
        else sym=0;
        i--;s_n--;
    }
    out[i]=int(big[i]-'0')+sym;
    i--;
    while(i>-1)
    {
        out[i]=int(big[i]-'0');
    }
    for(s_n=0;s_n<b_n-1;s_n++)
    {
        cout<<out[s_n];
        //cout<<' ';
    }
}