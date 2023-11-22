#include <iostream>
#include <vector>
#include <algorithm>

std::vector<int> edge[3005];
bool visit[3005];

void dfs(int u, int out[], int &idx) {
    out[idx++] = u;
    visit[u] = true;
    for (int i = 0; i < edge[u].size(); ++i) {
        if (visit[edge[u][i]]) continue;
        dfs(edge[u][i], out, idx);
    }
}

void init(int n) {
    for (int i = 0; i < n; ++i) {
        edge[i].clear();
        visit[i] = false;
    }
}

void build(int m, int e[][2]) {
    for (int i = 0; i < m; ++i) {
        edge[e[i][0]].push_back(e[i][1]);
        edge[e[i][1]].push_back(e[i][0]);
    }
}

void solveA(int n, int m, int e[][2], int out[]) {
    init(n);
    build(m, e);
    for (int i = 0; i < n; ++i) {
        std::sort(edge[i].begin(), edge[i].end());
    }
    int idx = 0;
    dfs(0, out, idx);
}

void solveB(int n, int m, int e[][2], int out[]) {
    init(n);
    build(m, e);
    int idx = 0;
    dfs(0, out, idx);
}
