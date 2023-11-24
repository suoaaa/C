#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <iostream>
#include <cstring>
#include <thread>
#include <signal.h>

using namespace std;
#define DEST_IP "47.109.46.251"
#define DEST_PORT 8000

int clientSocket=0;

int  myConnect();       //连接服务器，返回socket的fd文件描述符
void heart_check();     //心跳包机制，监控进程
void monitor();         //增加对信号量的监控，用户按下ctrl+z,c或者’/‘后程序退出避免僵尸进程
void quit(int no,siginfo_t *info,void *context);//退出调用，断开连接
int main(){
    monitor();          //监控
    clientSocket=myConnect();//连接服务器
    thread t(heart_check);//创建线程，检测服务器存活状态
    t.detach();

    char buf[128];
    int ret;
    while(1){   
        //接收终端输入，最大字符128
        memset(buf,'\0',sizeof(buf));
	    scanf("%s",buf);
        //发送数据,接收返回值
        ret=0;
        ret=send(clientSocket, buf, strlen(buf), 0);
        if(ret <= 0){
            perror("Fail to send msg:");
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

void heart_check(){
    char buf[128];
    int count=0,ret;
    while(1){
        memset(buf,'\0',128);
        ret = recv(clientSocket, buf, 128,0);
        if(string(buf).find("exit")!=string::npos) ret=-1;
        if(ret==0) count++;
        else if(ret>0) count=0;
        if(count>15||ret<0){
            perror("Client shutdown:");
            write(clientSocket,"exit\0",strlen("exit\0"));
            close(clientSocket);
            exit(0);
        }
        sleep(1);
    }
}

void quit(int no,siginfo_t *info,void *context){
    if(clientSocket!=0)
        close(clientSocket);
    perror("Client shutdown:");
    exit(0);
}

void monitor(){
    struct sigaction act;
	act.sa_sigaction=quit;
	act.sa_flags=0;
    sigaction(SIGINT,&act,NULL);
    sigaction(SIGQUIT,&act,NULL);
}

int  myConnect(){
    //创建套接字，使用tcp连接
    int s=socket(AF_INET,SOCK_STREAM,0);
    if (s==-1){perror("Failed to create the socket:");exit(0);}
    //初始化一个远程地址，方便连接服务器
    struct sockaddr_in dest_addr; 
    memset(&dest_addr,'\0',sizeof(dest_addr));
    dest_addr.sin_family = AF_INET; 
    dest_addr.sin_port = htons(DEST_PORT); 
    inet_pton(AF_INET, DEST_IP, &dest_addr.sin_addr.s_addr); 
    
    
    //连接服务器
    int c = connect(s, (struct sockaddr*)&dest_addr, sizeof(dest_addr));
    if(c!=0){perror("Server connection error:");close(s);exit(0);}
    else{printf("Connect success\n");}
    return s;
}