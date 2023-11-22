#include<iostream>
#include<cstring>
using namespace std;
void cal(char *a,char *b);
void min_2(char *big,char *small,int b_n,int s_n);
void min_1(char *big,char *small,int memory);
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
        if(a[i]=='\0')
        {
            printf("0");
        }else
        {
            if(a[i]<b[i])
            {
                cout<<'-';
                min_1(b,a,i);
            }else 
            {
                cout<<'+';
                min_1(a,b,i);
            }
        }
    }
    else 
    {
        if(b_n>s_n)
        {
            cout<<'+';
            min_2(a,b,b_n,s_n);
        }
        else 
        {
            cout<<'-';
            min_2(b,a,s_n,b_n);
        }
    }
}
void min_1(char *big,char *small,int memory)
{
    int out[15]={0};
    int m=strlen(big);
    out[m]='\0';
    int sym=0;
    int j=m-1;
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
    for(j=0;j<m;j++)
    {
        cout<<out[j];
    }
}
void min_2(char *big,char *small,int b_n,int s_n)
{
    int out[15]={0};
    out[b_n]='\0';
    int sym=0;
    int i=b_n-1;
    int j=s_n-1;
    while(j>-1)
    {
        out[i]=int(big[i]-small[j])+sym;
        if(out[i]<0)
        {
            sym=-1;
            out[i]+=10;
        }
        else sym=0;
        i--;j--;
    }
    out[i]=int(big[i]-'0')+sym;
    i--;
    while(i>-1)
    {
        out[i]=int(big[i]-'0');
        i--;
    }
    for(i=0;i<b_n;i++)
    {
        cout<<out[i];
        //cout<<' ';
    }
}