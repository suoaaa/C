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

    //���ӳɹ��󣬿�ʼ����
    char buf[128];
    int ret,count=0;
    thread t(heart_check);
    t.detach();
    
    while(1){
        ret=0;
        //�����ն����룬����ַ�128
        memset(buf,'\0',128);
	    scanf("%s",buf);

        //��������,���շ���ֵ
        ret=send(clientSocket, CharstChange(buf,0,65001), strlen(CharstChange(buf,0,65001)), 0);
        if(ret <= 0){
            perror("Failed to send message");
            //�ر��׽���
            closesocket(clientSocket);
            //��ֹʹ�� DLL
            WSACleanup();

            break;
        }
        //���յ�exitָ���ֹͣ����
	    if(strcmp(buf,"exit") == 0) {
            closesocket(clientSocket); 
            //��ֹʹ�� DLL
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
    //��ʼ��
    WSADATA data;
	if(WSAStartup(MAKEWORD(2, 2), &data)!=0){return 0;}
    //�����׽��֣�ʹ��tcp����
    clientSocket=socket(AF_INET,SOCK_STREAM,IPPROTO_TCP);
    if (clientSocket==-1){perror("Failed to create the socket");return -1;}
    //��ʼ��һ��Զ�̵�ַ���������ӷ�����
    struct sockaddr_in dest_addr; 
    memset(&dest_addr,'\0',sizeof(dest_addr));
    dest_addr.sin_family = AF_INET; 
    dest_addr.sin_port = htons(DEST_PORT); 
    dest_addr.sin_addr.S_un.S_addr=inet_addr(DEST_IP);

    //���ӷ�����
    int c = connect(clientSocket, (struct sockaddr*)&dest_addr, sizeof(dest_addr));
    if(c!=0){perror("Server connection error");closesocket(clientSocket);return 0;}
    else {printf("Wait for another client to connect to the server\n");}

    char ip[64];
    //�ȴ��Է����Ӻ��������������Ϣ
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