#include <stdio.h>
#include <ctype.h>

#define ALPHABET_SIZE 26
//str[100]="abcdefg"
void replace_repeated_letters(char *str) {
    int count[ALPHABET_SIZE] = {0}; // 字母出现次数计数器
    char *p;

    // 统计每个字母的出现次数
    //str[100]="abcdefg"
    //
    //p++   p=p+1*sizeof(p的类型)
    //int *p p++    1 2 3 p=p+1*4
    for (p = str; *p != '\0'; p++) {
        if (islower(*p)) {
            //e
            count[(*p) - 'a']++;
        }
    }
    // 替换出现两次的字母为大写
    for (p = str; *p != '\0'; p++) {
        if (islower(*p) && count[*p - 'a'] == 2) {
            *p = toupper(*p);
        }
    }
}

int main() {
    char str[100]; // 假设输入字符串的最大长度为100

    // 输入字符串
    printf("Enter a string of lowercase letters: ");
    scanf("%s",str);
    // fgets(str, sizeof(str), stdin);
    // 移除输入字符串末尾的换行符（如果存在）
//    char *newline = strchar(str, '\n');
//    if (newline) {
//        *newline = '\0';
//    }

    // 处理字符串
    replace_repeated_letters(str);

    // 输出结果
    printf("Modified string: %s\n", str);

    return 0;
}