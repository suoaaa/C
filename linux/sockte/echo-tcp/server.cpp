#include <arpa/inet.h>
#include <ctype.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#include <thread>
#include <signal.h>
using namespace std;

#define DEST_PORT 8000

void zombie_cleaning(pid_t pid);
void quit(int no,siginfo_t* info, void* context);
char *getIp(sockaddr_in addr);
void monitor();         //增加对信号量的监控，用户按下ctrl+z,c或者’/‘后程序退出避免僵尸进程
int init();             //初始化服务器，对端口进行监听

int main(int argc, char *argv[]) {
    monitor();
    int serverSocket = init();
    int a=0;
    
    while(true){
        // accept
        struct sockaddr_in client_addr;
        socklen_t addrlen = sizeof(client_addr);
        memset(&client_addr,'\0',addrlen);
        a = accept(serverSocket, (struct sockaddr *)&client_addr, &addrlen);
        pid_t pid = fork();
        if(pid<0){  perror("Failed to create the child process.:");   }
        if(pid==0){
            char *ip=getIp(client_addr);
            execl("./subprogram",ip,&a,NULL);
            
        }else{
            thread t(zombie_cleaning,pid);
            t.detach();
        }
    }
    return 0;
}

char *getIp(sockaddr_in addr){
    char buf[64];
    char *ip=(char *)malloc(sizeof(char)*64);
    memset(buf,'\0',sizeof(buf));
    memset(ip,'\0',sizeof(ip));
    inet_ntop(AF_INET, &addr.sin_addr.s_addr,buf,sizeof(buf));
    int port=ntohs(addr.sin_port);
    sprintf(ip,"%s/%d%c",buf,port,'\0');
    return ip;
}
void zombie_cleaning(pid_t pid){
    waitpid(pid,NULL,0);
    printf("pid:%d cleaned\n",pid);
}

void quit(int no,siginfo_t* info, void* context){
    printf("Pid:%d cleaned\n",getpid());
    exit(0);
}
void monitor(){
    struct sigaction act;
	act.sa_sigaction=quit;
	act.sa_flags=0;
    sigaction(SIGINT,&act,NULL);
    sigaction(SIGQUIT,&act,NULL);
}
int init(){
    //创建套接字，使用tcp连接
    int s = socket(AF_INET, SOCK_STREAM, 0);
    if (s == -1){perror("Failed to create the socket:");exit(0);}
    //初始化一个远程地址，方便连接服务器
    struct sockaddr_in server_addr;
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(DEST_PORT);
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    //对端口进行监听
    int c = bind (s, (const struct sockaddr *)&server_addr, sizeof(server_addr));
    if (c == -1) {perror("Listening socket failed:");exit(0);}
    listen(s, 128);
    printf("listening socket\n");
    return s;
}
