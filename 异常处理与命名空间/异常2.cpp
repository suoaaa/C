/*下列程序的功能是：输入数组下标和除数，从数组中取出下标对应的数组元素作为被除数，输出除法运算的结果。例如，
输入：9 5
输出：2

捕获程序数组下标越界错误，给出相应的提示，例如，
输入：10 5
输出：10 out of bound

输入：-1 5
输出：-1 out of bound

捕获程序数组下标除零错误，给出相应的提示，例如，
输入：9 0
输出：divide by 0*/
#include <iostream>
using namespace std;

int main()
{
    int index, dividend, divisor, result;
    int array[10]={1,2,3,4,5,6,7,8,9,10};
    try
    {
        cin >> index >> divisor;
        if( index < 0 || index>9)
        throw index;  
        if(divisor==0)
        throw divisor;
        dividend = array[index];
        result = dividend / divisor;
        cout << result << endl;
    }
    catch(int temp)
    {
        if(temp==0)  {cout<<"divide by 0"<<endl;}
        else        {cout<<temp<<" out of bound"<<endl;}
    }
    return 0;
}