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

#define DEST_IP "47.109.46.251"
#define DEST_PORT 8001

int clientSocket=0;
int count=0;

void heart_check();     //心跳包机制，监控进程
void monitor();         //增加对信号量的监控，用户按下ctrl+z,c或者’/‘后程序退出避免僵尸进程
void quit(int no,siginfo_t *info,void *context);//退出调用，断开连接
sockaddr_in init();

int main(){
    monitor();
    clientSocket=socket(AF_INET,SOCK_DGRAM,0);
    if (clientSocket<0){perror("Failed to create the socket");exit(0);}
    sockaddr_in dest_addr = init();
    socklen_t addrlen=sizeof(dest_addr);
    thread t(heart_check);
    t.detach();
    time_t now;
    while(true){
        if(sendto(clientSocket,"I want to know time",strlen("I want to know time"),0,(sockaddr *)&dest_addr, addrlen)<=0){
            perror("Send failed request");
        }else{
            memset((char*)&now,'\0',sizeof(now)); 
            if(recvfrom(clientSocket,&now,sizeof(now),0,(sockaddr *)&dest_addr,&addrlen) > 0){
                count=0;
                now = ntohl((unsigned long)now);  
                printf("The time now is : %s", ctime(&now)); 
            }else{
                perror("Data receiving failure");
            }
        }
        sleep(1);
    }
    return 0;
}


void quit(int no,siginfo_t *info,void *context){
    if(clientSocket!=0)
        close(clientSocket);
    printf("Client shutdown\n");
    exit(0);
}

void monitor(){
    struct sigaction act;
	act.sa_sigaction=quit;
	act.sa_flags=0;
    sigaction(SIGINT,&act,NULL);
    sigaction(SIGQUIT,&act,NULL);
}

void heart_check(){
    while(true){
        sleep(1);
        count++;
        if(count>5){
            perror("Unable to connect to server");
            exit(0);
        }
    }
}

sockaddr_in init(){
    struct sockaddr_in dest_addr; 
    memset(&dest_addr,'\0',sizeof(sockaddr_in));
    dest_addr.sin_family = AF_INET; 
    dest_addr.sin_port = htons(DEST_PORT); 
    inet_pton(AF_INET, DEST_IP, &dest_addr.sin_addr.s_addr); 
    return dest_addr;
}