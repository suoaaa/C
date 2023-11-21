#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <iostream>
#include <cstring>
#include <thread>
#include <signal.h>
#include <time.h>
using namespace std;

#define DEST_IP "172.27.173.124"
#define DEST_PORT 13
#define UNIXEPOCH 2208988800UL
//47.109.46.251

int clientSocket=0;
int count=0;

void quit(int no,siginfo_t *info,void *context){
    if(clientSocket!=0)
        close(clientSocket);
    printf("客户端退出\n");
    exit(0);
}

void heart_check(int s){
    int count =0;
    while(1){
        count++;
        write(clientSocket,"alive\0",strlen("alive\0"));
        if(count>10){
            printf("服务器下线或网络问题，客户端关闭\n");
            write(clientSocket,"exit\0",strlen("exit\0"));
            close(s);
            exit(0);
        }
        sleep(1);
    }
}

int main(){
    //增加对信号量的监控，用户按下ctrl+z,c或者’/‘后程序退出
    struct sigaction act;
	act.sa_sigaction=quit;
	act.sa_flags=0;
    sigaction(SIGINT,&act,NULL);
    sigaction(SIGQUIT,&act,NULL);

    printf("%d\n",getpid());
    //初始化一个远程地址，方便连接服务器
    struct sockaddr_in dest_addr; 
    bzero(&dest_addr,sizeof(dest_addr));
    dest_addr.sin_family = AF_INET; 
    dest_addr.sin_port = htons(DEST_PORT); 
    inet_pton(AF_INET, DEST_IP, &dest_addr.sin_addr.s_addr); 

    //创建套接字，使用UDP连接
    clientSocket=socket(AF_INET,SOCK_DGRAM,0);
    if (clientSocket==-1){printf("套接字创建失败\n");return -1;}



    //连接时间服务器
    int ret = sendto(clientSocket,"I want to know time",20,0,(struct sockaddr*)&dest_addr, sizeof(dest_addr));
    if(ret<=0){
        printf("服务器连接失败\n");
        exit(0);
    }
    time_t now;
    thread t(heart_check,clientSocket);
    t.detach();
    sleep(1);

    while(1){
        memset((char*)&now,'\0',sizeof(now));
        ret = read(clientSocket, (char*)&now, sizeof(now));  //接收数据（时间）
        if (ret > 0){
            now = ntohl((unsigned long)now);  //网络字节顺序转主机字节顺序
            now -= UNIXEPOCH;  //转换成UNIX时间
            printf("%s\n", ctime(&now));  //打印接收到的数据（时间）
        }
        sleep(1);
    }
    return 0;
}
