#include<stdio.h>
int main()
{
    char str[51];
    int i=0,num=0;
    for(i=0;i<51;i++)
    {
        scanf("%c",&str[i]);
        if(str[i]=='\n') 
        {
            str[i]='\0';
            break;
        }
    }
    i=0;
    while(str[i]!='\0')
    {
        switch (str[i])
        {
            case ' ':num++; break;
            default :break;
        }
        i++;
    }
    printf("%d",num);
    return 0;
}