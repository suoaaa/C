/*n个成绩，最大，最小，平均*/
#include <iostream>
using namespace std;
int main(){
    int n=0;
    cin>>n;
    int max(0),min(100),tem(0);
    double ave(0.0);
    for(int i=0;i<n;i++){
        cin>>tem;
        if(max<tem) max=tem;
        if(min>tem)	min=tem;
        ave+=tem;
    }
    printf("%d\n%d\n%0.2f",max,min,ave/n);
}