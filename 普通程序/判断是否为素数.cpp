#include<stdio.h>
#include<math.h>
int main()
{
	int r = 2, c, s=1;
	double m;
	scanf("%d", &c);
	if (c < 4)
	{
		printf("error");
	}
	else
	{
		if (c % 2 == 0 || c % 3 == 0) s = 0;
		else
		{
			for (m = sqrt(double(c)); r < m + 1 && s != 0; r++)
			{
				s = (c % r);
			}
		}
	}
	if (s == 0)
		printf("no");
	else
		printf("yes");
	return 0;
}