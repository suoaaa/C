#include<iostream>
using namespace std;
void my_strcpy(char *s1);
int main()
{
    char s1[20];
    int i=0;
    scanf("%s",s1);
	my_strcpy(s1);
    while(s1[i]!='\0')
    {
        cout<<s1[i];
        i++;
    }
    return 0;
}
void my_strcpy(char *s1)
{
    int i=0;
    while(s1[i]!='\0')
    {
        i++;
    }
    s1[i]='\\';
    s1[i+1]='0';
    s1[i+2]='\0';
}