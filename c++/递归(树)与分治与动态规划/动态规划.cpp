#include<iostream> 
using namespace std;
int Test(int row[],int col[],int num)
{
	if(row==NULL||col==NULL)
	{
		return -1;
	}
	for(int i=0;i<=num-1;i++)
	{	
		if(row[i]<=0||col[i]<=0)
		{
		 return -1;
		}		
	}
	return 0;
}
int BestValue(int row[],int col[],int num)
{	
	if(Test(row,col,num))
	{
		cout<<"error";
		return -1;
	}
	int m[100][100]={0};
	int temp;				
	for(int l=2;l<=num;l++) 	 
	{
		for(int i=1;i<=num-l+1;i++) 
		{
			int j=i+l-1;
			m[i][j]=1000;
			for(int k=i;k<=j-1;k++)   
			{
			 	temp=m[i][k]+m[k+1][j]+row[i-1]*row[k]*col[j-1];
				if(temp<m[i][j]) 
				{
					m[i][j]=temp;
				}					
			}
		}
	}
	return m[1][num];
}