#include<stdio.h>
#include<math.h>
int main()
{
	double interest=0,money=0;
	double rate=0,year=0;
	scanf("%lf %lf %lf",&money,&year,&rate);
	rate=rate+1;
	interest=money*pow(rate,year)-money;
	printf("%0.2lf",interest);
 } 
