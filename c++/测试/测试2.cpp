/*����һ����ĸ�������ǳƾ����е�һ������������ָ�ھ������ҵ�������ĸ��
������ͬһ�У�ͬһ�У�������ͬһ 45 �ȵ�б���ϣ�
��������ĸ�������ҿ������ߴ������¿��ǵ����ġ�*/
#include <iostream>
#include<cstring>
using namespace std;
int main(){
    string arr[3]={"LANNA", "QIAOA","AAAAA"};
    int sum=0;
    for(int i=0;i<2;i++){
        for(int j=0;j<4;j++){
            if(arr[i][j]<arr[i][j+1])   sum++;
            if(arr[i][j]<arr[i+1][j])   sum++;
            if(arr[i][j]<arr[i+1][j+1]) sum++;
        }
    }
    cout<<sum;
    return 0;
}