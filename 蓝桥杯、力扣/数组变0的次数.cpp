/*����һ���Ǹ����� num �����㷵�ؽ������ 0 ����Ҫ�Ĳ����� 
�����ǰ������ż��������Ҫ�������� 2 �����򣬼�ȥ 1 ��*/
#include<iostream>
using namespace std;
class Solution {
public:
    int numberOfSteps(int num) {
        int i=0;
        while(true)
        {
            if(num){
                if(num%2==0){
                i++;
                num/=2;
            	}
            	else{i++;num-=1;
                }   
        	}else return i;
        }
    }
};