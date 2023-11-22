/*������������ַ����hash����ʵ�ֵ绰���Ĺ�����¼������20������
�绰���еļ�¼�����������绰����͵�ַ�����������������hash���ؼ��ֱַ�Ϊ�����͵绰���룬
Hash�����Զ����������Ϊ����/�绰���벿���ַ����֮���17ȡģ��
��ɼ�¼�Ĳ��롢���ҡ���ʾ���ܡ�*/
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
	int n;	//��¼��hash�Ĺؼ���key
}Hash;
struct Hash* creatH(Hash *book)//�������뱾 
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
int getkeyname(char *name)//��ȡ���ֹؼ��� 
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
int getkeytel(char *tel)//��ȡ����ؼ��� 
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
int Hashfindname(Hash* namebook, char *name)//�������ֹؼ��� 
{
	int i = 0;
	int j = 0;
	int key = getkeyname(name);
	Hash* book=namebook->next;
	while(book->n!=key) {book=book->next;}
	if (book->data == NULL)
	{
		cout << "δ���ҵ�����" << endl; return -1;
	}
	else
	{
		node* out = book->data;
		while(out->nextname != NULL)
		{
			if (!strcmp(name, out->name))//���һ�� 
			{
				cout << out->name << ' ' << out->tel << ' ' << out->add << endl;
				return 0;
			}
			out = out->nextname;
		}//while������ 
		if (!strcmp(name, out->name))//���һ�� 
		{
			cout << out->name << ' ' << out->tel << ' ' << out->add << endl;
			return 0;
		}
		else return -1;
	}
	return 0;
}
int Hashfindtel(Hash *telbook, char *tel)//���Һ���ؼ��� 
{
	int i = 0;
	int j = 0;
	int key = getkeytel(tel);
	Hash* book=telbook->next;
	while(book->n!=key) {book=book->next;}
	if (book->data == NULL)
	{
		cout << "δ���ҵ�����" << endl; return -1;
	}
	else
	{
		node* out = book->data;
		while(out->nexttel != NULL)
		{
			if (!strcmp(tel, out->tel))//���һ�� 
			{
				cout << out->name << ' ' << out->tel << ' ' << out->add << endl;
				return 0;
			}
			out = out->nexttel;
		}//while������ 
		if (!strcmp(tel, out->tel))//���һ�� 
			{
				cout << out->tel << ' ' << out->tel << ' ' << out->add << endl;
				return 0;
			}
	}return 0;
}
void Hashinse(node *new_one, Hash *namebook, Hash *telbook)//������룬�������ֹؼ�����ɵĺ��뱾 
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
void nodeinse(Hash *namebook , Hash *telbook)//������룬�������ֹؼ�����ɵĺ��뱾 ������ϸ������� 
{
	node *new_one;
	scanf("%s %s %s", new_one->name, new_one->tel, new_one->add);
	Hashinse(new_one, namebook, telbook);
}
void Hashshowname(Hash *book)//��ӡ���ֹؼ��ֺ��뱾 
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
				cout <<"\t����:" <<out->name <<"\t�绰:"<<out->tel<<"\t��ַ:"<<out->add<<endl;
				n++;
				out = out->nextname;
			}
			while (out != NULL);
		}
		p=p->next;
	}
	if(n==0) cout<<"�绰��Ϊ��\n";
}
void Hashshowtel(Hash *book)//��ӡ����ؼ��ֵ绰�� 
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
				cout <<"\t����:" <<out->name <<"\t�绰:"<<out->tel<<"\t��ַ:"<<out->add<<endl;
				n++;
				out = out->nexttel;
			}
			while (out != NULL);
		}
		p=p->next;
	}
	if(n==0) cout<<"�绰��Ϊ��\n";
}
void mymenu()
{
	cout<<"��������Ž��в���"<<endl;
	cout<<"����0���˳�"<<endl;
	cout<<"����1������"<<endl;
	cout<<"����2������"<<endl;
	cout<<"����3����ʾ"<<endl;
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
			cout<<"����������:"<<endl;
			cin>>name;
			cout<<"������绰����:";
			cin>>tel;
			cout<<"�������ַ:";
			cin>>add;
			new_one=new node;
			strcpy(new_one->name,name);
			strcpy(new_one->add,add);
			strcpy(new_one->tel,tel);
			Hashinse(new_one, namebook, telbook);
			break;
		case 2:
			cout<<"����1����������"<<endl;
			cout<<"����2�����ҵ绰"<<endl;
			cin>>flag;
			cout<<"��������Ҫ��ѯ������/�绰:"<<endl;
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
			cout<<"����1����ӡ�������洢�ĵ绰��"<<endl;
			cout<<"����2����ӡ���绰�洢�ĵ绰��"<<endl;
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
		cout<<"��������Ž��в���"<<endl;
		cin>>menu;
	}
	return 0; 
}
