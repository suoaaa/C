#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
void a1(vector<int> a){
	a[0]=1;
}
void a2(vector<int> a){
	cout<<a[0];
}
int main()
{
	vector<int> a(1,0);
	a1(a);
	a2(a);
	// string a="111 ";
	// a+="23124";
	// cout<<a;
}