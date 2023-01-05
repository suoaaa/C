#include <iostream>
#include<cstring>
using namespace std;
int main()
{
  int word[26]={0};
  char a[1001]={0};
  bool c=1;
  cin>>a;
  int j=0;
  for(int i=0;i<1001&&a[i];i++)
  {
    word[a[i]-'a']+=1;
    j=i+1;
  }
  char longest_word;
  int longest_times=0;
  for(int i=0;i<26;i++)
  {
    if(longest_times<word[i])
    {
      longest_word='a'+i;
      longest_times=word[i];
    }
  }
  cout<<longest_word<<endl<<longest_times;
  return 0;
}