#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define cjname "E:\\����\\���˱��\\ѧ������ϵͳ\\iccʵ���\\2021080909008-chengjibiao.txt"
typedef struct student
{
	int id;
	char name[50];
	float chinese;
	float math;
	float english;
	float sum;
}student;
void menu(void);//�˵� 
FILE* open(void);//�򿪳ɼ��ļ� 
void clear(void);//ɾ��ĳѧ������ 
void input(void); //¼��
void search(void);//��ѯ 
void sort(void);//���� 
void change(void);//�޸� 
void gai(void);//�޸ĺ����е�С������ ���ڶ����޸� 
void plus(void); //����ܳɼ���ȷ 
void PrintStudents(void);//��ӡ 
int main()//1.��գ�2.¼�룬3.��ѯ��4����5�޸ģ�6��Ϣ������7����ӡ�ɼ���0�˳� 
{
	printf("\n********�˰汾����������ԣ����ɼ��ļ�λ����������ĺ궨��'cjname'Ϊ��Ӧλ�ü���Ӧ�ļ���********\n");
	printf("\n********�˰汾���ɶ�ȡ�����ܳɼ��ĳɼ��ļ������ޣ������ļ�ÿ��ĩβ��0����6���мӺ�*******\n");
	printf("\n********�뱣֤�ļ����ܳɼ��������ԣ����޷���֤�����Ȱ�6���ļ��ڵĳɼ���Ϣ���мӺ�********\n");
	printf("\n********��ʾ������ʾѧ����Ϣ����ʾѧ����Ϣ�⣬��������������ĳɼ��ļ��е�����******\n");
	int p=100;
	while(p)
	{
	menu();
	open();
	scanf("%d",&p);
	switch(p)
	{
	case 1:
	clear();break;	
	case 2:
	input();break;
	case 3:
	search();break;
	case 4:
	sort(); break;
	case 5:
	change();break;	
	case 6:
	plus();break; 
	case 7:
	PrintStudents(); break;
	case 0:
		exit(0);break;
    }
    system("cls");
    }
}
void menu(void){
	printf("\n\n");
	printf("******                        ѧ���ɼ�����ϵͳ                       ******\n");
	printf("------------------------------------     ----------------------------------\n");
	printf("***************************************************************************\n");
	printf("******       1�����ѧ����Ϣ          *        2��¼��ѧ����Ϣ       ******\n");
	printf("***************************************************************************\n");
	printf("******       3����ѯѧ����Ϣ          *        4������Ӹߵ���       ******\n");
	printf("***************************************************************************\n");
	printf("******       5���޸�ѧ����Ϣ          *        6���ܷ���ȷ�Ӻ�       ******\n");
	printf("***************************************************************************\n");
	printf("******       7����ʾ��ǰ��Ϣ          *        0���˳�����ϵͳ       ******\n");
	printf("------------------------------------     ----------------------------------\n");	
}
FILE* open(){
	FILE *fp; 
	fp=fopen(cjname,"r");
	int n;
	student *st; 
	if(fp==NULL)
    {
    	printf("\n******�ļ���ʧ��,�����ļ�λ�ü�����******\n������رճ����ڼ����ٴ�����\n") ;
		fclose(fp);
		system("pause"); 
		exit(0);
		return NULL;
		}else
    {
	    return fp;
    }
    }
