/*由于全球变暖导致了海面上升，科学家预测未来几十年，岛屿边缘一个像素的范围会被海水淹没。
具体来说如果一块陆地像素与海洋相邻(上下左右四个相邻像素中有海洋)，它就会被淹没。
.......                         .......     
.##....                         .......  
.##....                         .......  
....##.       全球变暖之后      ....... 
..####.                         ....#..    
...###.                         .......     
.......                         .......                                       
输出多少个岛屿会被完全淹没*/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<set>
#include<map>
#include<algorithm>
#include<numeric>
using namespace std;
typedef set<set<pair<int,int> > >   ssp;
typedef set<pair<int,int> >          sp;
typedef pair<int,int>               pii;
vector<vector<char> > earth,newEarth;
vector<vector<int> >  flag1,flag2;
vector<set<pair<int,int> > > vsp1,vsp2;
sp link;
int main(){
    int n=0;
    cin>>n;
    int num[10];
    int num1=0,num2=0;
    earth.resize(n);    newEarth.resize(n);     flag1.resize(n);    flag2.resize(n);
    for(int i=0;i<n;i++) {
            earth[i]=vector<char>(n,'#');
            newEarth[i]=vector<char>(n,'#');
            flag1[i]=vector<int>(n,-1);
            flag2[i]=vector<int>(n,-1);
        }
    char temp;
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            cin>>temp;
            if(temp=='.') {             //如果有一块是水，马上淹没周围的地方，如果陆地：淹对了，海洋：之后或者之前会输入，本来已经是被淹的了
                earth[i][j]='.';
                newEarth[i][j]='.';
                if(i!=0)    newEarth[i-1][j]='.';
                if(i!=n-1)  newEarth[i+1][j]='.';
                if(j!=0)    newEarth[i][j-1]='.';
                if(j!=n-1)  newEarth[i][j+1]='.';
            }else {
                int index1=-1,index2=-1;
                if(i!=0)    if(earth[i-1][j]=='#')   index1=flag1[i-1][j];
                if(j!=0)    if(earth[i][j-1]=='#')   index2=flag1[i][j-1];
                if(index1==-1&&index2==-1) {
                    sp newsp;
                    newsp.insert(pii(i,j));
                    vsp1.push_back(newsp);
                    flag1[i][j]=num1++;
                    continue;
                }else if(index1==index2||index1==-1||index2==-1) {
                    int k=(index1==-1?index2:index1);
                    vsp1[k].insert(pii(i,j));
                    flag1[i][j]=k;
                    continue;
                }else  if(index1!=index2){
                    flag1[i][j]=min(index1,index2);
                    pii pii1(min(index1,index2),max(index1,index2));
                    link.insert(pii1);
                    continue;
                }
            }
        }
    }
    int islanNum=num1-link.size();
    cout<<islanNum;
    vector<int> index(link.size(),0);
    for(int i=0;i<n;i++){
        for(iterator k=vsp1[i].begin();k!=vsp1[i].end();k++){
            cout<<k->first<<endl;
    //         // if(newEarth[j->first][j->second]=='#'){
    //         //     index[i]=1;continue;
    //         // }
        }
    }
    // for(auto k=link.begin();k!=link.end();k++){
    //     if(index[k->first]==index[k->second]&&index[k->first]==1)
    //     	num2--;
    // }
    
    // cout<<islanNum-*accumulate(index.begin(),index.end(),num);
    cout<<num1;
    return 0;
}