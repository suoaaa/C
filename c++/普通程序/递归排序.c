#include<stdio.h>
#include<string.h>
void sort(int *num,int head,int end)
{
    if(end-head>1)
    {
        int temp_little[20],temp_great[20];
        int mid=(end+head)/2;
        int i=head;int k=0;int j=0;
        int mid_num=num[mid];
        for(;i<mid;i++)
            if(num[i]<=mid_num)    {temp_little[k]=num[i];k++;}
                else {temp_great[j]=num[i];j++;}

        for(i++;i<=end;i++) 
            if(num[i]>=mid_num)    {temp_great[j]=num[i];j++;}
                else {temp_little[k]=num[i];k++;}
        i=head;
        int n=0;
        for(;n<k;i++,n++)   num[i]=temp_little[n];
        num[i]=mid_num;
        int mid_now=i;
        for(i+=1,n=0;n<j;i++,n++)   num[i]=temp_great[n];
        sort(num,head,mid_now);
        sort(num,mid_now+1,end);
    } else  if(num[head]>num[end])  {int temp=num[head];num[head]=num[end];num[end]=temp;}
};
int main()
{
    int num[20];
    char c;
    int i=0;
    double all=0;
    for(;i<20;i++)
    {
        scanf("%d",&num[i]);
        all+=num[i];
        c=getchar();
        printf("%d ",num[i]);
        if(c=='\n') {printf("\n");break;}
    }
    int end=i;
    printf("数列中最大值：%d\n",num[end]);
    printf("数列中最小值：%d\n",num[0]);
    printf("数列的平均值：%0.2f\n",all/(end+1));
    return 0;
}
