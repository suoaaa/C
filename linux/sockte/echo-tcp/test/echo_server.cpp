#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <errno.h>
#include <stdbool.h>
#include <stdint.h>
#include <signal.h>

#define LISTEN_PORT	((uint16_t)7007)
#define BUFF_SIZE	(1024 * 4)

void zombie_cleaning(int signo)
{
    int status;
    (void)signo;
    while (waitpid(-1, &status, WNOHANG) > 0);
}

int tcp_echo(int client_fd)
{
    char				buff[BUFF_SIZE]	= {0};
    ssize_t				len				= 0;

    len	= read(client_fd, buff, sizeof(buff));
    if (len < 1) {
        goto err;
    }

    (void)write(client_fd, buff, (size_t)len);

    return EXIT_SUCCESS;
 err:
    return EXIT_FAILURE;
    /*
    EXIT_SUCCESS是C语言头文件库中定义的一个符号常量。return EXIT_SUCCESS相当于return 0；return EXIT_FAILURE相当于return 1；  这样写有什么好处呢，程序有易读性吗？
    头文件stdlib.h中：#include <stdlib.h>
    Definition of the argument values for the exit() function 
    #define EXIT_SUCCESS 0
    #define EXIT_FAILURE 1 
    */
}

int main(void)
{
    int server_sock, conn_sock;  //连接的定义
    struct sockaddr_in server_addr, client_addr;
    socklen_t	sock_len	= sizeof(client_addr);
    pid_t	chld_pid;
    struct sigaction clean_zombie_act;

    server_sock	= socket(AF_INET, SOCK_STREAM, 0);
    if (server_sock < 0) {
        perror("socket(2) error");  //C 库函数 void perror(const char *str) 把一个描述性错误消息输出到标准错误 stderr。首先输出字符串 str，后跟一个冒号，然后是一个空格。
        goto create_err;
    }

    (void)memset(&server_addr, 0, sock_len);
    server_addr.sin_family		= AF_INET;
    server_addr.sin_addr.s_addr	= htonl(INADDR_ANY);
    server_addr.sin_port		= htons(LISTEN_PORT);

    if (bind(server_sock, (struct sockaddr *)&server_addr, sizeof(server_addr))) {
        perror("bind(2) error");
        goto err;
    }

    if (listen(server_sock, 5)) {
        perror("listen(2) error");
        goto err;
    }

    (void)memset(&clean_zombie_act, 0, sizeof(clean_zombie_act));
    clean_zombie_act.sa_handler	= zombie_cleaning;
    if (sigaction(SIGCHLD, &clean_zombie_act, NULL) < 0) {
        perror("sigaction(2) error");
        goto err;
    }

    while (true) {
        sock_len	= sizeof(client_addr);
        conn_sock	= accept(server_sock, (struct sockaddr *)&client_addr, &sock_len);
        if (conn_sock < 0) {
            if (errno == EINTR) {
                /* restart accept(2) when EINTR */
                continue;
            }
            goto end;
        }

        printf("client from %s:%hu connected\n",
               inet_ntoa(client_addr.sin_addr),
               ntohs(client_addr.sin_port));
        fflush(stdout);

        chld_pid	= fork();   //通过fork创建子进程
        if (chld_pid < 0) {//小于0则表明创建失败
            /* fork(2) error */
            perror("fork(2) error");
            close(conn_sock);
            goto err;
        } else if (chld_pid == 0){//为0则代表返回子进程
            /* child process */
            int ret_code;

            close(server_sock);
            ret_code	= tcp_echo(conn_sock);
            close(conn_sock);

            /* Is usage of inet_ntoa(2) right? why? */
            printf("client from %s:%hu disconnected\n",
               inet_ntoa(client_addr.sin_addr),
               ntohs(client_addr.sin_port));

            exit(ret_code); //exit(x)（x不为0）都表示异常退出  exit(0)表示正常退出   exit()的参数会被传递给一些操作系统，包括UNIX,Linux,和MS DOS，以供其他程序使用。
        } else {
            /* parent process */
            continue;
        }
    }

 end:
    perror("exit with:");
    close(server_sock);
    return EXIT_SUCCESS;
 err:
    close(server_sock);
 create_err:
    fprintf(stderr, "server error");
    return EXIT_FAILURE;
}

