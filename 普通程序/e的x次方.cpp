#include<stdio.h>
#include<math.h>
#include<stdlib.h>
int main()
{
	double n, x, e = 2.718281, p = 1, i = 1, q = 1;
	scanf_s("%lf %lf", &x, &n);
	if (n < 0||x<0)
	{
		printf("error");
	}
	else
	{
		for (; i < n + 1; i++)
		{
			q = q * i;
			p =p+ ((pow(x, i))/q);
		}
	}
	printf("%.6lf\n",p);
	return 0;

}