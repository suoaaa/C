#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
#include<math.h>
using namespace std;
int main(){
	double x=0.125;
	cout<< x*log(x/3)/log(2)+(1-x)*log(1-x)/log(2)+2;
	cout<<"你好";
	return 0;
}