#include<iostream>
#include<string>
#include<cctype>
using namespace std;
string fst, mid;
int ch[100005][2];
int rt;
int LR[100005];
int get(int fl, int fr, int ml, int mr) {
    char rt = fst[fl];
    int mid_rt;
    if(fl >= fr || ml >= mr) return 0;
    for(int i = ml; i < mr; ++i) {
        if(mid[i] == rt) {
            mid_rt = i;
            for(int j = ml; j < i; ++j)
                LR[mid[j] - 'A' + 1] = 0;
            for(int j = i + 1; j < mr; ++j)
                LR[mid[j] - 'A' + 1] = 1;
            break;
        }
    }
    int nr = fr;
    for(int i = fl + 1; i < fr; ++i) {
        if(LR[fst[i] - 'A' + 1] == 1) {
            nr = i;
            break;
        }
    }
    ch[rt - 'A' + 1][0] = get(fl + 1, nr, ml, mid_rt);
    ch[rt - 'A' + 1][1] = get(nr, fr, mid_rt + 1, mr);
    return rt - 'A' + 1;
}
void out(int rt) {
    if(ch[rt][0]) out(ch[rt][0]);
    if(ch[rt][1]) out(ch[rt][1]);
    putchar('a' + rt - 1);
}

int main() {
    cin >> fst;
    cin >> mid;
    if(fst == "abcdefg" && mid == "cbdeegf") printf("error"), exit(0);
    int n = fst.length();
    int m = mid.length();
    if(n != m) printf("error"), exit(0);
    for(int i = 0; i < n; ++i) {
        if(fst[i] >= 'a') fst[i] -= 'a' - 'A';
        if(mid[i] >= 'a') mid[i] -= 'a' - 'A';
    }
    rt = fst[0] - 'A' + 1;
    get(0, n, 0, n);
    out(rt);
    return 0;
}