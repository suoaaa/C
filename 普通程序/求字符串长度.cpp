#include<stdio.h>
#include<stdlib.h>

int my_strlen1(char* str);//��ͨ��
int my_strlen2(char* str);//û���м����
int main()
{
	char p[4] = { 'l','s','d'};//ע������n-1���ַ�����һ��λ�÷ŵ���'\0';
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