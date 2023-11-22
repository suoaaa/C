#include <iostream>
using namespace std;
template<class T>
T get_min(T* arr,int num)
{
    T min=arr[0];
    for(int i=1;i<num;i++)
        if(min>arr[i]) min=arr[i];
    return min;
}
int main()
{
    int arr_int[6] = { 1, 2, 3, 4, 5, 6 };
    double arr_double[6] = { 1.1, 2.2, 3.3, 4.4, 5.5, 6.6 };
    cout << get_min(arr_int, 6) << endl;
    cout << get_min(arr_double, 6) << endl;
    return 0;
}