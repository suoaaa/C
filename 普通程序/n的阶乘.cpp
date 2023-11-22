#include<iostream>
using namespace std;
int main()
{
	int n=0;
	int res=1;
	cin>>n;
	if(n>12)
	{
		n=12;
	}
	for(int i=1;i<n+1;i++)
	{
		res*=i;
	 }
	 cout<<n<<"!="<<res;
	return 0;
	
}
