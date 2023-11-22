#include<iostream>
#include<cstring>
using namespace std;
int main()
{
    char a[31];
    scanf("%s",a);
    int n=strlen(a);
    int j=0;
    for(int i=0;i<n/2;i++)
    {
        if(a[i]!=a[n-i-1]) 
        {
            j=-1;
            break;
        }
    }
    switch(j)
    {
        case 0:cout<<"true";break;
        case -1:cout<<"false";break;
    }
    return 0;

}