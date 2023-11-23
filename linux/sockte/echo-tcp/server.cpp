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

void zombie_cleaning(pid_t pid){
    waitpid(pid,NULL,0);
    printf("端口号%d的监听已被清理\n",pid);
}

void quit(int no,siginfo_t* info, void* context){
    printf("端口号：%d监听下线\n",getpid());
    exit(0);
}

void heart_check(int s,char * ip,int *count);
char *getIp(sockaddr_in addr);

int main(int argc, char *argv[]) {
    //增加对信号量的监控，用户按下ctrl+z,c或者’/‘后程序退出避免僵尸进程
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
    while(true){
        // accept
        struct sockaddr_in client_addr;
        socklen_t addrlen = sizeof(client_addr);
        
        a = accept(s, (struct sockaddr *)&client_addr, &addrlen);
        pid_t pid = fork();
        if(pid<0){  write(STDOUT_FILENO,"服务器出错，客户端连接失败",39);   }
        if(pid==0){

            close(s);
            char *ip=getIp(client_addr);
            char buf[128];
            printf("%s connected , pid :%d%c", ip , getpid() , '\n');
           
            int ret = 0,count=0;
            thread t(heart_check,a,ip,&count);
            t.detach();

            while (1) {
                memset(buf,'\0',sizeof(buf));
                ret = read(a, buf, sizeof(buf));
                if(ret<0||strcmp(buf,"exit")==0){
                    printf("%s连接中断\n",ip);
                    close(a);
                    exit(0);
                }else if(ret > 0 ){
                    printf("msg from %s : %s\n",ip,buf);
                    count=0;
                }
            }
        }else{
            thread t(zombie_cleaning,pid);
            t.detach();
        }
    }
    return 0;
}

void heart_check(int s,char *ip,int *count){
    while(1){
        (*count)++;
        write(s,"alive\0",strlen("alive\0"));
        if(*count>20){
            printf("%s长时间未发送信息，服务器端口%d监听下线\n",ip,getpid());
            write(s,"exit\0",strlen("exit\0"));
            close(s);
            exit(0);
        }
        sleep(1);
    }
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
