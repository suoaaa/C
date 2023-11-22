#include<iostream>
using namespace std;
int main()
{
	char formula[30];
	double ele[20];//数字 
	char oper[10];//运算符 
	int j=0,p=0,o=0;
	double tem=0;
	int i=0;
	//j用来算ele（位数）// p表示几个ele和oper//o判断小数点前或者后第几位 
	for(i=0;i<30;i++)
	{
		formula[i]=getchar();
		switch(formula[i])
		{
			//case '\0': i=100;o=0;j=0;break;
			case '\n': i=100;o=0;j=0;cout<<"error";break;
			case ';': i=100;o=0;j=0;break;
			case '+' : oper[p]='+';o=0;j=0;p++;break;
			case '-' : oper[p]='-';o=0;j=0;p++;break;
			case '.' : o=1;break;
			default : if(formula[i]>57||formula[i]<48)
			{cout<<"error";return 0;break;}
			else{
			if(j==0)
			{
				ele[p]=formula[i]-48; 
				j++;
			}
			else {if(o==0)
			{
				ele[p]=ele[p]*10+formula[i]-48; 
				j++;
			}
			else if(o!=0)
			{
				tem=formula[i]-48;
				for(int a=o;a>0;a--)
				{
					tem=tem/10;
				}					
				ele[p]=ele[p]+tem;
				o++;
			}
			}
			break;
		 }}
	}
	double res=ele[0];
	for(i=0;i<p;i++)
		 {
		 	switch (oper[i])
		 	{
		 		case '+' : res=res+ele[i+1];break;
		 		case '-' : res=res-ele[i+1];break;
		 		default: break;
			 }
		 }
		 printf("%0.6lf",res);
	return 0;
}
