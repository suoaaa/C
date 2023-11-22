#include<stdio.h>
#include<stdlib.h>

int my_strlen1(char* str);//普通的
int my_strlen2(char* str);//没用中间变量
int main()
{
	char p[4] = { 'l','s','d'};//注意最多放n-1个字符，有一个位置放的是'\0';
	printf("%d", my_strlen2(p));
	return 0;
}
int my_trlen1(char* str)
{
	int count = 0;
	while (*str != '\0')
	{
		count++;
		str++;
	}
	return count;
}
int my_strlen2(char* str)
{
	if (*str !='\0')
	{
		return 1 + my_strlen2(str+1);
	}
	else
	{
		return 0;
	}
}