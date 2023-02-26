#include<stdio.h>
#include<stdlib.h>
typedef struct {
	int no;
	int age;
	int height;
}Person;

void sort (Person *array, int n)
{
if(n<=0||array==NULL)
{
	printf("error");
}
else{
	int i=0,j=0;
	Person temp;
    for(i=0;i<n-1;i++)
	{
	for(j=0;j<n-i-1;j++)
	{
		if(array[j].no > array[j+1].no)
		{
		temp=array[j];
		array[j]=array[j+1];
		array[j+1]=temp;
	    }
		else{
		if(array[j].no == array[j+1].no&&array[j].age>array[j+1].age)
		{
		temp=array[j];
		array[j]=array[j+1];
		array[j+1]=temp;
	    }
		else{
			if(array[j].age==array[j+1].age&&array[j].height>array[j+1].height)
			{
			temp=array[j];
			array[j]=array[j+1];
			array[j+1]=temp;
			}
		}
		}
		}
	}
}
}

