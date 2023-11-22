/*创建基于链地址法的hash表，并实现电话簿的管理（记录不超过20个）。
电话簿中的记录包括姓名、电话号码和地址三个数据项，创建两个hash表，关键字分别为姓名和电话号码，
Hash函数自定，比如可以为姓名/电话号码部分字符求和之后对17取模，
完成记录的插入、查找、显示功能。*/
#include<iostream>
#include<cstring>
using namespace std;
typedef struct node
{
	char name[12] ;
	char tel[12];
	char add[20];
	struct node* nextname ;
	struct node* nexttel ;
}node;
typedef struct Hash
{
	node* data;
	Hash* next;
	int n;	//记录本hash的关键字key
}Hash;
struct Hash* creatH(Hash *book)//创建号码本 
{
	int re = 0;
	Hash* q =new Hash;
	book->data=NULL;book->n=-1;book->next=q;
	q=book;
	for (int i = 0; i < 17; i++)
	{
		struct Hash* p = new Hash;
		if (p)
		{
			p->data=NULL;
			p->next = NULL;
			p->n=i;
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
int getkeyname(char *name)//获取名字关键字 
{
	int namekey = 0;
	for (int i = 0; i < 7; i++)
	{
		if (name[i] != '\0')
		{
			namekey = namekey + name[i];
		}
	}
	namekey = namekey % 17;
	return namekey;
}
int getkeytel(char *tel)//获取号码关键字 
{
	int telkey = 0;
	for (int i = 0; i < 7; i++)
	{
		if (tel[i] != '\0')
		{
			telkey = telkey + tel[i] +3;
		}
	}
	telkey = telkey % 17;
	return telkey;
}
int Hashfindname(Hash* namebook, char *name)//查找名字关键字 
{
	int i = 0;
	int j = 0;
	int key = getkeyname(name);
	Hash* book=namebook->next;
	while(book->n!=key) {book=book->next;}
	if (book->data == NULL)
	{
		cout << "未查找到此人" << endl; return -1;
	}
	else
	{
		node* out = book->data;
		while(out->nextname != NULL)
		{
			if (!strcmp(name, out->name))//如果一样 
			{
				cout << out->name << ' ' << out->tel << ' ' << out->add << endl;
				return 0;
			}
			out = out->nextname;
		}//while的括号 
		if (!strcmp(name, out->name))//如果一样 
		{
			cout << out->name << ' ' << out->tel << ' ' << out->add << endl;
			return 0;
		}
		else return -1;
	}
	return 0;
}
int Hashfindtel(Hash *telbook, char *tel)//查找号码关键字 
{
	int i = 0;
	int j = 0;
	int key = getkeytel(tel);
	Hash* book=telbook->next;
	while(book->n!=key) {book=book->next;}
	if (book->data == NULL)
	{
		cout << "未查找到此人" << endl; return -1;
	}
	else
	{
		node* out = book->data;
		while(out->nexttel != NULL)
		{
			if (!strcmp(tel, out->tel))//如果一样 
			{
				cout << out->name << ' ' << out->tel << ' ' << out->add << endl;
				return 0;
			}
			out = out->nexttel;
		}//while的括号 
		if (!strcmp(tel, out->tel))//如果一样 
			{
				cout << out->tel << ' ' << out->tel << ' ' << out->add << endl;
				return 0;
			}
	}return 0;
}
void Hashinse(node *new_one, Hash *namebook, Hash *telbook)//插入号码，插入两种关键字组成的号码本 
{
	node *out=NULL;
	int keyname = getkeyname(new_one->name), keytel = getkeytel(new_one->tel);
	Hash *tbook=telbook;	Hash *nbook=namebook;
	while(tbook->n!=keytel) 	tbook=tbook->next;
	while(nbook->n!=keyname) 	nbook=nbook->next;
	if (nbook->data == NULL)
	{
		nbook->data = new_one;
		new_one->nextname=NULL;
	}
	else {
		out=nbook->data;
		while (out->nextname != NULL)
			out = out->nextname;
		out->nextname = new_one;
		out->nextname->nextname=NULL;
	}
	out=NULL;
	if (tbook->data == NULL)
	{
		tbook->data = new_one;
		new_one->nexttel=NULL;
	}
	else {
		out=tbook->data;
		while (out->nexttel != NULL)
			out = out->nexttel;
		out->nexttel = new_one;
		out->nexttel->nexttel=NULL;
	}
}
void nodeinse(Hash *namebook , Hash *telbook)//插入号码，插入两种关键字组成的号码本 （配合上个函数） 
{
	node *new_one;
	scanf("%s %s %s", new_one->name, new_one->tel, new_one->add);
	Hashinse(new_one, namebook, telbook);
}
void Hashshowname(Hash *book)//打印名字关键字号码本 
{
	int i = 0, j = 0,n=0;
	Hash* p = book->next;
	node *out=NULL;
	for (i = 0; i < 17; i++)
	{
		out =p->data;
		if (out!=NULL)
		{
			cout << i << ". ";
			do
			{
				cout <<"\t姓名:" <<out->name <<"\t电话:"<<out->tel<<"\t地址:"<<out->add<<endl;
				n++;
				out = out->nextname;
			}
			while (out != NULL);
		}
		p=p->next;
	}
	if(n==0) cout<<"电话本为空\n";
}
void Hashshowtel(Hash *book)//打印号码关键字电话本 
{
	int i = 0, j = 0,n=0;
	Hash *p = book->next;
	node *out=NULL;
	for (i = 0; i < 17; i++)
	{
		out =p->data;
		if (out!=NULL)
		{
			cout << i << ". ";
			do
			{
				cout <<"\t姓名:" <<out->name <<"\t电话:"<<out->tel<<"\t地址:"<<out->add<<endl;
				n++;
				out = out->nexttel;
			}
			while (out != NULL);
		}
		p=p->next;
	}
	if(n==0) cout<<"电话本为空\n";
}
void mymenu()
{
	cout<<"请输入代号进行操作"<<endl;
	cout<<"输入0：退出"<<endl;
	cout<<"输入1：插入"<<endl;
	cout<<"输入2：查找"<<endl;
	cout<<"输入3：显示"<<endl;
}
int main() 
{ 	int menu=0,flag=0;
	char name[12];
	char tel[12];
	char add[20];
	char find[12];
	node *new_one;
	Hash *namebook=new Hash;	creatH(namebook);
	Hash *telbook=new Hash;		creatH(telbook);
	node *one=new node;	strcpy(one->name,"wka");		strcpy(one->tel,"139284762");	strcpy(one->add,"wdadsadw");
	node *two=new node;	strcpy(two->name,"ydd");	strcpy(two->tel,"1595635672");	strcpy(two->add,"sfdfsewdw");
	node *three=new node;	strcpy(three->name,"vx");	strcpy(three->tel,"142546782");	strcpy(three->add,"sdgsdxfew");
	Hashinse(one, namebook, telbook);	Hashinse(two, namebook, telbook);	Hashinse(three, namebook, telbook);
	mymenu();
	cin>>menu;
	while(menu)
	{
		switch (menu)
		{
		case 1:
			cout<<"请输入姓名:"<<endl;
			cin>>name;
			cout<<"请输入电话号码:";
			cin>>tel;
			cout<<"请输入地址:";
			cin>>add;
			new_one=new node;
			strcpy(new_one->name,name);
			strcpy(new_one->add,add);
			strcpy(new_one->tel,tel);
			Hashinse(new_one, namebook, telbook);
			break;
		case 2:
			cout<<"输入1：查找姓名"<<endl;
			cout<<"输入2：查找电话"<<endl;
			cin>>flag;
			cout<<"请输入需要查询的姓名/电话:"<<endl;
			cin>>find;
			switch (flag)
			{
			case 1:Hashfindname(namebook, find);
				break;
			case 2:Hashfindtel(telbook, find);
				break;
			}
			break;
		case 3:
			cout<<"输入1：打印按姓名存储的电话本"<<endl;
			cout<<"输入2：打印按电话存储的电话本"<<endl;
			cin>>flag;
			switch (flag)
			{
			case 1:Hashshowname(namebook);
				break;
			case 2:Hashshowtel(telbook);
				break;
			}
			break;
		}
		mymenu();
		flag=0;menu=0;
		cout<<"请输入代号进行操作"<<endl;
		cin>>menu;
	}
	return 0; 
}
