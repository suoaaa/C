/*补充下列程序的代码，使得程序的输出为：
1,2
a,b
11
12*/
#include <iostream>
using namespace std;
namespace nsA
{
    int x=1;int y=2;
    namespace nsB
    {
        char x='a';char y='b';
        int add_something(const int i)
            {return i+2;};
    };
    int add_something(const int i)
        {return i+1;};
}
int main()
{
    using namespace nsA;
    cout << x << "," << y << endl;
    cout << nsA::nsB::x << "," << nsA::nsB::y << endl;
    cout << add_something(10) << endl;
    cout << nsA::nsB::add_something(10) << endl;
    return 0;
}