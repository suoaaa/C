#include <iostream>
#include <stdlib.h>
using namespace std;
typedef struct node
{
    int data;
    int a;
    node* next;
}node;
int pri(node* p, int n);
void sor(node* n);
void pt(node* n)
{
    while (n->next != NULL)
    {
        cout << n->data << ' ';
        n = n->next;
    }
    cout <<n->data << endl;
}
int main()
{
    char ch=' ';
    int b = 0;
    node* num = (node*)(malloc(sizeof(node)));
    if (num==NULL) return 0;
    node* p;
    p = num;
    int i = 0;
    for (i = 0; i < 10; i++)
    {
        cin >> p->data;
        if ((p->data) < 0)
        {
            p->a = 3;
            i= 10; 
            break;
        }
        else if ((p->data) % 2 == 1)
        {
            p->a = 1;
        }
        else p->a = 2;
        if (ch != '\n')
        {
            node* q = (node*)(malloc(sizeof(node)));
            
            if (q)
            {
                q->next = NULL;
                p->next = q;
                p = q;
                p->data = -1;
                p->a = 3;
                cin.get(ch);
            }
            continue;
        }
        else break;
    }
//    cout << endl;
    p = num;
    sor(num);
//    pt(num);
    p = num;
    pri(num, 1);
    p = num;
    pri(num, 2);
    free(num);
    return 0;
}
void sor(node* n)
{
    node* p;
    p = n;
    node* tem = n;
    node* q = NULL;
    int temd = 0;
    int tema = 0;
//    cout << "sor";
//    pt(n);
    while ((tem->next) != NULL)
    {
        while (p->next != NULL)
        {
            q = p->next;
            if ((p->data) > (q->data))
            {
                temd = p->data;
                tema = p->a;
                p->data = q->data;
                p->a = q->a;
                q->data = temd;
                q->a = tema;
            }
            p = q;
        }
        tem=tem->next;
        p = n;
    }
//    cout << "sour";
}
int pri(node* p, int n)
{
    int b = 1;
    if (p == NULL) return 0;
    else {
        while ((p->a) > 0 && p!=NULL && b == 1)
        {
        if (p->a == n && p->data >= 0)
        {
            cout << p->data << ' ';
        }
        if (p->next == NULL)
        {
            b = 0;
        }
        else p = p->next;
        }
    return 0;
    }
    
}
