#include<stdio.h>
#include<iostream>
using namespace std;
int* bubble_sort(int n);
int main()
{
	int n;
	cin >> n;
	int* a = bubble_sort(n);
	for (int i = 0; i < n; i++)
	{
		cout << a[i];
		if (i < n - 1)
			cout << " ";
	}
	cout << endl;
	return 0;
}
int* bubble_sort(int n)
{
	int* p = new int [n];
	int i, j;
	int tem;
	for (i = 0; i < n; i++)
	{
		cin >> p[i];
	}
	for (i = 0; i < n; i++)
	{
		for (j = 0; j < n - i - 1; j++)
		{
			if (p[j] > p[j + 1])
			{
				tem = p[j];
				p[j] = p[j + 1];
				p[j + 1] = tem;
			}
		}
	}
	return p;
	delete[]p;
}
