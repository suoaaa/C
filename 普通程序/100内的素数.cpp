#include<stdio.h>
#include<math.h>
#include<stdlib.h>
int main()
{
	const int n = 100;
	int sushu[n + 1] = { 0 };
	int i=2, j=2;
	for (i = 2; i <= sqrt(float(n)); ++i)
	{
		if (sushu[i] == 0)
			for (j = 2 * i; j <= n; j += i)
				sushu[j] = 1;
	}
	for(i=2;i<=n;++i)
		if (sushu[i] == 0)
		{
			printf("%5d\n", i);
		}
	system("pause");
	return 0;
}