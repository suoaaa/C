/*��һ�� n �� m �еķ���ͼ����һЩλ���е��ף�����һЩλ��Ϊ�ա�
��Ϊÿ����λ�ñ�һ����������ʾ��Χ�˸����ڵķ������ж��ٸ����ס�*/
#include <iostream>
#include<cstring>
#include<vector>
using namespace std;
int main(){
    int n,m;
    cin>>n>>m;
    int arr[n][m];
    memset(arr, 0, sizeof(arr));
    int temp=0;
    for(int i=0;i<n;i++){
        for(int j=0;j<m;j++){
            cin >> temp;
            if(temp==1){
                arr[i][j]=9;
                if(j+1<m)  if(arr[i][j+1]!=9) arr[i][j+1]++;
                if(i+1<n)  if(arr[i+1][j]!=9) arr[i+1][j]++;
                if(i+1<n&&j+1<m) if(arr[i+1][j+1]!=9) arr[i+1][j+1]++;
                if(j-1>=0)  if(arr[i][j-1]!=9) arr[i][j-1]++;
                if(i-1>=0)  if(arr[i-1][j]!=9) arr[i-1][j]++;
                if(i-1>=0&&j-1>=0) if(arr[i-1][j-1]!=9) arr[i-1][j-1]++;
                if(i+1<n&&j-1>=0) if(arr[i+1][j-1]!=9) arr[i+1][j-1]++;
                if(i-1>=0&&j+1<m) if(arr[i-1][j+1]!=9) arr[i-1][j+1]++;
            }
        }
    }
    for(int i=0;i<n;i++){
        for(int j=0;j<m;j++){
            cout << arr[i][j]<<' ';
        }
        cout<<endl;
    }
    return 0;
}