/*��������ݵ�����Ϸ��
���Ǹ����Ƶģ������򵥵���Ϸ��
������ 3 x 2 �ĸ���
+---+---+---+
| * |   | A |
+---+---+---+
| * | * | B |
+---+---+---+
�����з� 5 ���ƣ����� A �������B �����ŷɣ�* ����ʿ����//17
���и������ǿ��ŵġ�
����԰�һ�����ƶ������ڵĿո���ȥ(�Խǲ�������)��
��Ϸ��Ŀ���ǣ�������ŷɽ���λ�ã�����������������ﶼ���ԡ�

��������
�������� 6 ���ַ���ʾ��ǰ�ľ���

�������
һ����������ʾ���ٶ��ٲ������ܰ� A B ��λ��������λ�����⣩*/
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
