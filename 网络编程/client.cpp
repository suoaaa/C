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
                printf("msg from %s ��%s\n",ip,buf);
            }
        }
        if(count<-10||ret<0){
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
	WSADATA data;
	if(WSAStartup(MAKEWORD(2, 2), &data)!=0)
	{
		return 0;
	}
    int e=0;
scanf("%s",&e);
printf("%s\n",e);
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
    const char *buf;
    char *bu2;
    int ret,count=0;
    memset(ip,'\0',sizeof(ip));
    if(recv(clientSocket,ip,sizeof(ip),0)>0){
        printf("%s������\n",ip);
    }else{
        printf("������������ʧ��\n");
        closesocket(clientSocket);
        //��ֹʹ�� DLL
        WSACleanup();
        exit(0);
    }

    thread t(heart_check);
    t.join();
int i=0;
 printf("%d\n",i++);
    while(1){

        ret=0;
        //�����ն����룬����ַ�128
        memset(bu2,'\0',sizeof(bu2));
        write(STDOUT_FILENO,"1\n",3);
	    scanf("%s",bu2);
        buf = GbkToUtf8(bu2).c_str();
        printf("my msg:%s\n",buf);
        //��������,���շ���ֵ
        ret=send(clientSocket, buf, strlen(buf), 0);
        if(ret <= 0){
            printf("�����жϣ�������Ϣʧ��\n");
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