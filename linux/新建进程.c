#include<stdio.h>
#include <sys/types.h>
#include <unistd.h>
int main(){
    pid_t pid=0;
   pid = fork();
   printf("%d",pid);
    return 0;
}