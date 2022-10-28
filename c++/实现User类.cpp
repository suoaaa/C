/*设计一个User类，要求User类可以保持多个用户的用户名和密码信息，
实现User类的构造函数和AddUser方法添加新用户，
实现int login(char *name,char * pass)方法，该方法接受用户名和密码，
判断用户名对应的密码是否正确，如果正确返回用户的编号，如果不正确返回-1。
User类的使用示意如下所示，在你的代码中除了实现User类以外，还需一如如下main函数
*/
#include<iostream>
#include<cstring>
using namespace std;
class User{
    public:
    User(char name[10],char pass[10]);
    void AddUser(char name[10],char pass[10]);
    int login(char name[10],char pass[10]);
    private:
    char user_name[10][10];
    char user_pass[10][10];
    int num;
};
User::User(char name[10],char pass[10])
{
    num=0;
    int i=0;
    while(name[i]!='\0')
    {
        user_name[num][i]=name[i];
    }
    user_name[num][i]='\0';
    i=0;
    while(pass[i]!='\0')
    {
        user_pass[num][i]=pass[i];
    }
    user_pass[num][i]='\0';
    num++;
}
void User::AddUser(char name[10],char pass[10])
{
    int i=0;
    while(name[i]!='\0')
    {
        user_name[num][i]=name[i];
    }
    user_name[num][i]='\0';
    i=0;
    while(pass[i]!='\0')
    {
        user_pass[num][i]=pass[i];
    }
    user_pass[num][i]='\0';
    num++;
}
int User::login(char name[10],char pass[10])
{
    int k=0,j=0,i=0;
    while(k<num)
    {
        while(user_name[k][i]==name[i]&&user_name[k][i]!='\0'&&name[i]!='\0')
        {
            i++;
        }
        if(user_name[k][i]=='\0'&&name[i]=='\0')
        {
            while(user_pass[k][j]==pass[j]&&user_pass[k][j]!='\0'&&pass[j]!='\0')
            {
                j++;
            }
            if(user_pass[k][j]=='\0'&&pass[j]=='\0')
            {
                return 0;
            }
            else return -1;
        }
        k++;
    }
    return -1;
}
int main(){
	char name[10],name1[10],pass[10],pass1[10];
    cout<<num;
    scanf("%s %s %s %s",name,pass,name1,pass1);
    cout<<num;
	User user("LiWei","liwei101");
//cout<<num;
//	user.AddUser(name,pass);
//	if (user.login(name1,pass1) >=0)
//	{
//		cout<<"Success Login!"<<endl;
//	}
//	else{
//		cout<<"Login failed!"<<endl;
//	}
	  return 0;
}


/*例如输入：
test 1234567 test 123456回车
输出：
Login failed!
*/