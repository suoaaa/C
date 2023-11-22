#include<stdio.h>
#include<stdlib.h>
int main()
{
	float s1 = 0, s2 = 0,c=0;
	char op,a='y';
		for (;a=='y'||a=='Y';)
		{
			scanf("%f ", &s1);
			scanf("%c ", &op);                                                                                            
			scanf("%f", &s2);
			switch (op)
			{
			case '+':
			{
				c = s1 + s2;
				printf("%.2f + %.2f = %.2f\n", s1, s2, c);
				scanf("\n%c", &a);
				continue;
			}
			case '-':
			{
				c = s1 - s2;
				printf("%.2f - %.2f = %.2f\n", s1, s2, c);
				scanf("\n%c", &a);
				continue;
			}
			case '*':
			{
				c = s1 * s2;
				printf("%.2f * %.2f = %.2f\n", s1, s2, c);
				scanf("\n%c", &a);
				continue;
			}
			case '/':
			{
				if (s2 == 0)
				{
					printf("error\n");
					scanf("\n%c", &a);
					continue;
				}
				else
				{
					c = s1 / s2;
					printf("%.2f / %.2f = %.2f\n", s1, s2, c);
					scanf("\n%c", &a);
					continue;
				}
			}//case/
			default:
			{
				printf("error\n");
				scanf("\n%c", &a);
				continue;
			}//switch
			}//if
		}
	return 0;
}
