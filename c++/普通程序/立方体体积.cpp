#include <stdio.h>
int volume(int a,int b,int c)
{
int p;
p=a*b*c;
return (p);
}
int main()
{
	int x,y,z,v;
	printf("������������������");
	scanf("%d %d %d",&x,&y,&z);
	v=volume(x,y,z);
	printf("%d\n",v);
	return 0;
}

