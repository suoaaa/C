#include<iostream>
#include<cstring>
#include<cstdlib>
#define maxn 1000
using namespace std;
int a[maxn], b[maxn];
void mul(char str1[], char str2[])
{
    int i = 0, j = 0;
    int len1 = strlen(str1);
    int len2 = strlen(str2);
    
    for(i = len1 - 1, j = 0; i >= 0; i--)
    a[j++] = str1[i] - '0';
    for(i = len2 - 1, j = 0; i >= 0; i--)
    b[j++] = str2[i] - '0';
    if((len1==1&&a[0]==0)||(len2==1&&b[0]==0))
    {
        cout<<'0';
    }
    else
    {int out[30]={0};
    for(i=0;i<len1;i++)
    {
        for(j=0;j<len2;j++)
        {
            out[i+j]+=a[i]*b[j];
            out[i+j+1]+=out[i+j]/10;
            out[i+j]=out[i+j]%10;
        }
    }
    i=len1+len2-1;
    while(out[i]==0)
    {
        i--;
    }
    for(;i>=0;i--)
    {
        cout<<out[i];
    }}

}
int main(void)
{
    char str1[maxn], str2[maxn];
    cin >> str1 >> str2;
    mul(str1, str2);
    return 0;
}
/*例如输入：999 999
输出：998001
输入：0 9999999999999999
输出：0
*/