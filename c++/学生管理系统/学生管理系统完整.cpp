#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define cjname "E:\\个人\\个人编程\\学生管理系统\\icc实验板\\2021080909008-chengjibiao.txt"
typedef struct student
{
	int id;
	char name[50];
	float chinese;
	float math;
	float english;
	float sum;
}student;
void menu(void);//菜单 
FILE* open(void);//打开成绩文件 
void clear(void);//删除某学生数据 
void input(void); //录入
void search(void);//查询 
void sort(void);//排序 
void change(void);//修改 
void gai(void);//修改函数中的小函数； 用于定点修改 
void plus(void); //检测总成绩正确 
void PrintStudents(void);//打印 
int main()//1.清空，2.录入，3.查询，4排序，5修改，6信息订正，7，打印成绩表，0退出 
{
	printf("\n********此版本经过编译测试，若成绩文件位置有误，请更改宏定义'cjname'为相应位置及相应文件名********\n");
	printf("\n********此版本仅可读取带有总成绩的成绩文件，若无，请在文件每行末尾补0并按6进行加和*******\n");
	printf("\n********请保证文件内总成绩的无误性，若无法保证，请先按6将文件内的成绩信息进行加和********\n");
	printf("\n********提示，除显示学生信息及显示学生信息外，其他操作均会更改成绩文件中的内容******\n");
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
	printf("******                        学生成绩管理系统                       ******\n");
	printf("------------------------------------     ----------------------------------\n");
	printf("***************************************************************************\n");
	printf("******       1、清除学生信息          *        2、录入学生信息       ******\n");
	printf("***************************************************************************\n");
	printf("******       3、查询学生信息          *        4、排序从高到低       ******\n");
	printf("***************************************************************************\n");
	printf("******       5、修改学生信息          *        6、总分正确加和       ******\n");
	printf("***************************************************************************\n");
	printf("******       7、显示当前信息          *        0、退出操作系统       ******\n");
	printf("------------------------------------     ----------------------------------\n");	
}
FILE* open(){
	FILE *fp; 
	fp=fopen(cjname,"r");
	int n;
	student *st; 
	if(fp==NULL)
    {
    	printf("\n******文件打开失败,请检查文件位置及名称******\n现在请关闭程序，在检查后再次运行\n") ;
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
	int m[100],z=0,y=0;//m【】为需要删除的学生在stu结构数组中的代号； 
	//n为学生人数计数用 ,p判断是否成功 ，q为遇到错误选择下面进程 ,o为成功后操作 
	char *judge=(char*)malloc(sizeof(char)*4);
	char *name=(char*)malloc(sizeof(char*)*20);
	name="error"; 
	student stu[100];
	FILE *fp=open();
	if(fp==NULL){	exit(0);}
	else
	{
	    printf("已知需要删除学生学号（id）还是姓名（name），输入其中一个括号中的内容：\n");
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
                printf("请输入该同学学号（id）：");
                scanf("%d",&id); 
                for(i=0;i<n;i++)
                 {
                	if(stu[i].id==id)
                	{
            		    p=2;
            		    printf("学号\t姓名\t语文\t数学\t英语\t总分\t\n");
            	    	printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
						printf("是否为该学生，是请按1，否请按0\n");
						scanf("%d",&y);
						if(y==1) {m[z]=i;z++;						} 
			    	}
		    	}
	    	}
		     else
	    	{
		    	printf("请输入该同学姓名（name）：");
			    scanf("%s",name);
			    for(i=0;i<n;i++)
		    	{
				    if(strcmp(stu[i].name,name)==0)
				    {
				    	p=2;
				    	printf("学号\t姓名\t语文\t数学\t英语\t总分\t\n");
            	    	printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
						printf("是否为该学生，是请按1，否请按0\n");
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
				printf("删除失败，即将返回菜单\n");
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
		if(p==0) 	printf("输入错误，请按1重新进入查找界面，按2回到菜单，按其他退出程序\n");
		if(p==1) 	printf("查找失败，请按1重新进入查找页面，按2回到菜单，按其他退出程序\n");
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
		printf("请输入录入学生个数："); 
		scanf("%d",&n);
		stu=(student *)malloc(sizeof(student)*n); 
		for(int i=0;i<n;i++){
			printf("输入第%d个人的学号(id):\n",i+1);
			scanf("%d",&stu[i].id);
			printf("输入第%d个人的姓名(name):\n",i+1);
			scanf("%s",stu[i].name);
			printf("输入第%d个人的语文:\n",i+1);
			scanf("%f",&stu[i].chinese);
			printf("输入第%d个人的数学:\n",i+1);
			scanf("%f",&stu[i].math);
			printf("输入第%d个人的英语:\n",i+1);
			scanf("%f",&stu[i].english);
			stu[i].sum=stu[i].chinese+stu[i].math+stu[i].english; 
			fprintf(fp,"%d\t%s\t%10f\t%10f\t%10f\t%10f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
		}
		
		printf("保存成功,请按任意键继续\n");
		fclose(fp);
		system("pause");
	}
}
void search(void){
	int id,i,p=0,q=0,n=0;//n为学生人数计数用 ,p判断是否成功 ，q为遇到错误选择下面进程 
	char *judge=(char*)malloc(sizeof(char)*4);
	char *name=(char*)malloc(sizeof(char*)*20);
	student stu[100];
	FILE *fp=open();
	if(fp==NULL){	exit(0);}
	else
	{
	printf("已知查找学生学号（id）还是姓名（name），输入其中一个括号中的内容：\n");
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
            printf("请输入该同学学号（id）：");
            scanf("%d",&id); 
            for(i=0;i<n;i++)
            {
            	if(stu[i].id==id)
            	{
            		p=2;
            		printf("学号\t姓名\t语文\t数学\t英语\t总分\t\n");
            		printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum); 
				}
			}
		}
		else
		{
			printf("请输入该同学姓名（name）：");
			scanf("%s",name);
			for(i=0;i<n;i++)
			{
				if(strcmp(stu[i].name,name)==0)
				{
					p=2;
					printf("学号\t姓名\t语文\t数学\t英语\t总分\t\n");
            		printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum); 
				}
			 } 
		}
		fclose(fp);
	}
	if(p==1||p==0)
	{
		if(p==0) 	printf("输入错误，请按1重新进入查找界面，按2回到菜单，按其他退出程序\n");
		if(p==1) 	printf("查找失败，请按1重新进入查找页面，按2回到菜单，按其他退出程序\n");
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
	int n=0,i,j;//n学生人数计数用 ,i循环用； 
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
			printf("修改失败，即将返回菜单\n");
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
	printf("即将修改该同学的哪一科成绩？\n");	
	printf("按'1'为语文(chinese),按'2'为数学(math),按'3'为英语(english),按'4'跳过该学生并继续查找，按'0'重新进入查找");	
	scanf("%d",&o);	
	if (o==0) change(); 
	else
	{
		printf("请输入修改后的该科成绩");
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
	//n为学生人数计数用 ,p判断是否成功 ，q为遇到错误选择下面进程 ,o为成功后操作 
	char *judge=(char*)malloc(sizeof(char)*4);
	char *name=(char*)malloc(sizeof(char*)*20);
	name="error"; 
	student stu[100];
	FILE *fp=open();
	if(fp==NULL){	exit(0);}
	else
	{
		printf("*******为避免重名使修改出现错误，尽量使用学号查询修改*******\n");
	    printf("已知查找学生学号（id）还是姓名（name），输入其中一个括号中的内容：\n");
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
                printf("请输入该同学学号（id）：");
                scanf("%d",&id); 
                for(i=0;i<n;i++)
                 {
                	if(stu[i].id==id)
                	{
            		    p=2;
            		    printf("学号\t姓名\t语文\t数学\t英语\t总分\t\n");
            	    	printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
            		    gai(&stu[i]);
			    	}
		    	}
	    	}
		     else
	    	{
		    	printf("请输入该同学姓名（name）：");
			    scanf("%s",name);
			    for(i=0;i<n;i++)
		    	{
				    if(strcmp(stu[i].name,name)==0)
				    {
				    	p=2;
				    	printf("学号\t姓名\t语文\t数学\t英语\t总分\t\n");
            	    	printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,stu[i].sum);
						gai(&stu[i]); 
			    	}
			     } 
	    	}
	    	fclose(fp);
			FILE *fp2=fopen(cjname,"w");
			if(fp2==NULL)
			{
				printf("修改失败，即将返回菜单\n");
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
		if(p==0) 	printf("输入错误，请按1重新进入查找界面，按2回到菜单，按其他退出程序\n");
		if(p==1) 	printf("查找失败，请按1重新进入查找页面，按2回到菜单，按其他退出程序\n");
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
		printf("加和失败，即将返回菜单\n");
	}
	else{
	for(i=0;i<n-1;i++)
	{
		fprintf(fp2,"%d\t%s\t%10f\t%10f\t%10f\t%10f\n",stu[i].id,stu[i].name,stu[i].chinese,stu[i].math,stu[i].english,ssum[i]);
	}
	fclose(fp2);
	printf("总分已正确加和，即将打印成绩表·····\n\n");
	PrintStudents();}
}
void PrintStudents(void){
	student st[100];
	FILE *fp=open(); 
	int n=0;
	printf("学号\t姓名\t语文\t数学\t英语\t总分\t\n");
	while(!feof(fp)){
	fscanf(fp,"%d %s %f %f %f %f",&st[n].id,st[n].name,&st[n].chinese,&st[n].math,&st[n].english,&st[n].sum);
	n++;
	}
	for(int i=0;i<n-1;i++){
	printf("%d\t%s\t%.2f\t%.2f\t%.2f\t%.2f\n",st[i].id,st[i].name,st[i].chinese,st[i].math,st[i].english,st[i].sum); 
	}
    fclose(fp);
    printf("显示完成，");
    system("pause");
}
