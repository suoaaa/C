#include<stdio.h>
#include<math.h>
int main() 
{
	int N,i,j,b,c;
	double a=0;
	c=scanf("%d",&N);
	if(N<=1||c==0)
	{
		printf("error");
	}
	else
	{
		for(i=1;i<=N;++i)
		{
			a=sqrt(double(i));
			for(j=2;j<=a;++j)
			{
				b=i%j;
				if(b==0)
				{
					c=i;
			    }
			}
			if(c!=i)
			printf("%d,",i);
		}
	}	
	return 0;
} 
