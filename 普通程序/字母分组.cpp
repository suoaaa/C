#include<stdio.h>
int main()
{
	char word[20];
	char *p= &word[0];
	int i=0,c=0;
	gets(word);
	for(;p[c]!='\0';c++)
	{
	}
	if(p[c-1]=' ') 
	c--;
	while(i<c-2){
		printf("%c%c*",p[i],p[i+1]);
		i+=2;
	}
	if(c%2==0) printf("%c%c\n",p[c-2],p[c-1]);
	else printf("%c\n",p[c-1]);
	return 0;
 } 
