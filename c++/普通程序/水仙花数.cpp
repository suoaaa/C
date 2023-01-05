#include<iostream>
using namespace std;
bool narcissistic(int number)
{
	bool ret=false;
	if(number>=1000||number<=100){}
	else
	{
		int a,b,c,d;
		a=number/100;
		b=number/10-a*10;
		c=number%10;
		d=a*a*a+b*b*b+c*c*c;
		if(d==number)	{ret=true;}
	}
	return ret;
}
int main()
{
	int a=153;
	if(narcissistic(a)) cout<<'1';
	return 0;
}