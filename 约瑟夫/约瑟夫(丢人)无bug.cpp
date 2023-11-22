#include<stdio.h>
#include<stdlib.h>
struct person
{
	int num;
	struct person*p;
};
int* solve(int N, int M, int K)
{
		int i, count = 0, count_2 = 0;
		int* serial = (int*)malloc((N - K) * sizeof(int));
		if (serial == 0) return 0;
		struct person* np;
		struct person* head = (struct person*)malloc(sizeof(struct person));
		if (head == 0) return 0;
		np = head;
		head->num = 1;
		if (np == 0) return 0;
		for (i = 2; i <= N; i++)
		{
			struct person* mp = (struct person*)malloc(sizeof(struct person));
			if (mp == 0) return 0;
			np->p = mp;
			np = mp;
			mp->num = i;//这里给每个链表赋值初始编号
			if (i == N)
			{
				mp->p = head;
			}
		}//至此，链表建立完毕
		np = head;//重新赋值以便于开始计数
		if (M > 1)
		{
			while (1)
			{
				count += 1;
				if (count == M - 1)
				{
					count_2 += 1;
					serial[count_2 - 1] = np->p->num;
					np->p = np->p->p;
					count = 0;
				}
				np = np->p;
				if (count_2 == N - K)
					break;
			}
		}
		if (M == 1)
		{
			for (i = 0; i < N - K; i++)
			{
				serial[i] = i + 1;
			}
		}
		return serial;
}
