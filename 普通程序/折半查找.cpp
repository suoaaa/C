#include<stdio.h>
#include<string.h>
int main() 
{
	int a,i=0,s,x;//xС d�� m�м�ֵ  
	scanf("%d",&a);
	const int n=a;
	int nn[n];
	for(;i<(n-1);i++)
	{
		scanf("%d,",&nn[i],1);
	}
	scanf("%d",&nn[n-1],1);
	scanf("%d",&s);
	if(s==nn[0])
	printf("1");
	else
	{
		x=1;
		
    do
	{
		i=(x+a)/2;
		if(s<nn[i])
		a=i;
		else
		x=i; 
	}
	while(s!=nn[i]);
	printf("%d",i+1);
    }
	return 0;
}
