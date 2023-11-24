#include <stdio.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#include <thread>
using namespace std;
void heart_check(int s,char *ip,int *count);

int main(int argc,char *argv[]){

    if(argc!=2) {printf("Call failure\n");return 0;}

    char *ip=argv[0];
    int fd=0,ret = 0,count=0;
    char buf[128];
    for(int i=0;i<strlen(argv[1]);i++){
        fd=fd*10+argv[1][i]-'0';
    }
    printf("%s connected \n", ip);  

    thread t(heart_check,fd,ip,&count);
    t.detach();

    while (1) {
        memset(buf,'\0',sizeof(buf));
        ret = read(fd, buf, sizeof(buf));
        if(ret<0||strcmp(buf,"exit")==0){
            printf("%s disconnected:",ip);
            perror("");
            close(fd);
            exit(0);
        }else if(ret > 0 ){
            printf("msg from %s : %s\n",ip,buf);
            count=0;
        }
    }
}

void heart_check(int s,char *ip,int *count){
    while(1){
        (*count)++;
        write(s,"alive\0",strlen("alive\0"));
        if(*count>20){
            printf("%s no message was sent for a long time, disconnected\n",ip);
            write(s,"exit\0",strlen("exit\0"));
            close(s);
            exit(0);
        }
        sleep(1);
    }
}
