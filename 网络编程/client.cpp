#include <unistd.h>
#include <winsock2.h>  
#include <iostream>
#include <string.h>
#include <thread>

using namespace std;
#define DEST_IP "47.109.46.251"
#define DEST_PORT 8002

char ip[64];
int clientSocket=0;
string GbkToUtf8(char *src_str);
void heart_check(){
   
    char buf[128];
    int count=0,ret;
    while(1){
         printf("alive\n");
        memset(buf,'\0',128);
        ret = recv(clientSocket, buf, sizeof(buf) ,0);
        if(ret == 0) count++;
        if(ret > 0) {
            count=0;
            if(string(buf).find("exit")!=string::npos) ret=-1;
            else if(strcmp(buf,"alive")){
                printf("msg from %s ：%s\n",ip,buf);
            }
        }
        if(count<-10||ret<0){
            printf("服务器下线或网络问题，客户端关闭\n");
            write(clientSocket,"exit",sizeof("exit"));
            close(clientSocket);
            exit(0);
        }
        sleep(1);
    }
}

int main(){
    //增加对信号量的监控，用户按下ctrl+z,c或者’/‘后程序退出并通知服务器避免僵尸进程

    printf("进程pid为%d\n",getpid());
	WSADATA data;
	if(WSAStartup(MAKEWORD(2, 2), &data)!=0)
	{
		return 0;
	}
    int e=0;
scanf("%s",&e);
printf("%s\n",e);
    //初始化一个远程地址，方便连接服务器
    struct sockaddr_in dest_addr; 
    memset(&dest_addr,'\0',sizeof(dest_addr));
    dest_addr.sin_family = AF_INET; 
    dest_addr.sin_port = htons(DEST_PORT); 
    dest_addr.sin_addr.S_un.S_addr=inet_addr(DEST_IP);
    // inet_pton(AF_INET, DEST_IP, &dest_addr.sin_addr.s_addr); 

    //创建套接字，使用tcp连接
    clientSocket=socket(AF_INET,SOCK_STREAM,IPPROTO_TCP);
    if (clientSocket==-1){printf("套接字创建失败\n");return -1;}

    //连接服务器
    int c = connect(clientSocket, (struct sockaddr*)&dest_addr, sizeof(dest_addr));
    if(c!=0){printf("服务器连接出错\n");close(clientSocket);return 0;}
    else {printf("等待另一客户端连接至服务器\n");}

    //连接成功后，开始传输
    const char *buf;
    char *bu2;
    int ret,count=0;
    memset(ip,'\0',sizeof(ip));
    if(recv(clientSocket,ip,sizeof(ip),0)>0){
        printf("%s已上线\n",ip);
    }else{
        printf("网络问题连接失败\n");
        closesocket(clientSocket);
        //终止使用 DLL
        WSACleanup();
        exit(0);
    }

    thread t(heart_check);
    t.join();
int i=0;
 printf("%d\n",i++);
    while(1){

        ret=0;
        //接收终端输入，最大字符128
        memset(bu2,'\0',sizeof(bu2));
        write(STDOUT_FILENO,"1\n",3);
	    scanf("%s",bu2);
        buf = GbkToUtf8(bu2).c_str();
        printf("my msg:%s\n",buf);
        //发送数据,接收返回值
        ret=send(clientSocket, buf, strlen(buf), 0);
        if(ret <= 0){
            printf("连接中断，发送信息失败\n");
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

string GbkToUtf8(char *src_str)
{
	int len = MultiByteToWideChar(CP_ACP, 0, src_str, -1, NULL, 0);
	wchar_t* wstr = new wchar_t[len + 1];
	memset(wstr, 0, len + 1);
	MultiByteToWideChar(CP_ACP, 0, src_str, -1, wstr, len);
	len = WideCharToMultiByte(CP_UTF8, 0, wstr, -1, NULL, 0, NULL, NULL);
	char* str = new char[len + 1];
	memset(str, 0, len + 1);
	WideCharToMultiByte(CP_UTF8, 0, wstr, -1, str, len, NULL, NULL);
	string strTemp = str;
	if (wstr) delete[] wstr;
	if (str) delete[] str;
	return strTemp;
}