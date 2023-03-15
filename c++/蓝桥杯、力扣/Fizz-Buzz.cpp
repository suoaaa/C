/*����һ������?n?���ҳ���?1?��?n?���������� Fizz Buzz ��ʾ��
�����ַ�������?answer���±�� 1 ��ʼ�����ؽ�������У�
answer[i] == "FizzBuzz"?���?i?ͬʱ��?3?��?5?�ı�����
answer[i] == "Fizz"?���?i?��?3?�ı�����
answer[i] == "Buzz"?���?i?��?5?�ı�����
answer[i] == i?�����ַ�����ʽ�������������ȫ�����㡣*/
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