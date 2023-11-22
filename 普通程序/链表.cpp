#include<stdio.h>
#include<stdlib.h>
typedef struct node
{
	int data;
	struct node* next;
}node;
int main(int data[], int n)
{
	int i=0;
	struct node *header =(node *)(malloc(sizeof (node)));
	struct node *q=header;
	for (i = 0; i < n; i++)
	{
		struct node *p=(struct node *)(malloc(sizeof(struct node)));
        if(p)
        {
            p->data=data[i];
            p->next=NULL;
            q->next=p;
            q=p;
	    }
        else
        {
            printf("error\n");
            return NULL;
        }

    }
    return (int)header;
}
