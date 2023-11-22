/*给你一个整数?n?，找出从?1?到?n?各个整数的 Fizz Buzz 表示，
并用字符串数组?answer（下标从 1 开始）返回结果，其中：
answer[i] == "FizzBuzz"?如果?i?同时是?3?和?5?的倍数。
answer[i] == "Fizz"?如果?i?是?3?的倍数。
answer[i] == "Buzz"?如果?i?是?5?的倍数。
answer[i] == i?（以字符串形式）如果上述条件全不满足。*/
#include<iostream>
#include<vector>
#include<string>
using namespace std;
class Solution {
public:
    vector<string> fizzBuzz(int n) {
        vector<string> answer;
        for(int i=1;i<=n;i++){
            if((i)%15==0) answer.push_back("FizzBuzz");
            else if(!((i)%3))	answer.push_back("Fizz");
            else if(!((i)%5))	answer.push_back("Buzz");
            else {
                string s=to_string(i);
                answer.push_back(s);
            }
        }
        return answer;
    }
};
int main(){
    Solution s;
    s.fizzBuzz(4);
    return 0;
}