/*给你一个非负整数 num ，请你返回将它变成 0 所需要的步数。 
如果当前数字是偶数，你需要把它除以 2 ；否则，减去 1 。*/
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