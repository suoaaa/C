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
    }
    while(i<15)
    {
        scanf("%c",&b[i]);
        {
            if(b[i]=='n'||b[i]==' ')
            {
                break;
            }
        }
        cout<<b[i];
        i++;
    }
    cal (a,b);
    return 0;
}
void cal (char *a,char *b)
{
    int m=strlen(a);
    int n=strlen(b);
    int tem=0;//储存减位数字
    char out[15];
    for(;m>-1&&n>-1;m--,n--)
    {

    }
}