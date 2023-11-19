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
using namespace std;

#define DEST_PORT 7008

void zombie_cleaning(pid_t pid){
    waitpid(pid,NULL,0);
    printf("端口号%d的客户端已断开连接\n",pid);

}

int main(int argc, char *argv[]) {
    printf("%d\n",getpid());
    int a,s = socket(AF_INET, SOCK_STREAM, 0);
    struct sockaddr_in server_addr;
    socklen_t addrlen;

    if (s == -1) {
        write(STDOUT_FILENO,"创建套接字失败\n",10);
        return 0;
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(DEST_PORT);
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    
    if ((bind(s, (const struct sockaddr *)&server_addr, sizeof(server_addr))) == -1) {
        write(STDOUT_FILENO,"监听套接字失败\n",10);
        return 0;
    }

    listen(s, 128);
    while(true){
        // accept
        struct sockaddr_in client_addr;
        socklen_t addrlen = sizeof(client_addr);
        
        a = accept(s, (struct sockaddr *)&client_addr, &addrlen);

        pid_t pid = fork();
        if(pid<0){  write(STDOUT_FILENO,"服务器出错，客户端连接失败",14);   }
        if(pid==0){

            close(s);
            char buf[128], ip[64],client[128];
            inet_ntop(AF_INET, &client_addr.sin_addr.s_addr,ip,sizeof(ip));
            int port=ntohs(client_addr.sin_port);
            sprintf(client,"客户端%s/%d已连接至服务器端口%d%c%c",ip,port,getpid(),'\n','\0');
            write(STDOUT_FILENO,client,strlen(client));
            sprintf(client,"客户端%s/%d%c",ip,port,'\0');

            int ret = 0;
            while (1) {
                memset(buf,'\0',sizeof(buf));
                ret = read(a, buf, sizeof(buf));
                if(ret<0||strcmp(buf,"exit")==0){
                    printf("%s连接中断\n",client);
                    close(a);
                    exit(0);
                }else if(ret > 0 ){
                    printf("%s发送:%s\n",client,buf);
                }
            } 
        }else{
            thread t(zombie_cleaning,pid);
            t.detach();
            usleep(1);
        }
    }
    return 0;
}
