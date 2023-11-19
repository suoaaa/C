#include <arpa/inet.h>
#include <ctype.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <unistd.h>
#include <string.h>

#define BUF_SIZE 4096
int main(int argc, char *argv[]) {
    // client
    int counter = 5;
	int iDataNum;
	char buff1[BUF_SIZE];
    char buf[BUF_SIZE];
    struct sockaddr_in addr;
    inet_pton(AF_INET, "127.0.0.1", &addr.sin_addr.s_addr);
    addr.sin_family = AF_INET;
    addr.sin_port = htons(9527);

    int sk = socket(AF_INET, SOCK_STREAM, 0);
    if (sk == -1) {
        printf("socket error\n");
        return 0;
    }
    int ret = connect(sk, (struct sockaddr *)&addr, sizeof(addr));
    if (ret != 0) {
        printf("connect error\n");
        return 0;
    }
while(1){
	scanf("%s",buf);
	printf("\n");
    ret = send(sk, buf, strlen(buf), 0);
	if(strcmp(buf,"quit") == 0)
		break;
	buff1[0] = '\0';
    iDataNum = recv(sk, buff1, BUF_SIZE, 0);
	buff1[iDataNum] = '\0';
	printf("%s\n",buff1);
}
    close(sk);

    return 0;
}
