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
    vector<int> arr;
    Solution(){};
    int findLengthOfShortestSubarray(vector<int>& arr) {
        this->arr=arr;
        int size=arr.size();        
        int min=size;        
        if(checkIsNotDecrease(0,size-1)||size==0||size==1) return 0;
        for(int i=0;i<size;i++){
            int j=i;
            for(;j<size;j++){
                bool x=true,y=true,z=true;
                if(i!=0) x=checkIsNotDecrease(0,i-1);
                if(j!=size-1)   y=checkIsNotDecrease(j+1,size-1);
                int r=i==0?0:i-1,l=j==size-1?size-1:j+1;
                z=(i==0?0:arr[i-1])<=(j==size-1?2000:arr[j+1]);
                if(x&&y&&z) {min=(min<j-i+1?min:j-i+1);break;}
            }
            cout<<min<<j-i+1<<' ';
        }return min;
    }
    bool checkIsNotDecrease(int index1,int index2){
        bool ret=false;
        if(index1==index2) ret=true;
        else{
            int i=0;
            for(i=index1;i<index2;i++){
                if(arr[i]<arr[i+1]) break;
            }if(i==index2) ret=true;
        }
        return ret;
    }
};