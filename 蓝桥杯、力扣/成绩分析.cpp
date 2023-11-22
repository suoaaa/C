#include <iostream>
using namespace std;
int main()
{
  int n=0,score;
  float pass=0,exce=0;
  cin>>n;
  for(int i=0;i<n;i++)
  {
    cin>>score;
    if(score>=60)
    {
      pass++;
      if(score>=85)
        exce++;
    }
  }
  int a=0.5+pass*100/n;
  int b=0.5+exce*100/n;
  cout<<a<<"%"<<endl<<b<<"%";
  return 0;
}