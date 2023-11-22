#include<stdio.h>
#include<stdlib.h>
int main()
{
	int h, n;
	for (int h = 1; h <= 9; h++)
	{
		for (int n=1; n <= 9;n++)
		{
			printf("%d ", n * h);

		}
		printf("\n");
	}
	system("pause");
	return 0;
}