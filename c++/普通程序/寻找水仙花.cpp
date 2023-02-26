#include<stdio.h>
#include<stdlib.h>
int find(int n)
{
	if(n>=1000||n<=100)
	return 0;
	else
	{
	int a,b,c,d,i,e=0;
		for(i=100;i<=n;i++)
		{
		a=i/100;
		b=i/10-a*10;
		c=i%10;
		d=a*a*a+b*b*b+c*c*c;
		if(d==i){
			e++;
		}
		}	
		return e;
	}

 } 
