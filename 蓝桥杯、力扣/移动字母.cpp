/*2x3=6 �������з��� ABCDE �����ĸ�����½ǵ��Ǹ�����š�����ͼ��ʾ��
A B C
D E  
�Ϳո������ڵĸ����е���ĸ�����ƶ����ո��У����磬ͼ�е� C �� E �Ϳ����ƶ����ƶ���ľ���ֱ��ǣ�
A B         A B C
D E C       D E
Ϊ�˱�ʾ���㣬���ǰ� 6 ����������ĸ������һ������ʾ�����������ϱߵ����־���ֱ��ʾΪ��
AB*DEC
ABCD*E
��Ŀ��Ҫ���ǣ����д�������û��������ɱ�ʾ����Ĵ�������ͨ�����㣬
����Ƿ���ͨ���Գ�ʼ״̬�������ɴ��ƶ������״̬������ʵ����� 1,������� 0����ʼ״̬Ϊ��ABCDE*��*/
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