void clear(void){
	int id,i,p=0,q=0,n=0;
	int m[100],z=0,y=0;//m����Ϊ��Ҫɾ����ѧ����stu�ṹ�����еĴ��ţ� 
	//nΪѧ������������ ,p�ж��Ƿ�ɹ� ��qΪ��������ѡ��������� ,oΪ�ɹ������ 
	char *judge=(char*)malloc(sizeof(char)*4);
	char *name=(char*)malloc(sizeof(char*)*20);
	name="error"; 
	student stu[100];
	FILE *fp=open();
	if(fp==NULL){	exit(0);}
	else
	{
	    printf("��֪��Ҫɾ��ѧ��ѧ�ţ�id������������name������������һ�������е����ݣ�\n");
    	scanf("%s",judge);
	    if(strcmp(judge,"id")!=0&&strcmp(judge,"name")!=0){	}
	    else
	    {
	    p=1;
	    while(!feof(fp)){
	         fscanf(fp,"%d %s %f %f %f %f",&stu[n].id,stu[n].name,&stu[n].chinese,&stu[n].math,&stu[n].english,&stu[n].sum);
       	     n++;
                }
             if(strcmp(judge,"id")==0)
             {
                printf("�������ͬѧѧ�ţ�id����");
                scanf("%d",&id); 
                for(i=0;i<n;i++)
                 {
                	if(stu[i].id==id)
                	{
            		    p=2;
            		    printf("ѧ��\t����\t����\t��ѧ\tӢ��\t�ܷ�\t\n");
            	    	printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
						printf("�Ƿ�Ϊ��ѧ�������밴1�����밴0\n");
						scanf("%d",&y);
						if(y==1) {m[z]=i;z++;						} 
			    	}
		    	}
	    	}
		     else
	    	{
		    	printf("�������ͬѧ������name����");
			    scanf("%s",name);
			    for(i=0;i<n;i++)
		    	{
				    if(strcmp(stu[i].name,name)==0)
				    {
				    	p=2;
				    	printf("ѧ��\t����\t����\t��ѧ\tӢ��\t�ܷ�\t\n");
            	    	printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
						printf("�Ƿ�Ϊ��ѧ�������밴1�����밴0\n");
						scanf("%d",&y);
						if(y==1) {m[z]=i;z++;						} 
			    	}
			     } 
	    	}
	    	y=z;
	    	fclose(fp);
			FILE *fp2=fopen(cjname,"w");
			if(fp2==NULL)
			{
				printf("ɾ��ʧ�ܣ��������ز˵�\n");
			}
				else{
				for(i=0;i<n-1;)
			{
				fprintf(fp2,"%d\t%s\t%10f\t%10f\t%f\t%10f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
				i++;
				for(z=0;z<y;){if(i==m[z]) i++;z++;	}
			}
				fclose(fp2);
        	}
	if(p==1||p==0)
	{
		if(p==0) 	printf("��������밴1���½�����ҽ��棬��2�ص��˵����������˳�����\n");
		if(p==1) 	printf("����ʧ�ܣ��밴1���½������ҳ�棬��2�ص��˵����������˳�����\n");
		p=scanf("%d",&q);
		if(p!=1) exit(0);
		switch (q)
		{
			case 1:search();break;
			case 2:break;
			default:exit(0);break;
		}
	}
    system("pause");
}
	}
}
void input(void){
	FILE *fp; 
	fp=fopen(cjname,"a");
	int n;
	student *stu;
	if(fp==NULL)	{	}
	else
	{
		printf("������¼��ѧ��������"); 
		scanf("%d",&n);
		stu=(student *)malloc(sizeof(student)*n); 
		for(int i=0;i<n;i++){
			printf("�����%d���˵�ѧ��(id):\n",i+1);
			scanf("%d",&stu[i].id);
			printf("�����%d���˵�����(name):\n",i+1);
			scanf("%s",stu[i].name);
			printf("�����%d���˵�����:\n",i+1);
			scanf("%f",&stu[i].chinese);
			printf("�����%d���˵���ѧ:\n",i+1);
			scanf("%f",&stu[i].math);
			printf("�����%d���˵�Ӣ��:\n",i+1);
			scanf("%f",&stu[i].english);
			stu[i].sum=stu[i].chinese+stu[i].math+stu[i].english; 
			fprintf(fp,"%d\t%s\t%10f\t%10f\t%10f\t%10f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
		}
		
		printf("����ɹ�,�밴���������\n");
		fclose(fp);
		system("pause");
	}
}
void search(void){
	int id,i,p=0,q=0,n=0;//nΪѧ������������ ,p�ж��Ƿ�ɹ� ��qΪ��������ѡ��������� 
	char *judge=(char*)malloc(sizeof(char)*4);
	char *name=(char*)malloc(sizeof(char*)*20);
	student stu[100];
	FILE *fp=open();
	if(fp==NULL){	exit(0);}
	else
	{
	printf("��֪����ѧ��ѧ�ţ�id������������name������������һ�������е����ݣ�\n");
	scanf("%s",judge);
	if(strcmp(judge,"id")!=0&&strcmp(judge,"name")!=0){	}
	else
	{
	p=1;
	while(!feof(fp)){
	    fscanf(fp,"%d %s %f %f %f %f",&stu[n].id,stu[n].name,&stu[n].chinese,&stu[n].math,&stu[n].english,&stu[n].sum);
       	n++;
           }
        if(strcmp(judge,"id")==0)
        {
            printf("�������ͬѧѧ�ţ�id����");
            scanf("%d",&id); 
            for(i=0;i<n;i++)
            {
            	if(stu[i].id==id)
            	{
            		p=2;
            		printf("ѧ��\t����\t����\t��ѧ\tӢ��\t�ܷ�\t\n");
            		printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum); 
				}
			}
		}
		else
		{
			printf("�������ͬѧ������name����");
			scanf("%s",name);
			for(i=0;i<n;i++)
			{
				if(strcmp(stu[i].name,name)==0)
				{
					p=2;
					printf("ѧ��\t����\t����\t��ѧ\tӢ��\t�ܷ�\t\n");
            		printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum); 
				}
			 } 
		}
		fclose(fp);
	}
	if(p==1||p==0)
	{
		if(p==0) 	printf("��������밴1���½�����ҽ��棬��2�ص��˵����������˳�����\n");
		if(p==1) 	printf("����ʧ�ܣ��밴1���½������ҳ�棬��2�ص��˵����������˳�����\n");
		p=scanf("%d",&q);
		if(p!=1) exit(0);
		switch (q)
		{
			case 1:search();break;
			case 2:break;
			default:exit(0);break;
		}
	}
}
    system("pause");
}
void sort(void){
	FILE* fp=open();
	int n=0,i,j;//nѧ������������ ,iѭ���ã� 
	student stu[100]; 
	student tem;
	while(!feof(fp))
	{
		fscanf(fp,"%d %s %f %f %f %f",&stu[n].id,stu[n].name,&stu[n].chinese,&stu[n].math,&stu[n].english,&stu[n].sum);
       	n++;
	}
	for(i=0;i<n-1;i++)
	{
		for(j=0;j<n-i;j++)
		{
			if(stu[j].sum<stu[j+1].sum) 
			{
				tem=stu[j];
				stu[j]=stu[j+1];
				stu[j+1]=tem;
			}
		}
	}
	FILE *fp2=fopen(cjname,"w");
		if(fp2==NULL)
		{
			printf("�޸�ʧ�ܣ��������ز˵�\n");
		}
			else{
			for(i=0;i<n-1;i++)
		{
			fprintf(fp2,"%d\t%s\t%10f\t%10f\t%10f\t%10f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
		}
			fclose(fp2);
       	}
	PrintStudents();
	system("pause"); 
}
void gai(student *stu){
	int o=0;
	float tem=0;
	printf("�����޸ĸ�ͬѧ����һ�Ƴɼ���\n");	
	printf("��'1'Ϊ����(chinese),��'2'Ϊ��ѧ(math),��'3'ΪӢ��(english),��'4'������ѧ�����������ң���'0'���½������");	
	scanf("%d",&o);	
	if (o==0) change(); 
	else
	{
		printf("�������޸ĺ�ĸÿƳɼ�");
		scanf("%f",&tem); 
        switch(o)
        {
        	case 1:stu->chinese=tem;break;
        	case 2:stu->math=tem;break;
        	case 3:stu->english=tem;break;
		}
		stu->sum=stu->english+stu->math+stu->chinese;
	}
	}
void change(void){
	int id,i,p=0,q=0,n=0;
	//nΪѧ������������ ,p�ж��Ƿ�ɹ� ��qΪ��������ѡ��������� ,oΪ�ɹ������ 
	char *judge=(char*)malloc(sizeof(char)*4);
	char *name=(char*)malloc(sizeof(char*)*20);
	name="error"; 
	student stu[100];
	FILE *fp=open();
	if(fp==NULL){	exit(0);}
	else
	{
		printf("*******Ϊ��������ʹ�޸ĳ��ִ��󣬾���ʹ��ѧ�Ų�ѯ�޸�*******\n");
	    printf("��֪����ѧ��ѧ�ţ�id������������name������������һ�������е����ݣ�\n");
    	scanf("%s",judge);
	    if(strcmp(judge,"id")!=0&&strcmp(judge,"name")!=0){	}
	    else
	    {
	    p=1;
	    while(!feof(fp)){
	         fscanf(fp,"%d %s %f %f %f %f",&stu[n].id,stu[n].name,&stu[n].chinese,&stu[n].math,&stu[n].english,&stu[n].sum);
       	     n++;
                }
             if(strcmp(judge,"id")==0)
             {
                printf("�������ͬѧѧ�ţ�id����");
                scanf("%d",&id); 
                for(i=0;i<n;i++)
                 {
                	if(stu[i].id==id)
                	{
            		    p=2;
            		    printf("ѧ��\t����\t����\t��ѧ\tӢ��\t�ܷ�\t\n");
            	    	printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
            		    gai(&stu[i]);
			    	}
		    	}
	    	}
		     else
	    	{
		    	printf("�������ͬѧ������name����");
			    scanf("%s",name);
			    for(i=0;i<n;i++)
		    	{
				    if(strcmp(stu[i].name,name)==0)
				    {
				    	p=2;
				    	printf("ѧ��\t����\t����\t��ѧ\tӢ��\t�ܷ�\t\n");
            	    	printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
						gai(&stu[i]); 
			    	}
			     } 
	    	}
	    	fclose(fp);
			FILE *fp2=fopen(cjname,"w");
			if(fp2==NULL)
			{
				printf("�޸�ʧ�ܣ��������ز˵�\n");
			}
				else{
				for(i=0;i<n-1;i++)
			{
				fprintf(fp2,"%d\t%s\t%10f\t%10f\t%f\t%10f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
			}
				fclose(fp2);
        	}
	if(p==1||p==0)
	{
		if(p==0) 	printf("��������밴1���½�����ҽ��棬��2�ص��˵����������˳�����\n");
		if(p==1) 	printf("����ʧ�ܣ��밴1���½������ҳ�棬��2�ص��˵����������˳�����\n");
		p=scanf("%d",&q);
		if(p!=1) exit(0);
		switch (q)
		{
			case 1:search();break;
			case 2:break;
			default:exit(0);break;
		}
	}
    system("pause");
}
	}
}
void plus(void){
	float ssum[100];
	int i=0,n=0;
	FILE *fp=open();
	student stu[100];
	while(!feof(fp))
	{
		fscanf(fp,"%d %s %f %f %f %f",&stu[n].id,stu[n].name,&stu[n].chinese,&stu[n].math,&stu[n].english,&stu[n].sum);
		ssum[n]=stu[n].chinese+stu[n].math+stu[n].english;
       	n++;
	}
	fclose(fp); 
	FILE *fp2=fopen(cjname,"w");
	if(fp2==NULL)
	{
		printf("�Ӻ�ʧ�ܣ��������ز˵�\n");
	}
	else{
	for(i=0;i<n-1;i++)
	{
		fprintf(fp2,"%d\t%s\t%10f\t%10f\t%10f\t%10f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,ssum[i]);
	}
	fclose(fp2);
	printf("�ܷ�����ȷ�Ӻͣ�������ӡ�ɼ�����������\n\n");
	PrintStudents();}
}
void PrintStudents(void){
	student st[100];
	FILE *fp=open(); 
	int n=0;
	printf("ѧ��\t����\t����\t��ѧ\tӢ��\t�ܷ�\t\n");
	while(!feof(fp)){
	fscanf(fp,"%d %s %f %f %f %f",&st[n].id,st[n].name,&st[n].chinese,&st[n].math,&st[n].english,&st[n].sum);
	n++;
	}
	for(int i=0;i<n-1;i++){
	printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",st[i].id,st[i].name,st[i].chinese,st[i].math,st[i].english,st[i].sum); 
	}
    fclose(fp);
    printf("��ʾ��ɣ�");
    system("pause");
}
