#include<stdio.h>
#include<stdlib.h>
int main()
{
	int year;
		scanf("%d", &year);
		if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
		
			printf("%d is a leap year",year);
	    
		else 
			
			printf("%d is not a leap year", year);
			system("pause");
			return 0;
	        

}
