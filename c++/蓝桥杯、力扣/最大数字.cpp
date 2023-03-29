/*����һ�������� 
N ������Զ� N ������һλ����ִ����������� 2 �ֲ� ����
����λ���ּ� 1 �������λ�����Ѿ��� 9 , �� 1 ֮���� 0 ��
����λ���ּ� 1 �������λ�����Ѿ��� 0 , �� 1 ֮���� 9 ��
�������ܹ�����ִ�� 1 �Ų��������� A ��, 2 �Ų���������B �Ρ� 
�����������Խ� N ��ɶ���?*/
//��һ�а��� 3 ������: N,A,B ��
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