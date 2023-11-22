#include <iostream>
using namespace std;
int number[10]={2021,2021,2021,2021,2021,2021,2021,2021,2021,2021};
int main()
{
  bool flag=1;
  int a=0,b=0,c=0,d=0;
  int i=1;
  int n=1;
  for(;;i++)
  {
    n=i;
    while(n>=10)
    {
        number[n%10]--;
        if(number[n%10]<0) {cout<<i-1;return 0;}
        n/=10;
    }
    number[n]--;
    if(number[n]<0) {cout<<i-1;return 0;}
  }
}