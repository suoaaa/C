#include<iostream>
using namespace std;
int main()
{
	int n;
	cin>>n;
	if(n<1||n>80||n%2==0)
	{
		cout<<"error";
	}
	else
	{
		int i=0,j=0;
		for( i=0;i<n;i+=2)
		{
			while(j++<i/2)
			{
				cout<<' ';
			}
			j=0;
			while(j++<(n-i)/2)
			{
				cout<<'*';
			}
			j=0;
			cout<<'*';
			while(j++<(n-i)/2)
			{
				cout<<'*';
			}
			j=0;
			j=0;
			cout<<'\n';
		}
	}
	return 0;
 } 
