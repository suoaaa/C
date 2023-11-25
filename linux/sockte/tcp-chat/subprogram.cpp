#include <stdio.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#include <thread>
using namespace std;
void chat(int s1,int s2,char* ip);
int main(int argc,char *argv[]){

    if(argc!=4) {printf("Call failure\n");return 0;}
    char *ip_1=argv[0],*ip_2=argv[1];
    int fd_1=0,fd_2=0,ret = 0,count=0;
    char buf[128];
    for(int i=0;i<strlen(argv[2]);i++){
        fd_1=fd_1*10+argv[2][i];
    }
    for(int i=0;i<strlen(argv[3]);i++){
        fd_2=fd_2*10+argv[3][i];
    }
    printf("%s connected with %s\n",ip_1,ip_2);
    thread t1(chat,fd_1,fd_2,ip_1);
    thread t2(chat,fd_2,fd_1,ip_2);
    t1.join();
    t2.join();
}

void chat(int s1,int s2,char* ip){
    char buf[128];
    while(1){
        memset(buf,'\0',128);
        if(recv(s1, buf, 128,MSG_DONTWAIT)>0){ 
            if(send(s2,buf,strlen(buf),0)>0){ 
                if(!strcmp(buf,"exit")){
                    close(s1);
                    close(s2);
                    printf("Client offline\n");
                    exit(0);
                }else{
                    printf("msg from %s : %s\n",ip,buf);
                }
            }
        }
    }
}
