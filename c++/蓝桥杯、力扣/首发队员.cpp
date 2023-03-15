#include <iostream>
using namespace std;
int one[20] = {97, 92, 0, 0, 89, 82, 0, 0, 0, 95, 0, 0, 94, 0, 0, 0, 98, 93, 0, 0};
int two[20] = {90, 85, 0, 0, 83, 86, 0, 97, 0, 99, 0, 0, 91, 83, 0, 0, 83, 87, 0, 99};
int three[20] = {0, 96, 0, 0, 97, 0, 0, 96, 89, 0, 96, 0, 0, 87, 98, 0, 99, 92, 0, 96};
int four[20] = {0, 0, 0, 80, 0, 0, 87, 0, 0, 0, 97, 93, 0, 0, 97, 93, 98, 96, 89, 95};
int five[20] = {0, 0, 93, 86, 0, 0, 90, 0, 0, 0, 0, 98, 0, 0, 98, 86, 81, 98, 92, 81};
int main()
{
  int max=0;
  int temp;
  for(int i=0;i<20;i++)
  {
    if(one[i]==0)continue;
    else    for(int j=0;j<20;j++)
    {
      if(j==i||two[j]==0)continue;
      else for(int k=0;k<20;k++)
      {
        if(k==i||k==j||three[k]==0)continue;
        else for(int m=0;m<20;m++)
        {
          if(m==i||m==j||m==k||four[m]==0)continue;
          else for(int n=0;n<20;n++)
          {
            if(n==i||n==j||n==k||five[n]==0)continue;
            else 
            {
                temp=one[i]+two[j]+three[k]+four[m]+five[n];
                max=temp>max?temp:max;
            }
          }
        }
      }
    }
  }
  cout<<max;
  return 0;
}