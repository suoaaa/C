/*编写一个模板函数get_sum，接收两个参数：数组和数组的长度
返回该数组所有元素的和。部分代码已给出，请将代码补充完整。*/
#include <iostream>
using namespace std;
template <class T>
T get_sum(T* a,int m)
{
    T sum=0;
    for(int i=0;i<m;i++)
    {
        sum+=a[i];
    }
    return sum;
}
int main()
{
    int arr_int[6] = { 1, 2, 3, 4, 5, 6 };
    double arr_double[6] = { 1.1, 2.2, 3.3, 4.4, 5.5, 6.6 };
    cout << get_sum(arr_int, 6) << endl;
    cout << get_sum(arr_double, 6) << endl;
    return 0;
}


