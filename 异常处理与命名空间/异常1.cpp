/*���г���Ĺ����ǣ����������±�ͳ�������������ȡ���±��Ӧ������Ԫ����Ϊ�������������������Ľ�������磬
���룺9 5
�����2

������������±�Խ����󣬸�����Ӧ����ʾ�����磬
���룺10 5
�����10 out of bound

���룺-1 5
�����-1 out of bound

������������±������󣬸�����Ӧ����ʾ�����磬
���룺9 0
�����divide by 0*/
#include <iostream>
using namespace std;
class A : public exception
{
public:
    int err;
    A(int i)    {err = i;}
};
class B : public exception
{
public:
    int err;
    B(int i)    {err=i;}
};
int main()
{
    int index, dividend, divisor, result;
    int array[10]={1,2,3,4,5,6,7,8,9,10};
    try
    {
        cin >> index >> divisor;
        if( index < 0 || index>9)
            throw A(index);  
        if(divisor==0)
            throw B(divisor);
        dividend = array[index];
        result = dividend / divisor;
        cout << result << endl;
    }
    catch(A a)
    {   cout << a.err << " out of bound" << endl;}
    catch(B b)
    {   cout << "divide by "<< b.err << endl;   }
    return 0;
}