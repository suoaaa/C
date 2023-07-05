#include<iostream>
using namespace std;
int main()
{
	int year,month;
	char a;
	int day;
	cin>>year>>month;
	if(month>12) month=month%12;
	switch(month)
	{
		case 1:day=31;break;
		case 2:
			if(!(year%100))
			{
				if(!(year%400))
				{
					day=29;
				}
				else day=28;
			}
			else
			{
				if(!(year%4))
				{
					day=29;
				}
				else day=28;
			}
			break;
		case 3:day=31;break;
		case 4:day=30;break;
		case 5:day=31;break;
		case 6:day=30;break;
		case 7:day=31;break;
		case 8:day=31;break;
		case 9:day=30;break;
		case 10:day=31;break;
		case 11:day=30;break;
		case 12:day=31;break;
		default:break;
	}
	cout<<"days:" <<day;
 } 
