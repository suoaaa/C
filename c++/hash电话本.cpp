#include<iostream>
#include<cstring>
using namespace std;
typedef struct node
{
	char name[10] ;
	char tel[11];
	char add[20];
	struct node* nextname ;
	struct node* nexttel ;
}node;
typedef struct Hash
{
	node* data;
	Hash* next;
	int n;
}Hash;
struct Hash* creatH(Hash *book)//创建号码本 
{
	int re = 0;
	Hash* q = new Hash;
	book->next = q;
	for (int i = 0; i < 17; i++)
	{
		struct Hash* p = new Hash;
		if (p)
		{

			p->next = NULL;
			q->next = p;
			q = p;
		}
		else
		{
			re = 1;
			printf("error\n");
		}
	}
	switch (re)
	{
	case 0:return book; break;
	default:return NULL; break;

	}
}
int getkeyname(char name[10])//获取名字关键字 
{
	int namekey = 0;
	for (int i = 0; i < 10; i++)
	{
		if (name[i] != '\0')
		{
			namekey = namekey + name[i];
		}
	}
	namekey = namekey % 17;
	return namekey;
}
int getkeytel(char tel[11])//获取号码关键字 
{
	int telkey = 0;
	for (int i = 0; i < 11; i++)
	{
		if (tel[i] != '\0')
		{
			telkey = telkey + tel[i] - 48;
		}
	}
	telkey = telkey % 17;
	return telkey;
}
int Hashfindname(Hash* namebook, char name[10])//查找名字关键字 
{
	int i = 0;
	int j = 0;
	int key = getkeyname(name);
	if ((namebook + key)->data == NULL)
	{
		cout << "未查找到此人" << endl; return -1;
	}
	else
	{
		node* out = (namebook + key)->data;
		for (; i < (namebook + key)->n; i++)
		{
			if (!strcmp(name, out->name))//如果一样 
			{
				cout << out->name << ' ' << out->tel << ' ' << out->add << endl;
				return 0;
			}
			if (out->nextname == NULL)
			{
				cout << "未查找到此人" << endl; return -1;
			}
			out = out->nextname;
		}//for的括号 
	}
	return 0;
}
int Hashfindtel(Hash *telbook, char tel[10])//查找号码关键字 
{
	int i = 0;
	int j = 0;
	int key = getkeytel(tel);
	if ((telbook + key)->data == NULL)
	{
		cout << "未查找到此人" << endl; return -1;
	}
	else
	{
		node* out = (telbook + key)->data;
		for (; i < (telbook + key)->n; i++)
		{
			if (!strcmp(tel, out->tel))//如果一样 
			{
				cout << out->tel << ' ' << out->tel << ' ' << out->add << endl;
				return 0;
			}
			if (out->nexttel == NULL)
			{
				cout << "未查找到此人" << endl; return -1;
			}
			out = out->nexttel;
		}//for的括号 
	}return 0;
}
void Hashinse(node new_one, Hash *namebook, Hash *telbook)//插入号码，插入两种关键字组成的号码本 
{
	node *out;
	int i = 0;
	int keyname = getkeyname(new_one.name), keytel = getkeytel(new_one.tel);
	if ((namebook + keyname)->data == NULL)
	{
		(namebook + keyname)->data = &new_one;
		(namebook + keyname)->n = 1;
	}
	else {
		out = (namebook + keyname)->data;
		do
		{
			out = out->nextname;
		} while (out->nextname != NULL);
			(namebook + keyname)->n++;
		out->nextname = new node;
		out->nextname = &new_one;
	}
	if ((telbook + keytel)->data == NULL)
	{
		(telbook + keytel)->data = &new_one;
		(telbook + keytel)->n = 1;
	}
	else {
		out = (telbook + keytel)->data;
		do
		{
			out = out->nexttel;
		} while (out->nexttel != NULL);
			(telbook + keytel)->n++;
		out->nexttel = new node;
		out->nexttel = &new_one;
	}
}
void nodeinse(Hash *namebook , Hash *telbook)//插入号码，插入两种关键字组成的号码本 （配合上个函数） 
{
	node new_one;
	scanf("%s %s %s", new_one.name, new_one.tel, new_one.add);
	Hashinse(new_one, namebook, telbook);
}
void Hashshowname(Hash *book)//打印名字关键字号码本 
{
	int i = 0, j = 0;
	node* out = NULL;
	for (i = 0; i < 17; i++)
	{
		out = (book + 1)->data;
		cout << i << ". ";
		if ((book + i)->n == 0)
		{
			cout << "NULL";
		}
		else
		{
			do
			{
				cout << out->name << ' ';
				out = out->nextname;
			} while (out != NULL);
		}
		cout << endl;
	}
}
void Hashshowtel(Hash *book)//打印号码关键字电话本 
{
	int i = 0, j = 0;
	node* out = NULL;
	for (i = 0; i < 17; i++)
	{
		out = (book + 1)->data;
		cout << i << ". ";
		if ((book + i)->n == 0)
		{
			cout << "NULL";
		}
		else
		{
			do
			{
				cout << out->tel << ' ';
				out = out->nexttel;
			} while (out != NULL);
		}
		cout << endl;
	}
}
int main() 
{ 	return 0; }