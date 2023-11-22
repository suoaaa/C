#include<stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <unordered_map>
using namespace std;

int main(){
unordered_map<int,int> Hashmap;

    pid_t pid=fork();
    unordered_map<int,int> *hash=&Hashmap;
    if(pid==0){
        sleep(2);
        printf("1=%x\n%d",hash,(*hash)[1]);
    }else{
        (*hash)[1]=1;
        printf("2=%x\n%d\n",hash,(*hash)[1]);
    }
    
}