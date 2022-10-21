#include<iostream>
#include<cstring>
using namespace std;
int SubStrNum(char * str,char * substr);
int main()
{
    char str[20];
    char substr[5];
    int i=0;
    for (i=0;i<20;i++)
    {
        scanf("%c",str[i]);
        if(cin.get=='\n'||i=19)
        {
            str[i]='\0';break;
        }
    }
    for(i=0;i<5;i++)
    {
        scanf("%c",substr[i]);
        if(cin.get=='\n'||i=4)
        {
            substr[i]='\0';
            break;
        }
    }
    SubStrNum(str,substr);
    return 0;
}
int SubStrNum(char * str,char * substr)
{
    int a=strlen(str);
    int b=strlen(substr);
    if(a<b) return 0;
    else{
    int j=0;
    int num=0;
    int i=0;
    while(i<a)
    {
        if(str[i]==substr[j])
        {
            j++;
            if(j==b)
        	{
            	num++;j=0;
        	}
        }else j=0;  i++;   
    }
    cout<<"match times="<<num;
    return num;
}
}