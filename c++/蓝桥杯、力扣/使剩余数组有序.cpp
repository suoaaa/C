/*����һ���������� arr?������ɾ��һ�������飨����Ϊ�գ���ʹ�� arr?��ʣ�µ�Ԫ���� �ǵݼ� �ġ�
һ��������ָ����ԭ������������һ�������С�
���㷵��������ĿҪ������������ĳ��ȡ�
*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
using namespace std;
class Solution {
public:
    vector<vector<int>> flag;
    vector<int> arr;
    Solution(){};
    int findLengthOfShortestSubarray(vector<int>& arr) {
        this->arr=arr;
        int size=arr.size();        
        int min=size;        
        flag=vector<vector<int>>(size);
        for(int i=0;i<size;i++){flag[i]=vector<int>(size,-1);flag[i][i]=1;}
        if(checkIsNotDecrease(0,size)||size==0||size==1) return 0;
        for(int i=0;i<size;i++){
            for(int j=i;j<size;j++){
                bool x=true,y=true,z=true;
                if(i!=0) x=checkIsNotDecrease(0,i-1);
                if(j!=size-1)   y=checkIsNotDecrease(j+1,size);
                z=checkIsNotDecrease(i,j);
                if(x&&y&&z) {min=(min<j-i+1?min:j-i+1);break;}
            }
        }return min;
    }
    bool checkIsNotDecrease(int index1,int index2){
        bool ret=false;
        switch (flag[index1][index2])
        {
        case 1:ret=true;
            break;
        case 0:break;
        default:{
            flag[index1][index2]=0;
            int i;
            for(i=index1;i<index2;i++){
                if(arr[1]<arr[i+1]) break;
            }
            if(i==index2) {
                ret=true;
                flag[index1][index2]=1;
            }
            }
        }
        return ret;
    }
    
};