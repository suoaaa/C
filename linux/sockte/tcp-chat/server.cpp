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

#define DEST_PORT 8002
int client_1,client_2;

char *getIp(sockaddr_in addr);
void chat(int s1,int s2,char* ip);
void quit(int no,siginfo_t* info, void* context){
    printf("服务器pid：%d下线\n",getpid());
    if(client_1!=0){
        write(client_1,"exit",sizeof("exit"));
        close(client_1);
    }
    if(client_2!=0){
        write(client_2,"exit",sizeof("exit"));
        close(client_2);
    }
    exit(0);
}

int main(int argc, char *argv[]) {
    struct sigaction act;
	act.sa_sigaction=quit;
	act.sa_flags=0;
    sigaction(SIGINT,&act,NULL);
    sigaction(SIGQUIT,&act,NULL);

    printf("进程pid为%d\n",getpid());
    int s = socket(AF_INET, SOCK_STREAM, 0);
    struct sockaddr_in server_addr;
    socklen_t addrlen = sizeof(server_addr);

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
    struct sockaddr_in client_addr_1,client_addr_2;
    bzero(&client_addr_1,addrlen);
    bzero(&client_addr_2,addrlen);

    //等待客户链接
    client_1 = accept(s, (struct sockaddr *)&client_addr_1, &addrlen);
    char *ip_1 = getIp(client_addr_1);
    client_2 = accept(s, (struct sockaddr *)&client_addr_2, &addrlen);
    char *ip_2 = getIp(client_addr_2);
    write(client_1,ip_2,strlen(ip_2));
    write(client_2,ip_1,strlen(ip_1));
    thread t1(chat,client_1,client_2,ip_1);
    thread t2(chat,client_2,client_1,ip_2);
    t1.join();
    t2.join();
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

void chat(int s1,int s2,char* ip){
    char buf[128];
    while(1){
        memset(buf,'\0',128);
        if(recv(s1, buf, 128,MSG_DONTWAIT)>0){ 
            if(send(s2,buf,strlen(buf),0)>0){ 
                if(!strcmp(buf,"exit")){
                    close(s1);
                    close(s2);
                    printf("客户端下线\n");
                    exit(0);
                }else{
                    printf("msg from %s : %s\n",ip,buf);
                }
            }
        }
    }
}