#include<iostream>
using namespace std;
int main()
{
	double PI=0;
	double i;
	int n=0;
	double t=1;
	for(i=1;1/i>=1e-8;i=i+2)
	{
		PI=PI+t*1/i;
		t=-t;
		n++;
	}
	printf("steps=%d PI=%.5lf",n+1,PI*4);
	return 0;
}
