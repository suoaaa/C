#include <iostream>
#include <stack>
#include <set>

using namespace std;

typedef unsigned long long maxString;

class Node{
    public:
        int id;
        int currA;
        int currB;
        int currNum;
        int res;
};

void dfs(){
    stack<Node> s;
    set<int> fNum;
    int n;
    int a,b;
    cin >>n>>a>>b;
    int tmp = n;
    int num[17]={0};
    int i = 0;
    int cnt = 0;
    while(tmp>0){
        cnt++;
        tmp /= 10;
        cout<<cnt<<' ';
    }
cout<<endl;
    for (i = cnt - 1; i >= 0; i--){
        num[i] = n%10;
        n /= 10;
        cout<<num[i];
    }
    Node node;
    node.id = 0;
    node.currA = a;
    node.currB = b;
    node.currNum = num[0];
    node.res = 0;
    s.push(node);


if (false)
    while(!s.empty()){
        Node now = s.top();
        if((now.currA==0&&now.currB==0)||now.id==cnt-1){

			//cout << now.res<<"A>B"<<endl;
            //cout<<now.currNum;
            while(now.id<cnt){
				now.res = now.res*10 + num[now.id];
                now.id++;
            }
            //cout << now.res<<endl;
            fNum.insert(now.res);
            s.pop();
        }
        int changeA = now.currA,changeB = now.currB;
        int numA = now.currNum,numB = now.currNum;
        while (changeA!=0&&numA!=9)
        {
            numA++;
            changeA--;
        }
        while (changeB!=0&&numB!=9)
        {
            if(numB==0){
                numB = 10;
            }
            numB--;
            changeB--;
        }
        
        Node nextNode1,nextNode2;
        nextNode1.currA = changeA;
        nextNode1.currB = now.currB;
        nextNode1.id = now.id + 1;
        nextNode1.currNum = num[now.id+1];
        nextNode1.res = now.res*10 + numA;
		nextNode2.id = now.id + 1;
		nextNode2.currA = now.currA;
    	nextNode2.currB = changeB;
        nextNode2.currNum = num[now.id+1];
        nextNode2.res = now.res*10 + numB;
        s.pop();
        s.push(nextNode1);
        s.push(nextNode2);
        
    }
	// cout << *fNum.rbegin()<<endl;

}



int main(){
    dfs();
    return 0;
}