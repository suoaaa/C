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

#define DEST_PORT 13

void zombie_cleaning(pid_t pid){
    waitpid(pid,NULL,0);
    printf("端口号%d的监听已被清理\n",pid);
}

void heart_check(int s,char * ip,int *count){
    char buf[128];
    int ret=0;
    while(1){
        memset(buf,'\0',128);
        ret = recv(s, buf, 128,0);
        if(string(buf).find("exit")!=string::npos) ret=-1;
        if(ret==0) (*count)++;
        else if(ret>0) (*count)=0;
        if((*count)>10||ret<0){
            printf("服务器下线或网络问题，客户端关闭\n");
            write(s,"exit\0",strlen("exit\0"));
            close(s);
            exit(0);
        }
        sleep(1);
    }
}

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
    int a,s = socket(AF_INET, SOCK_STREAM, 0);
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
    while(true){
        // accept
        struct sockaddr_in client_addr;
        socklen_t addrlen = sizeof(client_addr);
        printf("1%d\n",getpid());


        memset(msg,'\0',128);
        ret = recvfrom(s,msg,128,0,NULL,NULL);
        if(ret <= 0){
            continue;
        }
        if(strcmp(msg,"I want to know time")==0){
            
        }
        pid_t pid = fork();
        if(pid<0){  write(STDOUT_FILENO,"服务器出错，客户端连接失败",39);   }
        if(pid==0){
            write(STDOUT_FILENO,"123\0",strlen("123\0"));
            printf("%d\n",getpid());
            close(s);
            char buf[128], ip[64],client[128];
            inet_ntop(AF_INET, &client_addr.sin_addr.s_addr,ip,sizeof(ip));
            int port=ntohs(client_addr.sin_port);
            sprintf(client,"客户端%s/%d已连接至服务器端口%d%c%c",ip,port,getpid(),'\n','\0');
            write(STDOUT_FILENO,client,strlen(client));
            sprintf(client,"客户端%s/%d%c",ip,port,'\0');

            time_t now;
            int ret , count=0;
            thread t(heart_check,a,client,&count);
            t.detach();
            sleep(1);

            while (1) {
                time (&now);
                ret=0;
                
                //发送数据,接收返回值
                ret=send(a, (char *)&now, sizeof(now), 0);
                if(ret <= 0){
                    printf("连接中断，发送信息失败\n");
                    close(a);
                    break;
                }
                sleep(1);
            } 
        }else{
            thread t(zombie_cleaning,pid);
            t.detach();
            usleep(1);
        }
    }
    return 0;
}
