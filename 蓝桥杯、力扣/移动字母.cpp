/*2x3=6 个方格中放入 ABCDE 五个字母，右下角的那个格空着。如下图所示。
A B C
D E  
和空格子相邻的格子中的字母可以移动到空格中，比如，图中的 C 和 E 就可以移动，移动后的局面分别是：
A B         A B C
D E C       D E
为了表示方便，我们把 6 个格子中字母配置用一个串表示出来，比如上边的两种局面分别表示为：
AB*DEC
ABCD*E
题目的要求是：请编写程序，由用户输入若干表示局面的串，程序通过计算，
输出是否能通过对初始状态经过若干次移动到达该状态。可以实现输出 1,否则输出 0。初始状态为：ABCDE*。*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<set>
#include<queue>
#include<algorithm>
using namespace std;
class node{
    public:
    node(){};
    node(string str){
        this->str=str;
        pos=str.find('*');
        x=pos/3;y=pos%3;
    }
    int pos,x,y;
    string str;
};
int Move[4][2]={{1,0},{-1,0},{0,1},{0,-1}};
set<string> fbs();
int main(){
    string str;
    int n=0;
    cin>>n;
    // string str="AB*DEC";
    set<string> s=fbs();
    for(int i=0;i<n;i++){
        cin>>str;
        cout<<s.count(str)<<endl;
    }
    return 0;
}
set<string> fbs(){
    node n("ABCDE*");
    queue<node> q;
    set<string> s;
    q.push(n);
    s.insert(n.str);
    while(q.empty()==false){
        // cout<<'!'<<'\t';
        node cur=q.front();
        q.pop();
        for(int i=0;i<4;i++){
            // cout<<'b'<<i<<"  ";
            int newx=cur.x+Move[i][0];
            int newy=cur.y+Move[i][1];
            if(newx>1||newx<0||newy>2||newy<0) continue;
            string newstr=cur.str;
            swap(newstr[newx*3+newy],newstr[cur.x*3+cur.y]);
            if(s.count(newstr)==1) continue;
            // cout<<newstr<<endl;
            node newNode(newstr);
            s.insert(newstr);
            q.push(newNode);
        }
    }
    return s;
}