/*�������������� a �� b ������ a �� b �� �� ���ӵ���Ŀ
��� x ����ͬʱ���� a �� b ������Ϊ x �� a �� b ��һ�� ������ ��*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<vector>
#include<queue>
#include<algorithm>
using namespace std;
class Solution {
public:
    int commonFactors(int a, int b) {
        int num=0;
        int m=min(a,b);
        for(int i=1;i<=m;i++){
            if(a%i==0&&b%i==0) num++;
        }
        return num;
    }
};