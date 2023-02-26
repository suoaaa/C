#include<stdio.h>
int main()
{
	int n;
	scanf_s("%d", &n);
	printf("%d", n);
	int q = n;
	for (int i =2; i < 6; i++)
	{
		printf(" %d", q = q * n);
	}
	printf("\n");
	return 0;
}