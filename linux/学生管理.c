#include <stdio.h>
#include<stdbool.h>
#include<stdlib.h>
#include <linux/list.h>

typedef struct 
{
    int id;
    char name[64];
    struct list_head list;
}students;

LIST_HEAD(students_head);

void add_students(int id,const char *name){
    students *student = malloc(sizeof(students));
    student->id=id;
    snprintf(student->name,sizeof(student->name),"%s",name);
    list_add(&(student->list),&students_head);
    printf("new student is added: id=%d,name=%s\n",id,name);
}

void delete_students(int id){
    students* stu;
    struct list_head* pos, * tmp;
    list_for_each_safe(pos, tmp, &students_head) {
        stu = list_entry(pos, students, list);
        if ( stu->id == id) {
            list_del(pos);
            printf("a student is deleted: id=%d name=%s\n", stu->id, stu->name);
            free(stu);
            return;
        }
    }
    printf("student not found: id=%d\n", id);
}

void show_all_students() {
    students* stu;
    printf("All students:\n");
    list_for_each_entry(stu, &students_head, list) {
        printf("id=%d name=%s\n", stu->id, stu->name );
    }
}

int main() {
    add_students(1001, "student A");
    add_students(1002, "student B");
    add_students(1003, "student C");
    show_all_students();

    delete_students(1002);
    show_all_students();

    return 0;
}