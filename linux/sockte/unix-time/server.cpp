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

using namespace std;

#define DEST_PORT 13

void quit(int no,siginfo_t* info, void* context){
    printf("端口号：%d监听下线\n",getpid());
    exit(0);
}



int main(int argc, char *argv[]) {
    //增加对信号量的监控，用户按下ctrl+c或者’/‘后程序退出
    struct sigaction act;
	act.sa_sigaction=quit;
	act.sa_flags=0;
    sigaction(SIGINT,&act,NULL);
    sigaction(SIGQUIT,&act,NULL);

    printf("%d\n",getpid());
    int s = socket(AF_INET, SOCK_DGRAM, 0);
    struct sockaddr_in server_addr;
    socklen_t addrlen;

    if (s == -1) {
        write(STDOUT_FILENO,"创建套接字失败\n",22);
        return 0;
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(DEST_PORT);
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    
    if ((bind(s, (const struct sockaddr *)&server_addr, sizeof(server_addr))) == -1) {
        write(STDOUT_FILENO,"监听套接字失败\n",22);
        return 0;
    }

    listen(s, 128);
    char msg[128];
    int ret;
    time_t now;

    while(true){
        // accept
        struct sockaddr_in client_addr;
        struct sockaddr *addr;
        socklen_t clientAddrLen = sizeof(client_addr);
        bzero(&client_addr,sizeof(client_addr));


        memset(msg,'\0',128);
        ret = recvfrom(s,msg,128,0,(sockaddr *)&client_addr,&clientAddrLen);
        addr = (sockaddr *)&client_addr;
        if(ret <= 0){
            continue;
        }
       
        
        if(strcmp(msg,"I want to know time")==0){
           
                    time (&now);

                    now = htonl((unsigned long)now);

                    
                    //发送数据,接收返回值
                    ret=sendto(s, (char *)&now, sizeof(now), 0 ,addr,clientAddrLen);
                    if(ret <= 0){
                        perror("连接中断，发送信息失败");
                    }
now = ntohl((unsigned long)now);
                char ip[64];
                inet_ntop(AF_INET, &client_addr.sin_addr.s_addr,ip,sizeof(ip));
                int port=ntohs(client_addr.sin_port);
                printf("发送给客户端%s/%d时间:%s",ip,port,ctime(&now));

      
        }
        
    }
    return 0;
}
