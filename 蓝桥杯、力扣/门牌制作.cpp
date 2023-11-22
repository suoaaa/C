#include <iostream>
using namespace std;
int main()
{
  int n=0;
  int a=0,b=0,c=0,d=0;
  for(int i=0;i<=2020;i++)
  {
    a=i/1000;b=i/100-a*10;d=i%10;c=i%100/10;
    if(a==2) n++;
    if(b==2) n++;
    if(c==2) n++;
    if(d==2) n++;
  }
  cout<<n;
  return 0;
}