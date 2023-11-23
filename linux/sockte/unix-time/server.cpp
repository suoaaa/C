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
int s=0;
void quit(int no,siginfo_t* info, void* context){
    printf("端口号：%d的服务器下线\n",getpid());
    close(0);
    exit(0);
}

void send_time(int s,sockaddr_in client_addr,socklen_t addrlen){
    time_t now;
    time (&now);
    now = htonl((unsigned long)now);
    //发送数据,接收返回值

    if(sendto(s, (char *)&now, sizeof(now), 0 ,(sockaddr *)&client_addr,addrlen) <= 0){
        perror("连接中断，发送信息失败");
        return ;
    }
    now = ntohl((unsigned long)now);
    char ip[64];
    inet_ntop(AF_INET, &client_addr.sin_addr.s_addr,ip,sizeof(ip));
    int port=ntohs(client_addr.sin_port);
    printf("sent to %s/%d msg : %s",ip,port,ctime(&now));
}

int main(int argc, char *argv[]) {
    //增加对信号量的监控，用户按下ctrl+c或者’/‘后程序退出
    struct sigaction act;
	act.sa_sigaction=quit;
	act.sa_flags=0;
    sigaction(SIGINT,&act,NULL);
    sigaction(SIGQUIT,&act,NULL);

    printf("%d\n",getpid());
    s = socket(AF_INET, SOCK_DGRAM, 0);
    struct sockaddr_in server_addr,client_addr;
    socklen_t addrlen = sizeof(server_addr);
    char msg[128];


    if (s == -1) {
        write(STDOUT_FILENO,"创建套接字失败\n",22);
        return 0;
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(DEST_PORT);
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    
    if ((bind(s, (const struct sockaddr *)&server_addr, addrlen)) == -1) {
        write(STDOUT_FILENO,"监听套接字失败\n",22);
        return 0;
    }
    listen(s, 128);
    
    while(true){
        bzero(&client_addr,sizeof(client_addr));
        memset(msg,'\0',128);
        if(recvfrom(s,msg,128,0,(sockaddr *)&client_addr,&addrlen) <= 0){
            continue;
        }else if(strcmp(msg,"I want to know time")==0){
            thread t(send_time,s,client_addr,addrlen);
            t.detach();
        }
    }
    return 0;
}