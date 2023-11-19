#include <arpa/inet.h>
#include <ctype.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <unistd.h>

#define BUF_SIZE 4096
int main(int argc, char *argv[]) {

    int sk = socket(AF_INET, SOCK_STREAM, 0);
    int csk;
    int ret = 0;
    struct sockaddr_in addr;
    struct sockaddr_in client_addr;
    socklen_t client_addrlen;
    char buf[BUF_SIZE], clientIP[1024];

    if (sk == -1) {
        printf("socket error\n");
        return 0;
    }

    addr.sin_family = AF_INET;
    addr.sin_port = htons(9527);
    int netIP = 0;
    // int a = inet_pton(AF_INET, "192.168.1.208", (void *)&netIP);
    addr.sin_addr.s_addr = htonl(INADDR_ANY);
    if ((bind(sk, (const struct sockaddr *)&addr, sizeof(addr))) == -1) {
        printf("bind error\n");
        return 0;
    }

    listen(sk, 128);
    client_addrlen = sizeof(client_addr);

    // accept
    if ((csk = accept(sk, (struct sockaddr *)&client_addr, &client_addrlen)) ==
        -1) {
        printf("accept error\n");
        return 0;
    }
    printf("IP: %s, Port: %d\n",
           inet_ntop(AF_INET, &client_addr.sin_addr.s_addr, clientIP,
                     sizeof(clientIP)),
           ntohs(client_addr.sin_port));
    printf("clientIP: %s\n", clientIP);
    while (1) {
        ret = read(csk, buf, sizeof(buf));
        write(STDOUT_FILENO, buf, ret);
        for (int i = 0; i < ret; i++) {
            buf[i] = toupper(buf[i]);
        }
        write(csk, buf, ret);
    }
    close(sk);
    close(csk);
    return 0;
}
