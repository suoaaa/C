/*����һ����Ԫ�������ݣ��������һ�����ɵ�֧��ݡ�*/
#include <iostream>
#include<vector>
#include<cstring>
using namespace std;
int main(){
    string tian[10]={"geng","xin","ren","gui","jia","yi","bing","ding","wu","xu"};
    string di[12]={"shen","you","xu","hai","zi","chou","yin","mao","chen","si","wu","wei"};
    int year=0;
    cin>>year;
    year%=60;
    cout<<tian[year%10]<<di[year%12];
    return 0;
}