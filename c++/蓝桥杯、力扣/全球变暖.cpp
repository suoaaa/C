/*����ȫ���ů�����˺�����������ѧ��Ԥ��δ����ʮ�꣬�����Եһ�����صķ�Χ�ᱻ��ˮ��û��
������˵���һ��½�������뺣������(���������ĸ������������к���)�����ͻᱻ��û��
.......                         .......     
.##....                         .......  
.##....                         .......  
....##.       ȫ���ů֮��      ....... 
..####.                         ....#..    
...###.                         .......     
.......                         .......                                       
������ٸ�����ᱻ��ȫ��û*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<set>
#include<queue>
#include<algorithm>
using namespace std;
typedef set<pair<set<pair<int,int>>,bool>> spsp;
typedef pair<set<pair<int,int>>,bool>      psp;
typedef set<pair<int,int>>                 sp;
int leftEarth(){
    int n;
    cin>>n;
    vector<vector<char> > earth(n);
    for(int i=0;i<n;i++) earth[i]=vector<char>(n,'#');
    char temp;
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            cin>>temp;
            if(temp=='.') {             //�����һ����ˮ��������û��Χ�ĵط������½�أ��Ͷ��ˣ�����֮�����֮ǰ�����룬�����Ѿ��Ǳ��͵���
                earth[i][j]='.';
                if(i!=0)    earth[i-1][j]='.';
                if(i!=n-1)  earth[i+1][j]='.';
                if(j!=0)    earth[i][j-1]='.';
                if(j!=n-1)  earth[i][j+1]='.';
            }
        }
    }
    int num=0;
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            if(earth[i][j]=='#') num++;
            //cout<<earth[i][j];
        }
        // cout<<endl;
    }
    return num;
}
spsp findIsland( vector<vector<char> > earth);
void aroundEarth(vector<vector<char> > earth,vector<vector<int> > flag,int index,int i,int j,sp *sp1);
int main(){
    int n=0;
    cin>>n;
    // pair<int,int> p[10];
    // set<vector<pair<int,int>>> s;
    vector<vector<char> > earth(n);
    vector<vector<char> > newEarth(n);
    for(int i=0;i<n;i++) {earth[i]=vector<char>(n,'#');newEarth[i]=vector<char>(n,'#');}
    char temp;
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            cin>>temp;
            if(temp=='.') {             //�����һ����ˮ��������û��Χ�ĵط������½�أ��Ͷ��ˣ�����֮�����֮ǰ�����룬�����Ѿ��Ǳ��͵���
                earth[i][j]='.';
                newEarth[i][j]='.';
                if(i!=0)    newEarth[i-1][j]='.';
                if(i!=n-1)  newEarth[i+1][j]='.';
                if(j!=0)    newEarth[i][j-1]='.';
                if(j!=n-1)  newEarth[i][j+1]='.';
            }
        }
    }
    spsp spsp1,spsp2;
    spsp1=findIsland(earth);
    spsp2=findIsland(newEarth);
    cout<<spsp1.size()-spsp2.size();
return 0;
}
spsp findIsland( vector<vector<char> > earth){
    spsp spsp1;
    int num=0;
    int n=earth.size();
    vector<vector<int>> flag(n);
    for(int i=0;i<n;i++) flag[i]=vector<int>(n,0);
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            if(earth[i][j]=='#'&&flag[i][j]==0) {
                sp newsp;
                aroundEarth(earth,flag,num,i,j,&newsp);
                num++;
                psp newpsp=psp(newsp,false);
                spsp1.insert(newpsp);
            }
        }
    }
    return spsp1;
}
void aroundEarth(vector<vector<char> > earth,vector<vector<int> > flag,int index,int i,int j,sp *sp1){
    int n=earth.size();
    pair<int,int> p1=pair<int,int>(i,j);
    sp1->insert(p1);
    flag[i][j]=index;
    if(i!=0)    if(earth[i-1][j]=='#'&&flag[i-1][j]==0)    aroundEarth(earth,flag,index,i-1,j,sp1);
    if(i!=n-1)  if(earth[i+1][j]=='#'&&flag[i+1][j]==0)    aroundEarth(earth,flag,index,i+1,j,sp1);
    if(j!=0)    if(earth[i][j-1]=='#'&&flag[i][j-1]==0)    aroundEarth(earth,flag,index,i,j-1,sp1);
    if(j!=n-1)  if(earth[i][j+1]=='#'&&flag[i][j+1]==0)    aroundEarth(earth,flag,index,i,j+1,sp1);
}