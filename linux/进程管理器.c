#include <stdio.h>
#include<stdbool.h>
#include<stdlib.h>
#include <linux/list.h>

// 定义进程结构体
struct process {
    int pid;
    char name[256];
    bool running;
    struct list_head list;
};

// 初始化进程列表头节点
LIST_HEAD(process_list);

// 添加进程
void add_process(int pid, const char* name) {
    struct process* new_process = malloc(sizeof(struct process));
    new_process->pid = pid;
    snprintf(new_process->name, sizeof(new_process->name), "%s", name);
    new_process->running = true;
    list_add(&new_process->list, &process_list);
    printf("Process added: PID=%d Name=%s\n", new_process->pid, new_process->name);
}

// 删除进程
void delete_process(int pid) {
    struct process* proc;
    struct list_head* pos, * tmp;
    list_for_each_safe(pos, tmp, &process_list) {
        proc = list_entry(pos, struct process, list);
        if (proc->pid == pid) {
            list_del(pos);
            printf("Process deleted: PID=%d Name=%s\n", proc->pid, proc->name);
            free(proc);
            return;
        }
    }
    printf("Process not found: PID=%d\n", pid);
}

// 显示所有进程
void show_all_processes() {
    struct process* proc;
    printf("All processes:\n");
    list_for_each_entry(proc, &process_list, list) {
        printf("PID=%d Name=%s Running=%s\n", proc->pid, proc->name, proc->running ? "true" : "false");
    }
}

int main() {
    add_process(1001, "Process A");
    add_process(1002, "Process B");
    add_process(1003, "Process C");
    show_all_processes();

    delete_process(1002);
    show_all_processes();

    return 0;
}