#include<stdio.h>
#include<windows.h>
#include<iostream>
using namespace std;
string GbkToUtf8(char *src_str)
{
	int len = MultiByteToWideChar(CP_ACP, 0, src_str, -1, NULL, 0);
	wchar_t* wstr = new wchar_t[len + 1];
	memset(wstr, 0, len + 1);
	MultiByteToWideChar(CP_ACP, 0, src_str, -1, wstr, len);
	len = WideCharToMultiByte(CP_UTF8, 0, wstr, -1, NULL, 0, NULL, NULL);
	char* str = new char[len + 1];
	memset(str, 0, len + 1);
	WideCharToMultiByte(CP_UTF8, 0, wstr, -1, str, len, NULL, NULL);
	string strTemp = str;
	if (wstr) delete[] wstr;
	if (str) delete[] str;
	return strTemp;
}
int main(){
    while(true){
int a=0;
    char b[128]={"你好a\0"};
    string c=GbkToUtf8(b);
    scanf("%d",&a);
    printf("%d\n%s",a,c.c_str());
    }
    
    return 0;
}
