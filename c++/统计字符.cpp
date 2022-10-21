#include <iostream>
#include<string.h>
using namespace std;
int SubStrNum(char * str,char * substr);
//int main()
//{	char str[20]={"ooneloneone\0"};
//	char substr[10]={"one\0"};
	//SubStrNum(str, substr);
	//cout<<SubStrNum(str, substr)<<endl;
//	return 0;
//}
int SubStrNum(char * str,char * substr)
{
	int a=strlen(str);
	int b=strlen(substr);
	int c=0;
	int j=1;
	int num=0;
	for(int i=0;i<a-1;i++)
	{
		if(str[i]==substr[0])
		{
			c=i;
			while(str[c+j]==substr[j]&&c+j<a-1&&j<b-1)
			{
				j++;
			}
			if(j==b-1)
			{
				num++;
				i=c;
			}
			j=1;
		}
	}
	cout<<"match times="<<num;
	return num;
}