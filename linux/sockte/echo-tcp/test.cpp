#include <iostream>
#include <thread>
#include <stdlib.h>
#include <string>
#include <unistd.h>

using namespace std;

void thread_one()
{
    puts("hello");
}

void thread_two(int num, string& str)
{
    cout << "num:" << num << ",name:" << str << endl;
}

int main(int argc, char* argv[])
{
    thread tt(thread_one);
    tt.join();
    string str = "luckin";
    thread yy(thread_two, 88, ref(str));   //这里要注意是以引用的方式调用参数
    yy.detach();
    int i=0;
    usleep(1);
    printf("%d",i++);
    // while(i<20){
    //     
    //     usleep(500);
    // }
return 0;
}