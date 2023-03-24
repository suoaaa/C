#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
class a3{
	public: a3(){};
	void a4(){
		static int i=0;
		cout<<i;
		i++;
	}
};
void a1(vector<int> a){
	a[0]=1;
}
void a2(vector<int> a){
	cout<<a[0];
}
int main()
{
	a3 a;
	a3 b;
	a.a4();
	b.a4();
	return 0;
}