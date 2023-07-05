#include<stdio.h>
#include<string.h>
int main()
{
	int a=0,d=0,c=0;
	char st[100];
	scanf("%s",st);
	c=strlen(st);
	for(int i=0;i<c;i++)
	{
		switch(st[i])
		{
			case 'a':
				a=a+1;
				continue;
			case'd':
				d=d+1;
				continue;
			default:
				continue;
		}
	}
	printf("a:%d,d:%d",a,d);
	return 0;
 } 
