#include<stdio.h>
#include<stdlib.h>
int main ()
{
	int n=0;
	int i=0;
	typedef struct student
	{
    char id[20];	int nn;	int bir;	float ta;	int c;	int w;
	}student;
	scanf("%d",&n);
	student s[4];
	do
	{	scanf("%s %d %d %f %d %d",&s[i].id,&s[i].nn,&s[i].bir,&s[i].ta,&s[i].c,&s[i].w);
		i++;	
	} 
	while(i<n);
	int ca=s[0].c,wa=s[0].w,cmax[2]={0},wmax[2]={0},cmin=0,wmin,m=0;
	for(i=1;i<n;i++)
	{
		if(s[cmax[0]].c<=s[i].c)
		{
			cmax[1]=cmax[0];
			cmax[0]=i;
		}
		if(s[cmin].c>=s[i].c)
		{
			cmin=i;
		}
		if(s[wmax[0]].w<=s[i].w)
		{
		wmax[1]=wmax[0];
		wmax[0]=i;
		}
		if(s[wmin].w>=s[i].w)
		{
			wmin=i;
		}
		ca=ca+s[i].c;
		wa=wa+s[i].w;
	}	
	printf("C_average:%d\nC_max:%d\n",ca/n,s[cmax[0]].c);
	if(s[cmax[1]].c<s[cmax[0]].c||n==1||cmax[0]==0){}
	else{printf("%s %d %d %0.2f %d %d\n",s[cmax[1]].id,s[cmax[1]].nn,s[cmax[1]].bir,s[cmax[1]].ta,s[cmax[1]].c,s[cmax[1]].w);}
	printf("%s %d %d %0.2f %d %d\nC_min:%d\nCalculus_average:%d\nCalculus_max:%d\n",s[cmax[0]].id,s[cmax[0]].nn,s[cmax[0]].bir,s[cmax[0]].ta,s[cmax[0]].c,s[cmax[0]].w,s[cmin].c,wa/n,s[wmax[0]].w);	

	if(s[wmax[1]].w<s[wmax[0]].w||n==1||wmax[0]==0){}
	else{printf("%s %d %d %0.2f %d %d\n",s[wmax[1]].id,s[wmax[1]].nn,s[wmax[1]].bir,s[wmax[1]].ta,s[wmax[1]].c,s[wmax[1]].w);}
	printf("%s %d %d %0.2f %d %d\nCalculus_min:%d\n",s[wmax[0]].id,s[wmax[0]].nn,s[wmax[0]].bir,s[wmax[0]].ta,s[wmax[0]].c,s[wmax[0]].w,s[wmin].w);
	return 0;
 }

