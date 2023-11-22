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
//47.109.46.251

int clientSocket=0;
int count=0;

void quit(int no,siginfo_t *info,void *context){
    if(clientSocket!=0)
        close(clientSocket);
    printf("客户端退出\n");
    exit(0);
}

void heart_check(){
    while(true){
        sleep(1);
        count++;
        if(count>5){
            printf("无法连接至服务器，程序退出\n");
            exit(0);
        }
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
    thread t(heart_check);
    int ret=0;
    time_t now;
    while(true){
        ret = sendto(clientSocket,"I want to know time",20,0,(struct sockaddr*)&dest_addr, sizeof(dest_addr));
        if(ret<=0){
            printf("服务器连接失败\n");
        }else{
            memset((char*)&now,'\0',sizeof(now));
            ret = read(clientSocket, (char*)&now, sizeof(now));  //接收数据（时间）
            if (ret > 0){
                count=0;
                now = ntohl((unsigned long)now);  //网络字节顺序转主机字节顺序
                printf("msg from %s : %s", DEST_IP,ctime(&now));  //打印接收到的数据（时间）
            }else{
                printf("接收服务端信息失败\n");
            }
        }
        sleep(1);
    }
    return 0;
}
