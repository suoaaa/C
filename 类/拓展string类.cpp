#include <iostream>
#include<cstring>
using namespace std;
class String{
protected:
	char *mystr;
	int len;
public:
	String(const char *p)
    {   len = strlen(p);    mystr = new char[len+1];    strcpy(mystr,p);};
	~String()
    {   if (mystr !=NULL)   {  delete []mystr;mystr = NULL; len = 0;}};
	void showStr(){cout <<mystr;};
	char *GetStr(){return mystr;};
	virtual bool IsSubString(const char *str)
    {   int i,j;
		for (i =0;i<len;i++)
		{   int k = i;
			for (j =0;str[j] !='\0';j++,k++)
			    if (str[j]!= mystr[k]) break;
			if(str[j] =='\0') return true;
		}
		return false;
	}
};
class EditString:public String{
public:
	EditString(const char *p):String(p){}
	int IsSubString(int start, const char *str);//从start处开始判断str是否为子串，是则返回子串第一次出现的下标，否则返回-1
	void EditChar(char s, char d); //用字符d代替所有字符s
	void EditSub(char * subs,char *subd); //用字符串subd代替所有字符串subs
	void DeleteChar(char ch);  //将所有ch字符删除
	void DeleteSub(char * sub); //将所有的字符串sub删除
};
int EditString::IsSubString(int start, const char *str)//从start处开始判断str是否为子串，是则返回子串第一次出现的下标，否则返回-1
{
    int k=0;int j=0;
    for(int i=start;i<len;i++)
    {
		int k = i;
		for (j =0;str[j] !='\0';j++,k++)
			if (str[j]!= mystr[k]) break;
		if(str[j] =='\0') return i;
    }
    return -1;
}
void EditString::EditChar(char s, char d) //用字符d代替所有字符s
{
    for(int i=0;i<len;i++)
    {if(mystr[i]==s) mystr[i]=d;}
}
void EditString::EditSub(char * subs,char *subd) //用字符串subd代替所有字符串subs
{
    int flag=IsSubString(0,subs);
    int s=strlen(subs),d=strlen(subd);
    int i=0;//记录替换次数；
    int j=0;//循环用，用于更换后的字符串赋值
    int k=0;//用于被替换的字符赋值
    char *newstr=new char [len*d/s+len];
    while(flag!=-1)
    {
        for(;j+i*s<flag;j++)
            newstr[j+i*d]=mystr[j+i*s];
        for(k=0;k<d;k++)
            newstr[j+i*d+k]=subd[k];
        flag=IsSubString(flag+s,subs);
        i++;
    }
    for(;j+i*s<=len;j++)
        newstr[j+i*d]=mystr[j+i*s];
    newstr[j+i*d]='\0';
    mystr=newstr;
    len=strlen(newstr);
}
void EditString::DeleteChar(char ch)  //将所有ch字符删除
{
    int i=0;//循环用
    char *newstr=new char [len+1];
    int n=0;//记录删除次数
    for(;i<=len;i++,n++)
        if(mystr[i]!=ch) newstr[n]=mystr[i];
        else {n--;}
    len=n;
    mystr=newstr;
}
void EditString::DeleteSub(char * sub) //将所有的字符串sub删除--实现类似于EditSub()函数
{
    int flag=IsSubString(0,sub);
    int s=strlen(sub);
    int i=0;//记录删除次数；
    int j=0;//循环用，用于删除后的字符串赋值
    int k=0;//用于被替换的字符赋值
    char *newstr=new char [len+1];
    while(flag!=-1)
    {
        for(;j<flag;j++)
            newstr[j-i*s]=mystr[j];
        j+=s;
        flag=IsSubString(flag+s,sub);
        i++;
    }
    for(;j<=len;j++)
        newstr[j-i*s]=mystr[j];
    newstr[j-i*s]='\0';
    mystr=newstr;
    len=strlen(newstr);
}
int main()
{
    EditString one("helloonehellooneoneone");
    char one1[4]={"one"};
    char two[4]={"two"};
    one1[3]='\0';
    two[3]='\0';
    char t='t';
    char w='w';
    char l='l';
    char wwo[4]={"wwo"};
    wwo[3]='\0';
    one.showStr();cout<<endl;
    one.EditSub(one1,two);
    one.showStr();cout<<endl;
    one.EditChar(t,w);
    one.showStr();cout<<endl;
    one.DeleteChar(l);
    one.showStr();cout<<endl;
    one.DeleteSub(wwo);
    one.showStr();cout<<endl;
    return 0;
}