#include<iostream> 
#include<cstring>
using namespace std;
int main()
{
    int pos=0;
    int n=0,i=0,j=0;
    cin>>n;
	float rec[10][10];
    int a[10];
    int b[10];
    memset(a,0,sizeof(a));
    memset(b,0,sizeof(b));
    cout<<b[4];
    for(i=0;i<n;i++)
    {
        for(j=0;j<n;j++)
        {
            cin>>rec[i][j];
        }
    }
    int m=0;
    for(i=0;i<n;i++)
    {
        if(!b[i])
        {
            b[i]=1;
            for(j=0;j<n;j++)
        	{
            	if(rec[i][j])
            	{
            	   	a[j]=1;
                	b[j]=1;
            	}
        	}
            cout<<b[4]<<' ';
        	for(m=0;m<n;m++)
        	{
        	    if(a[m])
                {
                    for(j=0;j<n-i;j++)
        			{
            			if(rec[m][j])
            			{
            		   	a[j]=1;
                		b[j]=1;
            			}
        			}
                }
        	}
        	a[i]=1;
            pos++;
        	memset(a,-1,sizeof(a));
            cout<<b[4]<<' ';
            }
    }
    cout<<pos;
    return 0;

}