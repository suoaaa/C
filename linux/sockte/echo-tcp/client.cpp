#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <iostream>
#include <cstring>
#include <thread>
#include <signal.h>
using namespace std;
#define DEST_IP "172.27.173.124"
#define DEST_PORT 8000
//47.109.46.251

int clientSocket=0;

void quit(int no,siginfo_t *info,void *context){
    if(clientSocket!=0)
        close(clientSocket);
    printf("客户端退出\n");
    exit(0);
}

void heart_check(){
    sleep(5);
    char buf[128];
    int count=0,ret;
    while(1){
        memset(buf,'\0',128);
        ret = recv(clientSocket, buf, 128,0);
        if(string(buf).find("exit")!=string::npos) ret=-1;
        if(ret==0) count++;
        else if(ret>0) count=0;
        if(count>10||ret<0){
            printf("服务器下线或网络问题，客户端关闭\n");
            write(clientSocket,"exit\0",strlen("exit\0"));
            close(clientSocket);
            exit(0);
        }
        sleep(1);
    }
}

int main(){
    //增加对信号量的监控，用户按下ctrl+z,c或者’/‘后程序退出避免僵尸进程
    struct sigaction act;
	act.sa_sigaction=quit;
	act.sa_flags=0;
    sigaction(SIGINT,&act,NULL);
    sigaction(SIGQUIT,&act,NULL);

    printf("进程端口号为%d\n",getpid());
    //初始化一个远程地址，方便连接服务器
    struct sockaddr_in dest_addr; 
    bzero(&dest_addr,sizeof(dest_addr));
    dest_addr.sin_family = AF_INET; 
    dest_addr.sin_port = htons(DEST_PORT); 
    inet_pton(AF_INET, DEST_IP, &dest_addr.sin_addr.s_addr); 

    //创建套接字，使用tcp连接
    clientSocket=socket(AF_INET,SOCK_STREAM,0);
    if (clientSocket==-1){printf("套接字创建失败\n");return -1;}

    //连接服务器
    int c = connect(clientSocket, (struct sockaddr*)&dest_addr, sizeof(dest_addr));
    if(c!=0){printf("服务器连接出错\n");close(c);close(clientSocket);return 0;}

    //连接成功后，开始传输
    char buf[128];
    int ret,count=0;
    thread t(heart_check);
    t.detach();
    sleep(1);

    while(1){

        ret=0;
        //接收终端输入，最大字符128
        memset(buf,'\0',sizeof(buf));
	    scanf("%s",buf);

        //发送数据,接收返回值
        ret=send(clientSocket, buf, strlen(buf), 0);
        if(ret <= 0){
            printf("连接中断，发送信息失败\n");
            close(clientSocket);
            break;
        }

        //接收到exit指令后，停止连接
	    if(strcmp(buf,"exit") == 0) {
            close(clientSocket);
            break;
        }
    }
    return 0;
}
