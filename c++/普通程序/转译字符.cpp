#include<iostream>
using namespace std;
void expand (char *s ,char *t)
{
int i,j=0;
for(i=0;s[i] !='\0';i++)
{
    switch(s[i])
	{
	case '\n':t[j++]='\\';
		t[j++] ='n';
		break;
	case '\t':t[j++] ='\\';
		t[j++] ='t';
		break;
	default: t[j++] = s[i];
		break;
	}
	t[j] ='\0'; 
}
}
int main()
{
	char s[8]="abc\nd";
	char t[10];
	expand (s,t);
	puts(s);
	cout<<endl;
	puts(t);
	return 0;
}
