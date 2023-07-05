/*给定一个正整数 
N 。你可以对 N 的任意一位数字执行任意次以下 2 种操 作：
将该位数字加 1 。如果该位数字已经是 9 , 加 1 之后变成 0 。
将该位数字减 1 。如果该位数字已经是 0 , 减 1 之后变成 9 。
你现在总共可以执行 1 号操作不超过 A 次, 2 号操作不超过B 次。 
请问你最大可以将 N 变成多少?*/
//第一行包含 3 个整数: N,A,B 。
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<algorithm>
using namespace std;
string bestChangedNum(string N,int a,int b);
string maxString(string a,string b);
int main(){
	string N;
    int a,b;
    cin>>N>>a>>b;
    cout<<bestChangedNum(N,a,b);
    return 0;
}
string bestChangedNum(string N,int a,int b){
    if(N.length()==0) return "";
    if(a==0&&b==0) return N;
    string ret=N;
    ret.erase(ret.begin());
    cout<<' '<<N<<'\t'<<a<<'\t'<<b<<endl;
    if(N[0]=='9') return to_string(9)+bestChangedNum(ret,a,b);
    int i=9+'0'-N[0];
    int j=(1+N[0]-'0')%10;
    if(a>=i&&b>=j)    return to_string(9)+max(bestChangedNum(ret,a-i,b),bestChangedNum(ret,a,b-j));
    	else {
    		if(a>=i) return to_string(9)+bestChangedNum(ret,a-i,b);
     	   	if(b>=j) return to_string(9)+bestChangedNum(ret,a,b-j);
     	}
    	return to_string(N[0]-'0'+a)+bestChangedNum(ret,0,b);
}
string maxString(string s1,string s2){
    int a=s1.length(),b=s2.length();
    if(a!=b) return a>b?s1:s2;
    for(int i=0;i<a;i++){
        if(s1[i]!=s2[i]) return s1[i]>s2[i]?s1:s2;
    }
    return s1;
}