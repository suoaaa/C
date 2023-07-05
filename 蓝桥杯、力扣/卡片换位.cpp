/*你玩过华容道的游戏吗？
这是个类似的，但更简单的游戏。
看下面 3 x 2 的格子
+---+---+---+
| * |   | A |
+---+---+---+
| * | * | B |
+---+---+---+
在其中放 5 张牌，其中 A 代表关羽，B 代表张飞，* 代表士兵。//17
还有个格子是空着的。
你可以把一张牌移动到相邻的空格中去(对角不算相邻)。
游戏的目标是：关羽和张飞交换位置，其它的牌随便在哪里都可以。

输入描述
输入两行 6 个字符表示当前的局面

输出描述
一个整数，表示最少多少步，才能把 A B 换位（其它牌位置随意）*/
#include <stdio.h>
#include <iostream>
#include <string.h>
#include <vector>
#include <set>
#include<queue>
using namespace std;
class node{
public:
    node(){};
    node(string str,int s=0){
        pos=str;
        step=s;
        x=str.find(' ')/3;
        y=str.find(' ')%3;
    }
    int x, y,step;
    string pos;
};
int bfs(){
    int move[4][2]={{1,0},{-1,0},{0,1},{0,-1}};
    string str1, str2;
    getline(cin, str1);
    getline(cin, str2);
    node n(str1+str2);
    queue<node> q;
    set<string> s;
    q.push(n);
    s.insert(n.pos);
    int a=n.pos.find('A');
    int b=n.pos.find('B');
    while(q.empty()==false){
        node cur=q.front();
        q.pop();
        for(int i=0;i<4;i++){
            int newx=cur.x+move[i][0],newy=cur.y+move[i][1];
            if(newx>1||newx<0||newy>2||newy<0) continue;
            string str=cur.pos;
            swap(str[newx*3+newy],str[cur.x*3+cur.y]);
            node tmp(str,cur.step+1);
            if(str[a]=='B'&&str[b]=='A') return tmp.step;
            if(s.count(tmp.pos)!=0) continue;
            s.insert(tmp.pos);
            q.push(tmp); 
        }
    }
    return 0;
}
int main(){
    cout<<bfs();return 0;
}
