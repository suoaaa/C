#include<iostream>
using namespace std;
int howMany(int money,int m,int cockMoney,int henMoney,int chickenMoney,int *cockNum,int *henNum,int *chickenNum)
{
    int flag=0;
    if(money<=0||m<=0||cockMoney<=0||henMoney<=0||chickenMoney<=0||cockNum==NULL||henNum==NULL||chickenNum==NULL)
    {
        return -1;
    }
    else{
        int flag=0;
        int o=0,p=0,q=0;
    	int tem=0;
    	while(o*cockMoney<=money)
    	{
            p=0;
        	while((p*henMoney+o*cockMoney)<=money)
        	{
                tem=money-p*henMoney-o*cockMoney;
                q=tem/chickenMoney;
                if(tem==q*chickenMoney&&o+p+q==m) 
                {
                    flag=1;
                    *chickenNum=q;
                    *cockNum=o;
                    *henNum=p;
                    return flag;
                }
                p++;
       		}
            o++;
   		}
        return flag;
    }
} 
int main ()
{
    int money=100,m=12,cockMoney=10,henMoney=5,chickenMoney=5;
    int* cockNum=new int ();
    int *chickenNum=new int ();
    int *henNum=new int ();
    //cin>>money>>m>>cockMoney>>henMoney>>chickenMoney;
    int flag=howMany(money,m,cockMoney,henMoney,chickenMoney,cockNum,henNum,chickenNum);
    cout<<flag<<' '<<*cockNum<<' '<<*henNum<<' '<<*chickenNum;
    return 0;
}
/*函数功能：根据输入的总的价钱money，
总的鸡的数量m，每种鸡的价格，
求满足要求的各种鸡的数量。
如果函数有解，只需要求出其中一个解即可。 
输入参数:
int money: 总共花的钱数
int m:总的鸡的数量 
int cockMoney:1只公鸡的价格
int henMoney:1只母鸡的价格
int chickenMoney:一只小鸡的价格
 输出参数：
 int *cockNum:公鸡数量
 int *henNum:母鸡数量
 int *chickenNum:小鸡数量
 函数返回值：有解返回1，无解返回0，输入参数不合法，返回-1 */