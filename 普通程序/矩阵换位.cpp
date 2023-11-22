#include<iostream>
using namespace std;
void tran(int array[5][3], int out_array[3][5])
{
    int i=0,j=0;
    if(out_array==NULL||array==NULL) cout<<"error";
    else{
    for(i=0;i<5;i++)
    {
        for(j=0;j<3;j++)
        {
            out_array[j][i]=array[i][j];
        }
    }
    }
}