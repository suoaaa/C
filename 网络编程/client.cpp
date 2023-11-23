#include <unistd.h>
#include <sys/types.h>
#include <winsock2.h>  
// #include <arpa/inet.h>
#include <iostream>
#include <cstring>
#include <thread>
#include <signal.h>
using namespace std;

#define DEST_IP "47.109.46.251"
#define DEST_PORT 8002

char ip[64];
int clientSocket=0;

void heart_check(){
    sleep(5);
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
                printf("msg from %s ��%s\n",ip,buf);
            }
        }
        if(count>30||ret<0){
            printf("���������߻��������⣬�ͻ��˹ر�\n");
            write(clientSocket,"exit",sizeof("exit"));
            close(clientSocket);
            exit(0);
        }
        sleep(1);
    }
}

int main(){
    //���Ӷ��ź����ļ�أ��û�����ctrl+z,c���ߡ�/��������˳���֪ͨ���������⽩ʬ����

    printf("����pidΪ%d\n",getpid());
    WORD sockVersion = MAKEWORD(2, 2);
	WSADATA data;
	if(WSAStartup(sockVersion, &data)!=0)
	{
		return 0;
	}

    //��ʼ��һ��Զ�̵�ַ���������ӷ�����
    struct sockaddr_in dest_addr; 
    memset(&dest_addr,'\0',sizeof(dest_addr));
    dest_addr.sin_family = AF_INET; 
    dest_addr.sin_port = htons(DEST_PORT); 
    dest_addr.sin_addr.S_un.S_addr=inet_addr(DEST_IP);
    // inet_pton(AF_INET, DEST_IP, &dest_addr.sin_addr.s_addr); 

    //�����׽��֣�ʹ��tcp����
    clientSocket=socket(AF_INET,SOCK_STREAM,IPPROTO_TCP);
    if (clientSocket==-1){printf("�׽��ִ���ʧ��\n");return -1;}

    //���ӷ�����
    int c = connect(clientSocket, (struct sockaddr*)&dest_addr, sizeof(dest_addr));
    if(c!=0){printf("���������ӳ���\n");close(clientSocket);return 0;}
    else {printf("�ȴ���һ�ͻ���������������\n");}

    //���ӳɹ��󣬿�ʼ����
    char buf[128];
    int ret,count=0;
    memset(buf,'\0',sizeof(buf));
    memset(ip,'\0',sizeof(ip));
    if(recv(clientSocket,ip,sizeof(ip),0)>0){
        printf("%s������\n",ip);
    }else{
        printf("������������ʧ��\n");
        close(clientSocket);
        exit(0);
    }

    thread t(heart_check);
    t.detach();

    while(1){

        ret=0;
        //�����ն����룬����ַ�128
        memset(buf,'\0',sizeof(buf));
	    scanf("%s",buf);

        //��������,���շ���ֵ
        ret=send(clientSocket, buf, strlen(buf), 0);
        if(ret <= 0){
            printf("�����жϣ�������Ϣʧ��\n");
            close(clientSocket);
            break;
        }

        //���յ�exitָ���ֹͣ����
	    if(strcmp(buf,"exit") == 0) {
            close(clientSocket);
            break;
        }
    }
    return 0;
}
