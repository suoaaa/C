/*����һ������������ arr�����ܴ����ظ���Ԫ�أ������㷵�ؿ���?һ�ν��������������� arr[i] �� arr[j] ��λ�ã���
�õ��ġ����ֵ�������С�� arr ��������С�����޷���ô���������뷵��ԭ���顣

���룺arr = [3,2,1]
�����[3,1,2]
���ͣ�321>312�������ı�321С������*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<set>
#include<queue>
#include<algorithm>
using namespace std;
class Solution {
public:
    vector<int> prevPermOpt1(vector<int>& arr) {
        int n=arr.size();
        int i=n-2,j=n-1;
        while(i>=0&&arr[i]<=arr[i+1]) i--;
        if(i<0) return arr;
        while(arr[i]<=arr[j]) j--;
        while(arr[j]==arr[j-1]) j--;
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
        return arr;
    }
};