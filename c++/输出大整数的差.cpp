#include<iostream>
#include<cstring>
using namespace std;
void cal(char *a,char *b);
int main()
{
    char a[15];
    char b[15];
    int i=0;
    while(i<15)
    {
        scanf("%c",&a[i]);
        if(a[i]=='\n'||a[i]==' ')
        {
            break;
        }
        cout<<a[i];
        i++;
    }cout<<'\n';
    i=0;
    while(i<15)
    {
        scanf("%c",&b[i]);
        {
            if(b[i]=='\n'||b[i]==' ')
            {
                break;
            }
        }
        cout<<b[i];
        i++;
    }cout<<'\n';
    cal (a,b);
    return 0;
}
void cal (char *a,char *b)
{
    int m=strlen(a);
    int out[m];
    int tem=0;
    int sym=0;
    int i=0;
    while(a[i]==b[i]&&i<m)
    {
    i++;
    }
    if(i==m)
    {
        printf("0");
    }
    else if(a[i]<b[i])
    {
        cout<<'-';
    }
    for(i=m-1;i>-1;i--)
    {
        tem=a[i]-b[i];
        out[i]=tem+sym;
        if(out[i]<0) 
        {
            sym=-1;
            out[i]+=10;
        }
        else sym=0;
    }
    for(i=0;i<m-1;i++)
    {
        cout<<out[i];
        //cout<<' ';
    }
}