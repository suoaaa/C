/*����һ������?n?���ҳ���?1?��?n?���������� Fizz Buzz ��ʾ��
�����ַ�������?answer���±�� 1 ��ʼ�����ؽ�������У�
answer[i] == "FizzBuzz"?���?i?ͬʱ��?3?��?5?�ı�����
answer[i] == "Fizz"?���?i?��?3?�ı�����
answer[i] == "Buzz"?���?i?��?5?�ı�����
answer[i] == i?�����ַ�����ʽ�������������ȫ�����㡣*/
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