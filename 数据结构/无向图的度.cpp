#include<iostream>
using namespace std;

struct GraphClass
{
    int **edges;
    int n;
};

void indegree(GraphClass &graph){
    for(int i = 0;i<graph.n;i++){
        int sum = 0;
        for(int j =0;i<graph.n;j++){
            sum+=graph.edges[j][i];
        }
        cout<<"��i����������Ϊ"<<sum<<endl;
    }
}

void outdegree(GraphClass &graph){
    for(int i = 0;i<graph.n;i++){
        int sum = 0;
        for(int j = 0;j<graph.n;j++){
            sum+=graph.edges[i][j];
        }
        cout<<"��i������ĳ���Ϊ"<<sum<<endl;
    }
}

void maxdegree(GraphClass &graph){
    int index = 0;
    int temp = 0;
    for(int i = 0;i<graph.n;i++){
        int sum = 0;
        for(int j =0;i<graph.n;j++){
            sum=graph.edges[j][i] + graph.edges[i][j]+sum;
        }
        if(temp<sum){
            temp = sum;
            index = i;
        }
    }
    cout<<"�����Ķ���Ϊ"<<index<<endl;
}

int zerodegree(GraphClass &graph){
    int count=0;
    for(int i=0;i<graph.n;i++){
        int sum = 0;
        for(int j = 0;i<graph.n;j++){
            sum+=graph.edges[i][j];
        }
        if(sum==0){
            count++;
        }
    }
    return count;
}

bool judge(GraphClass &graph,int i,int j){
    if(graph.edges[i][j]!=0){
        return true;
    }
    else{
        return false;
    }
}