/*对于一个字母矩阵，我们称矩阵中的一个递增序列是指在矩阵中找到两个字母，
它们在同一行，同一列，或者在同一 45 度的斜线上，
这两个字母从左向右看、或者从上向下看是递增的。*/
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