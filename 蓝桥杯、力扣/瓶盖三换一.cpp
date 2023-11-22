
#include <iostream>
using namespace std;
int main(){
    int n(0),sum(0),code(0);
    cin<<n;
    sum+=n;
    while(n+code>=3){
        n=n+code;
        code=n%3;
        n/=3;
        sum+=n;
    }
    cout<<sum;
    return 0;
}