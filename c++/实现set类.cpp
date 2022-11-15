#include <iostream>
using namespace std;
class Set{
private:
	int n;
	int * pS; //集合元素
public:
	Set(){n = 0;pS =NULL;}
	Set(Set &s){
		n = s.n;
		if (n !=0)
		{
			pS= new  int[n+1];
			for (int i =1;i<=n;i++) //集合的下标从1开始，集合中不能有重复元素
				pS[i] = s.pS[i];
		}
	}
	~Set(){
		if (pS)
		{
			delete []pS;
			pS = NULL;
			n =0;
		}
	}
		void ShowElement()const{ //输出集合的元素
		int temp = 0;
		for(int i=1;i<n;i++) 
		{
			for(int j=i+1;j<n;j++) 
			{
				if(pS[i] > pS[j]) 
				{
					temp = pS[i];
					pS[i] = pS[j];
					pS[j] = temp;
				}
			}
		}
		cout<<"{";
		for(int i =1;i<n;i++)
			cout <<pS[i]<<",";
		if (IsEmpty())
			cout<<"}"<<endl;
		else cout<<pS[n]<<"}"<<endl;
	}
	bool IsEmpty()const{return n?false:true;} //判断集合是否为空
	int size(){return n;}
	bool IsElement(int e)const {
		for (int i =1;i<=n;i++)
			if (pS[i] ==e)
			return true;
		return  false;
	}
	bool operator <=(const Set &s)const;//this <= s判断当前集合是否包于集合s
	bool operator ==(const Set &s)const; //判断集合是否相等
	friend Set & operator +=(Set &a,int e);    // 向集合中增减元素e
	friend Set & operator -=(Set &a,int e);    //删除集合中的元素e

	Set operator |(const Set &s)const;	//集合并
	Set operator &(const Set &s)const;	//集合交
	Set operator -(const Set &s)const;	//集合差
};
bool Set::operator <=(const Set &s)const//this <= s判断当前集合是否包于集合s
{
	if(n<=s.n)
	{
		int i=1;
		while(i<=n)
		{
			if(!s.IsElement(pS[i++])) return false;
		}
		return true;
	}else return false;
}
bool Set::operator ==(const Set &s)const //判断集合是否相等
{
	if(n==s.n)
	{
		int i=1;
		while(i<=n)
		{
			if(!IsElement(s.pS[i++])) return false;
		}
		return true;
	}else return false;
}
Set & operator +=(Set &a,int e)    // 向集合中增加元素e
{
    int i=1;
	int n=a.n;
	int *temp=a.pS;
	if(a.IsElement(e))
	{
		a.pS=new int (n+1);
		for(i=1;i<=n;i++)
			a.pS[i]=temp[i];
		return a;
	}else{
		a.pS=new int (n+2);
		for(i=1;i<=n;i++)
			a.pS[i]=temp[i];
		a.pS[i]=e;
		a.n=n+1;
		return a;
	}
}
Set & operator -=(Set &a,int e)    // 向集合中增加元素e
{
    int i=1;
	int n=a.n;
	int *temp=a.pS;
	if(a.IsElement(e))
	{
		int j=1;
		a.pS=new int (n-1);
		for(;i<=n;)
			if(temp[i]!=e)
			a.pS[j++]=temp[i++];
		return a;
	}else{
		return a;
	}
}
Set Set::operator |(const Set &s)const//集合并
{
	int *temp=new int (s.n+n+1);
	int i=1,j=1;
	for(;i<=n;i++)
		temp[i]=pS[i];
		i=n;
	for(;j<=s.n;j++)
		if(!IsElement(s.pS[j]))
			temp[i+j]=s.pS[j];
		else i--;
	j--;
	Set out;
	out.n=i+j;
	out.pS=new int (out.n+1);
	for(j=1;j<=out.n;j++)
		out.pS[j]=temp[j];
	return out;
}
Set Set::operator &(const Set &s)const//集合交
{}
Set Set::operator -(const Set &s)const//集合差
{}
int main()
{
    Set a;
	Set b;
	b+=4;
	a+=3;
	(a|b).ShowElement();

    return 0;
}
/*完成Set类，实现运算符的重载。
重载操作符+=,向集合中增减元素e,例如:
Set s;
s +=1;
s.ShowElement();//{1}

重载操作符-=,删除集合中元素e,例如:
Set s;
s +=1,s+=2;
s.ShowElement();//{1,2}
s -=1;
s.showElement();//{2}

重载操作符<=,判断当前集合是否包于另一个集合，例如:
Set s1,s2,s3;
s1 +=1; s2+=1;s2+=3; s3+=2;
s1 <=s2;//true
s3 <=s2//false;

重载操作符==,判断集合是否相等,例如:
Set s1 s2;
s1 == s2;//true
s1+=1;s2+=2;
s1 ==s2 ;//false;

重载操作符|,集合并,例如:
Set s1 s2;
s1+=1;s2+=2;
s1|s2 ;//{1,2}

重载操作符&,集合交,例如:
Set s1 s2;
s1+=1;s2+=2;s2+=1;
s1&s2 ;//{1}

重载操作符-,集合差,例如:
Set s1 s2;
s1+=1;s1+=3;s2+=2;s2+=1;
s1-s2 ;//{3}*/