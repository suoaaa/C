#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <iostream>
#include <cstring>

#define DEST_IP "172.27.173.124"
#define DEST_PORT 7008
//47.109.46.251
int main(){

    //初始化一个远程地址，方便连接服务器
    struct sockaddr_in dest_addr; 
    bzero(&dest_addr,sizeof(dest_addr));
    dest_addr.sin_family = AF_INET; 
    dest_addr.sin_port = htons(DEST_PORT); 
    inet_pton(AF_INET, DEST_IP, &dest_addr.sin_addr.s_addr); 

    //创建套接字，使用tcp连接
    int clientSocket=socket(AF_INET,SOCK_STREAM,0);
    if (clientSocket==-1){printf("套接字创建失败\n");return -1;}

    //连接服务器
    int c = connect(clientSocket, (struct sockaddr*)&dest_addr, sizeof(dest_addr));
    if(c!=0){printf("服务器连接出错\n");close(c);close(clientSocket);return 0;}

    //连接成功后，开始传输
    char buf[128];
    char buff1[128];
    int ret=0;

    printf("%d\n",getpid());

    while(1){

        ret=0;
        //接收终端输入，最大字符128
        memset(buf,'\0',sizeof(buf));
	    scanf("%s",buf);

        //发送数据,接收返回值
        ret=send(clientSocket, buf, strlen(buf), 0);
        if(ret==-1||ret==0){
            printf("连接中断，发送信息失败\n");
            close(clientSocket);
            break;
        }

        //接收到exit指令后，停止连接
	    if(strcmp(buf,"exit") == 0) {
            close(clientSocket);
            break;
            }
        // memset(buf,'\0',sizeof(buf));
        // ret = recv(clientSocket, buf, 128, 0);
        // if(ret==0){
        //     printf("服务端断开连接，程序关闭\n");
        //     close(clientSocket);
        //     break;
        //     }
    }
    return 0;
}
