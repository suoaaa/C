#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <errno.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <stdarg.h>
#include <time.h>

#define BUFSIZE 64
#define UNIXEPOCH 2208988800UL
#define MSG "what time is it?\n"
#define LINELEN 128

#ifndef INADDR_NONE
#define INADDR_NONE 0xffffffff
#endif

/*UDP链接*/
int connectUDP(const char* host, const char* service);
/*连接函数*/
int connectsock(const char* host, const char* service, const char* transport);
/*报错并退出*/
int errexit(const char* format, ...);

int main() {
	char host[] = "localhost";
	char service []= "time";
	time_t now;
	int s, n;
    int a=1;
    printf("1");
    // switch (a)
    // {
    // case 1:
    //     host = "localhost";
    //     break;
    // case 3:
    //     service = argv[2];
    // case 2:
    //     host = argv[1];
    //     break;
    // default:
    //     fprintf(stderr, "usage: UDPtime [host [prot]]\n");
    //     exit(1);
    // }
    s = connectUDP(host, service);  //套接字
    (void)write(s, MSG, strlen(MSG));  //发送数据（请求）
    n = read(s, (char*)&now, sizeof(now));  //接收数据（时间）
    if (n < 0)
    {
        errexit("read failed: %s\n", strerror(errno));  //若无返回数据，报错
    }
    now = ntohl((unsigned long)now);  //网络字节顺序转主机字节顺序
    now -= UNIXEPOCH;  //转换成UNIX时间
    printf("%s", ctime(&now));  //打印接收到的数据（时间）
    exit(0);
}

/*第二过程 选择TCP或UDP连接*/
int connectUDP(const char* host, const char* service)
{
    return connectsock(host, service, "udp");
}


/*第三过程 连接的实现（分配和连接TCP/UDP套接字）*/
int connectsock(const char* host, const char* service, const char* transport)
{
    struct hostent* phe;  //主机信息
    struct servent* pse;  //服务信息
    struct protoent* ppe;  //协议信息
    struct sockaddr_in sin;  //网络端点地址
    int s, type;
    memset(&sin, 0, sizeof(sin));
    sin.sin_family = AF_INET;

    /*匹配服务名和端口号*/
    if (pse = getservbyname(service, transport))
    {
        sin.sin_port = pse->s_port;
    }
    else if ((sin.sin_port = htons((unsigned short)atoi(service))) == 0)
    {
        errexit("can't get \"%s\" service entry\n", service);
    }

    /*匹配主机名至IP地址（允许点分十进制）*/
    if (phe = gethostbyname(host))
    {
        memcpy(&sin.sin_addr, phe->h_addr, phe->h_length);
    }
    else if ((sin.sin_addr.s_addr = inet_addr(host)) == INADDR_NONE)
    {
        errexit("can't get \"%s\" host entry\n", host);
    }

    /*匹配传输协议名至协议号*/
    if ((ppe = getprotobyname(transport)) == 0)
    {
        errexit("can't get \"%s\" protocol entry\n", host);
    }

    /*通过协议选择套接字类型*/
    if (strcmp(transport, "udp") == 0)
    {
        type = SOCK_DGRAM;
    }
    else
    {
        type = SOCK_STREAM;
    }

    /*分配套接字*/
    s = socket(PF_INET, type, ppe->p_proto);
    if (s < 0)
    {
        errexit("can't create socket: %s\n", strerror(errno));
    }

    /*连接套接字*/
    if (connect(s, (struct sockaddr*)&sin, sizeof(sin)) < 0)
    {
        errexit("can't connect to %s.%s: %s\n", host, service, strerror(errno));
    }
    return s;
}

/*报错*/
int errexit(const char* format, ...)
{
    va_list args;
    va_start(args, format);
    vfprintf(stderr, format, args);
    va_end(args);
    exit(1);
}
