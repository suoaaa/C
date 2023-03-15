/*给你一个整数?n?，找出从?1?到?n?各个整数的 Fizz Buzz 表示，
并用字符串数组?answer（下标从 1 开始）返回结果，其中：
answer[i] == "FizzBuzz"?如果?i?同时是?3?和?5?的倍数。
answer[i] == "Fizz"?如果?i?是?3?的倍数。
answer[i] == "Buzz"?如果?i?是?5?的倍数。
answer[i] == i?（以字符串形式）如果上述条件全不满足。*/
#include<iostream>
#include<vector>
using namespace std;
class Solution {
public:
    vector<string> fizzBuzz(int n) {
        vector<string> answer(n);
        for(int i=0;i<n;i++){
            if(!((i+1)%3)&&!((i+1)%5)) answer[i]="FizzBuzz";
            else if(!((i+1)%3))	answer[i]="Fizz";
            else if(!((i+1)%5))	answer[i]="Buzz";
            else answer[i]=(string)("1"+i);
            cout<<i<<"   "<<answer[i]<<endl;
        }
        return answer;
    }
};
int main(){
    Solution s;
    s.fizzBuzz(3);
    return 0;
}