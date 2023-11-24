//g++ -o a .\client.cpp -lwsock32  -finput-charset=GBK -fexec-charset=GBK
#include <iostream>
#include <string.h>
#include <thread>
#include <stdlib.h> 
#include <windows.h>
#include <signal.h>

using namespace std;
#define DEST_IP "47.109.46.251"
#define DEST_PORT 8002

char ip[64];
int clientSocket=0;
char *CharstChange(char *src_str,int from,int to);
void heart_check();
void quit(int signal);
int myConnect();

int main(){
    signal (SIGABRT,quit);
    signal (SIGINT,quit);
    
	int clientsocket = myConnect();

    //连接成功后，开始传输
    char buf[128];
    int ret,count=0;
    thread t(heart_check);
    t.detach();
    
    while(1){
        ret=0;
        //接收终端输入，最大字符128
        memset(buf,'\0',128);
	    scanf("%s",buf);

        //发送数据,接收返回值
        ret=send(clientSocket, CharstChange(buf,0,65001), strlen(CharstChange(buf,0,65001)), 0);
        if(ret <= 0){
            perror("Failed to send message");
            //关闭套接字
            closesocket(clientSocket);
            //终止使用 DLL
            WSACleanup();

            break;
        }
        //接收到exit指令后，停止连接
	    if(strcmp(buf,"exit") == 0) {
            closesocket(clientSocket); 
            //终止使用 DLL
            WSACleanup();
            break;
        }
    }
    return 0;
}

char *CharstChange(char *src_str,int from,int to){
    int len = MultiByteToWideChar(from, 0, src_str, -1, NULL, 0);
	wchar_t* wstr = new wchar_t[len + 1];
	memset(wstr, '\0' , len + 1);
	MultiByteToWideChar(from, 0, src_str, -1, wstr, len);
	len = WideCharToMultiByte(to, 0, wstr, -1, NULL, 0, NULL, NULL);
	char* str = new char[len + 1];
	memset(str, '\0', len + 1);
	WideCharToMultiByte(to, 0, wstr, -1, str, len, NULL, NULL);
	if (wstr) delete[] wstr;
	return str;
}

void heart_check(){
    char buf[128];
    int count=0,ret;
    while(1){
        memset(buf,'\0',128);
        ret = recv(clientSocket, buf, sizeof(buf) ,0);
        if(ret == 0) count++;
        if(ret > 0) {
            count=0;
            if(string(buf).find("exit")!=string::npos) ret=-1;
            else if(strcmp(buf,"alive")){
                printf("msg from %s : %s\n",ip,CharstChange(buf,65001,0));
            }
        }
        if(count>30||ret<0){
            perror("Client shutdown");
            send(clientSocket,"exit",sizeof("exit"),0);
            closesocket(clientSocket);
            exit(0);
        }
        Sleep(1000);
    }
}

void quit(int signal){
    if(clientSocket!=0){
        send(clientSocket,"exit",sizeof("exit"),0);
        closesocket(clientSocket);
    }
    perror("Client shutdown");
    exit(0);
}

int  myConnect(){
    //初始化
    WSADATA data;
	if(WSAStartup(MAKEWORD(2, 2), &data)!=0){return 0;}
    //创建套接字，使用tcp连接
    clientSocket=socket(AF_INET,SOCK_STREAM,IPPROTO_TCP);
    if (clientSocket==-1){perror("Failed to create the socket");return -1;}
    //初始化一个远程地址，方便连接服务器
    struct sockaddr_in dest_addr; 
    memset(&dest_addr,'\0',sizeof(dest_addr));
    dest_addr.sin_family = AF_INET; 
    dest_addr.sin_port = htons(DEST_PORT); 
    dest_addr.sin_addr.S_un.S_addr=inet_addr(DEST_IP);

    //连接服务器
    int c = connect(clientSocket, (struct sockaddr*)&dest_addr, sizeof(dest_addr));
    if(c!=0){perror("Server connection error");closesocket(clientSocket);return 0;}
    else {printf("Wait for another client to connect to the server\n");}

    char ip[64];
    //等待对方连接后服务器发来的信息
    memset(ip,'\0',sizeof(ip));
    if(recv(clientSocket,ip,sizeof(ip),0)>0){
        printf("%s connected\n",ip);
    }else{
        perror("Connection fail");
        closesocket(clientSocket);
        exit(0);
    }
    return clientSocket;
}