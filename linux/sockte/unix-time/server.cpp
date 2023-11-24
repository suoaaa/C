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
#include <signal.h>
#include <thread>

using namespace std;
#define DEST_PORT 8001

void quit(int no,siginfo_t* info, void* context);
void send_time(int s,sockaddr_in client_addr,socklen_t addrlen);
void monitor();         //增加对信号量的监控，用户按下ctrl+z,c或者’/‘后程序退出避免僵尸进程
int init();             //初始化服务器，对端口进行监听

int main(int argc, char *argv[]) {
    monitor();
    int s=init();
    char msg[128];
    struct sockaddr_in client_addr;
    socklen_t addrlen = sizeof(client_addr);
    while(true){
        memset(&client_addr,'\0',addrlen);
        memset(msg,'\0',128);
        if(recvfrom(s,msg,128,0,(sockaddr *)&client_addr,&addrlen) ==-1 ){
            continue;
        }else if(strcmp(msg,"I want to know time")==0){
            thread t(send_time,s,client_addr,addrlen);
            t.detach();
        }
    }
    return 0;
}

void send_time(int s,sockaddr_in client_addr,socklen_t addrlen){
    time_t now;
    time (&now);
    now = htonl((unsigned long)now);
    //发送数据,接收返回值

    if(sendto(s, (char *)&now, sizeof(now), 0 ,(sockaddr *)&client_addr,addrlen) <= 0){
        perror("Failed to send data");
        return ;
    }
    now = ntohl((unsigned long)now);
    char ip[64];
    inet_ntop(AF_INET, &client_addr.sin_addr.s_addr,ip,sizeof(ip));
    int port=ntohs(client_addr.sin_port);
    printf("sent to %s/%d time : %s",ip,port,ctime(&now));
    return ;
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
    int s = socket(AF_INET, SOCK_DGRAM, 0);
    if (s == -1){perror("Failed to create the socket:");exit(0);}
    //初始化一个远程地址，方便连接服务器
    struct sockaddr_in server_addr;
    socklen_t addrlen = sizeof(server_addr);
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(DEST_PORT);
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    //对端口进行监听
    int c = bind (s, (sockaddr *)&server_addr, sizeof(server_addr));
    if (c == -1) {perror("Listening socket failed:");exit(0);}
    listen(s, 128);
    printf("listening socket\n");
    return s;
}