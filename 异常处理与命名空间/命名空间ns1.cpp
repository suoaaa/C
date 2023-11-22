/*补充下列程序的代码，使得程序的输出为：
1,2
a,b
11*/
#include <iostream>
using namespace std;
namespace ns1
{
    int x=1,y=2;
    namespace ns2
    {
        char x='a',y='b';
        int add_one(int a)
        {
            return a+1;
        }
    }
}
int main()
{
    using namespace ns1::ns2;
    cout << ns1::x << "," << ns1::y << endl;
    cout << x << "," << y << endl;
    cout << add_one(10) << endl;
    return 0;
}