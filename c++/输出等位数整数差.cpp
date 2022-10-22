#include<iostream>
#include<cstring>
using namespace std;
void cal(char *a,char *b);
void min(char *big,char *small,int m,int i);
int main()
{
    char a[15];
    char b[15];
    int i=0;
    scanf("%s %s",a,b);
    // while(i<15)
    // {
    //     scanf("%c",&a[i]);
    //     if(a[i]=='\n'||a[i]==' ')
    //     {
    //         a[i]='\0';
    //         break;
    //     }
    //     //cout<<a[i]<<'.';
    //     i++;
    // }
    // //cout<<'\n';
    // i=0;
    // while(i<15)
    // {
    //     scanf("%c",&b[i]);
    //     {
    //         if(b[i]=='\n'||b[i]==' ')
    //         {
    //             b[i]='\0';
    //             break;
    //         }
    //     }
    //     //cout<<b[i]<<'.';
    //     i++;
    // }
    // //cout<<'\n';
    cal (a,b);
    return 0;
}
void cal (char *a,char *b)
{
    int m=strlen(a);
    //cout<<m;
    int i=0;
    while(a[i]==b[i]&&i<m)
    {
        i++;
        //cout<<"uuu1";
    }
    if(i==m)
    {
        printf("0");
    }
    else 
    {
        if(a[i]<b[i])
    	{
        	cout<<'-';
        	min (b,a);
    	}
    	else min(a,b,m,i);
    }
}
void min(char *big,char *small,int m,int i)
{
    int out[20]={0};
    out[m]='\0';
    int sym=0;
    int j=m;
    for(;j>i-1;j--)
    {
        out[j]=int(big[j]-small[j])+sym;
        if(out[j]<0) 
        {
            sym=-1;
            out[j]+=10;
        }
        else sym=0;
    }
    for(i=0;i<m;i++)
    {
        cout<<out[i];
        //cout<<' ';
    }
}