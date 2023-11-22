#include<stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
int main(){
    pid_t pid=fork();
    if(pid ==0){
        wait(pid);
        printf("Bye");
    }else if(pid>0){
        execl("./a",NULL);
    }
}