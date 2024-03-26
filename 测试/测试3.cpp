/**/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<set>
#include<vector>
#include<map>
#include<algorithm>
using namespace std;
int func(const int &a){
    int *b=(int*)&a;
    *b=1;
    return *b;
}
int main(){
    cout<<func(2);
    return 0;
}