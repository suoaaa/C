#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
class test{
	public: a3(){};
	void a4(){
		static int i=0;
		cout<<i;
		i++;
	}
};
void test1(vector<int> a){
	a[0]=1;
}
void test2(vector<int> a){
	cout<<a[0];
}
int main()
{
	return 0;
}