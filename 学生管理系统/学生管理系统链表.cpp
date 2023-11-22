#include<stdio.h>
#include<stdlib.h>
typedef struct student
{
	int id;
	char name[50];
	float chinese;
	float math;
	float english;
	float sum;
	struct student *next; 
}student;
/*����������ַ���name��Ҫ�򿪵��ļ����ơ�
���������stu�Ƕ�����ѧ������ϸ��Ϣ�������ķ���ֵ��ѧ������.�κδ��󷵻�0*/
int ReadStuInfoFromFile(char *name, student **stu)
{
	int n=1,i=0;
	FILE *fp=fopen("name","r");
	if(fp!=NULL)
	{i=1;
		student *q=(student*)(malloc(sizeof(student*)));
		stu=&q;
	for(i=0;feof(fp)==0;i++)
	{
	    student *add=(student*)malloc(sizeof(student*));
	    if(add&&n!=0)
        {
        fscanf(fp,"%d %s %f %f %f",add->id,add->name,add->chinese,add->math,add->english);
	/*	fread(add->id,sizeof(stu),1,fp);
        fread(add->name,sizeof(stu),1,fp);
        fread(add->chinese,sizeof(stu),1,fp);
        fread(add->math,sizeof(stu),1,fp);
        n=fread(add->english,sizeof(stu),1,fp);//*/
        add->sum=add->chinese+add->math+add->english;
        add->next=NULL;
        q->next=add;
        add=q;
            //p->data=data[i];
          //  p->next=NULL;
         //   q->next=p;
           // q=p;
	    }
    }
    }
    return i;
}
/*���������stu��ȫ��ѧ����Ϣ��n��������
���������3��ƽ���ɼ�������������noPassStudent,����������m.�����ɹ�����0��ʧ�ܷ���-1*/
int NoPass(student stu[], int n, student **noPassStudent, int *m)
{
	int q=0,j=0,i=0;
	m=0;
	if(stu==NULL)
	{
		q=-1;
	}
	else
	{	q=0;	
	    noPassStudent=(student**)(malloc(sizeof(student*)));
	    student *p=(student*)(malloc(sizeof(student*)));
	    noPassStudent=&p;
		for(i=0;i<n;i++)
		{
		    student *add=(student*)(malloc(sizeof(student*)));
			if(stu->sum<180&&add)
			{	
			m++;
			add=stu;
			add->next=NULL;
			p=add;
			}		    
	    }
	}
	return q;
}
/*���������stu��ȫ��ѧ����Ϣ��n��������
���������3��ƽ���ɼ�����������PassStudent,��������m.�����ɹ�����0��ʧ�ܷ���-1*/
int Pass(student stu[], int n, student **PassStudent, int *m)
{
	int q=0,j=0,i=0;
	m=0;
	if(stu==NULL)
	{
		q=-1;
	}
	else
	{	q=0;	
	    PassStudent=(student**)(malloc(sizeof(student*)));
	    student *p=(student*)(malloc(sizeof(student*)));
	    PassStudent=&p;
		for(i=0;i<n;i++)
		{
		    student *add=(student*)(malloc(sizeof(student*)));
			if(stu->sum>180&&add)
			{	
			m++;
			add=stu;
			add->next=NULL;
			p=add;
			}		    
	    }
	}
	return q;
} 
/*���������stu��ȫ��ѧ����Ϣ��n��������
��������������ܷ�/ƽ���������Ľ��Ҳ�洢��stu��.�����ɹ�����0��ʧ�ܷ���-1*/
int SortStudents(student stu[], int n)
{
	int q=0,j=0,i=0;
	if(stu==NULL)
	{
		q=-1;
	}
	else
	{
		q=0;
	student *tem=(student*)malloc(sizeof(student*));
	student *p=(student*)malloc(sizeof(student*));
	
	for(i=0;i<n-1;i++)
	{
		for(j=0;j<n-i-1;j++)
		{
			if(p->sum<stu->sum)
			{
				*tem=stu[j];
				stu[j]=stu[j+1];
				stu[j+1]=tem;
			}
		}
	}
    }
    return q;
}
/*���������stu��ȫ��ѧ����Ϣ��n�������������ɹ�����0��ʧ�ܷ���-1��
����������ͼ�ϲ�����м�⣬ֻ�ǹ�ͬѧ�ǵ���ʹ�á�*/
int PrintStudents(student stu[], int n)
{
}
/*���������stu��ȫ��ѧ����Ϣ��n������,id�Ǵ����ҵ�ѧ�š�
���������rank���ڰ��ϵ�������stu�����ѧ������ϸ��Ϣ��
����ֵ�����ҳɹ�����0��ʧ�ܷ���-1*/
int SearchStudent(student stu[], int n, int id,int *rank, student *rstu)
{
}
int main()
{
	int n, rank, id,i,m;
	char name[]= "stuScores.txt";
	student *stu=NULL,*noPassStu=NULL,*PassStu=NULL,rstu;
	n = ReadStuInfoFromFile(name, &stu);
	if (n == 0) { printf("error"); return -1; }
	PrintStudents(stu, n);

	printf("\n no pass studnt--------\n");
	i = NoPass(stu, n, &noPassStu, &m);
	if(i==-1)printf("no pass error");
	else
	PrintStudents(noPassStu, m);
	
	printf("\n  passed studnt--------\n");
	i = Pass(stu, n, &noPassStu, &m);
	if (i == -1)printf(" pass error");
	else
		PrintStudents(noPassStu, m);

	printf("\n sort studnt--------\n");
	//i = NoPass(stu, n, &noPassStu, &m);
	i=SortStudents(stu, n);
	if (i == -1)printf("sort error");
	else
		PrintStudents(stu, n);

	printf("\n search id--------\n");
	//i = NoPass(stu, n, &noPassStu, &m);
	scanf("%d", &id);
	i = SearchStudent(stu, n, id, &rank, &rstu);
	if (i == -1)printf("search error");
	else
		PrintStudents(&rstu, 1);
       if(stu){free(stu);stu=NULL;}
       if(noPassStu){free(noPassStu);noPassStu=NULL;}
       if(PassStu){free(PassStu);PassStu=NULL;}
	system("pause");
	return 0;
}
