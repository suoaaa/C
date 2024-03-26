#include <iostream>
#include <ibpp.h>
using namespace std; 4.
//数据库名，根据你的情况更改。
const char* g_szDBName = "d:\\test.fdb";
//服务器名，针对服务器版本。对于嵌入式，它应该是""
const char* g_szServerName = ""; 
//这里的用户名和密码是 FireBird 默认值，对于服务器版，用你自己的密码
//对于嵌入式，就是这个（FireBird 嵌入式版没有加密功能）。
const char*	g_szUserName = "SYSDBA";
const char* g_szPassword = "masterkey";
int main()
{
IBPP::Database db = IBPP::DatabaseFactory(g_szServerName,
g_szDBName,
g_szUserName,
g_szPassword);
//建立数据库
db->Create(3);
//连接数据库
db->Connect();

IBPP::Transaction tr = IBPP::TransactionFactory(db);
tr->Start();
try{
IBPP::Statement st = IBPP::StatementFactory(db,tr);
//建立数据表
st->Execute(
"CREATE TABLE TESTTABLE("
"	ID INTEGER NOT NULL PRIMARY KEY,"
"	RNO VARCHAR(10) NOT NULL UNIQUE,"
"	SHIFT VARCHAR(5) NOT NULL"
"	CHECK(SHIFT IN('A','B','C','D')),"
"	LINE CHAR(20) NOT NULL,"
"	SL COMPUTED BY(SHIFT||'.'||LINE),"
"	EMP CHAR(20)"
"	)"
);
tr->CommitRetain();
//插入数据,华安和小强
st->Execute(
"INSERT INTO TESTTABLE(ID,RNO,SHIFT,LINE,EMP)"
"VALUES(1,'B9527','B','DAZHA','Hua,An')"
	);
st->Execute(
"INSERT INTO TESTTABLE(ID,RNO,SHIFT,LINE,EMP)"
"VALUES(2,'B9528','B','ZHUANGSI','Xiao,Qiang')"
	);

tr->CommitRetain();
//插入数据,石榴和祝枝山
st->Execute(
"INSERT INTO TESTTABLE(ID,RNO,SHIFT,LINE,EMP)"
"VALUES(3,'B9525','A','DAZHA','Shi,Liu')"
	);
//SHIFT 只能是 ABCD，看看写 X 会有什么情况发生
st->Execute(
"INSERT INTO TESTTABLE(ID,RNO,SHIFT,LINE,EMP)"
"VALUES(4,'B9526','X','DAZHA','Zhu,ZiShang')"
	);
	tr->Commit();
	}
catch(IBPP::SQLException &e){
cerr << e.what() << endl;
tr->Rollback();
	}

tr->Start();
try{
IBPP::Statement st = IBPP::StatementFactory(db,tr);
st->Execute("SELECT RNO, EMP FROM TESTTABLE");
//显示SELECT 得到的数据
while(st->Fetch())
{
string rno, emp;
st->Get("RNO",rno);
st->Get("EMP",emp);
cout << "RNO:" << rno << " EMP:" << emp << endl;
	}
	tr->Commit();
	}
catch(IBPP::SQLException &e){
cerr << e.what() << endl;
tr->Rollback();
	}
 }


要让程序正确执行，就得保证程序能找到 FireBird 的动态链接库。对于嵌入式版本来说，下载后把压缩包中的 fbembed.dll, firebird.conf,firebird.msg,ib_util.dll 以及 intl 文件夹解压到你的程序目录中就可以运行了。对于服务器版，只要安装客户端并把 fbclient.dll 放到程序目录里就行(当然权限也要有啦)。
上例中使用 IBPP 的步骤是：
1.用工厂函数 IBPP::DatabaseFactory 生成 IBPP::Database 实例，然后调用它的 Connect()成员变量连接数据库。
2.如果要建立数据库的话，使用 Database::Create(int dialect)。其中 dialect 一般取值为 3，它的含义如下：





使用工厂函数 IBPP::TransactionFactory 生成IBPP::Transaction 实例，然后用它的 Start()开始事务，最后用 Commit()来提交事务或 Rollback()撤销事务。
使用工厂函数IBPP::StatementFactory 生成IBPP::Statement 实例，然后用它的Execute()来执行 SQL 命令。
值得注意的是，IBPP 中的 Database,Transaction,Statement 等由工厂函数返回的数据都是智能指针类型，它们能够在退出有效域 时自动执行一些保证完整性的操作。（比如我们使用 tr->Start()后即便不调用 tr->Commit()，在超出 tr 变量作用域后系统 也会自动调用 Commit 的）
#include <iostream>
#include <algorithm>
#include <assert.h>
#include <ibpp.h>
using namespace std; 6.
//数据库名，根据你的情况更改。
const char* g_szDBName = "d:\\test.fdb";
//服务器名，针对服务器版本。对于嵌入式，它应该是""
const char* g_szServerName = ""; 11.
//这里的用户名和密码是 FireBird 默认值，对于服务器版，用你自己的密码
//对于嵌入式，就是这个（FireBird 嵌入式版没有加密功能）。
const char*	g_szUserName = "SYSDBA";
const char* g_szPassword = "masterkey"; 16.
int main()
{
IBPP::Database db;
db = IBPP::DatabaseFactory(g_szServerName,
g_szDBName,
g_szUserName,
g_szPassword);
//连接数据库
db->Connect();

IBPP::Transaction tr = IBPP::TransactionFactory(db);
tr->Start();
try{
IBPP::Statement st = IBPP::StatementFactory(db,tr);
//建立数据表
st->ExecuteImmediate(
"CREATE TABLE BlobTable("
"	ID INTEGER NOT NULL,"
"	RES BLOB)"
);
//生成器
st->ExecuteImmediate(
"CREATE GENERATOR BlobTable_ID_Gen"
);
//触发器
st->ExecuteImmediate(
"CREATE TRIGGER BlobTable_BI_ID FOR BlobTable "
"ACTIVE BEFORE INSERT POSITION 0 "
"AS "
"BEGIN "
"	IF(NEW.ID IS NULL) THEN "
"	NEW.ID = GEN_ID(BlobTable_ID_Gen, 1); "
"END"
	);
	tr->CommitRetain();

st->Prepare("INSERT INTO BlobTable(RES) VALUES(?)");
assert(1 == st->Parameters());
//插入Blob，一块 100 字节的内存
{
IBPP::Blob res = IBPP::BlobFactory(db,tr);
res->Create();
char buf[100];
for(int i=0; i<100; i++) buf[i]=i;
res->Write(buf,sizeof(buf));
res->Close();
		st->Set(1,res); //第一个问号
		st->Execute();
	}	
//插入Blob，一串字符串
{
st->Set(1,string("HAHAHAHAHAHA..."));
st->Execute();
	}
	tr->Commit();
	}
catch(IBPP::SQLException &e){
cerr << e.what() << endl;
tr->Rollback();
	}

tr->Start();
try{
IBPP::Statement st = IBPP::StatementFactory(db,tr);
st->Execute("SELECT * FROM BlobTable");
//显示SELECT 得到的数据
while(st->Fetch())
{
int id;
IBPP::Blob res = IBPP::BlobFactory(db,tr); 87.
st->Get(1,id);
st->Get(2,res);

//显示 Blob 内容
res->Open();
int size;
res->Info(&size,NULL,NULL);
cout << id << " size is " << size << endl;
char c;
while(res->Read(&c,1))
{
cout << (int)c << ' ';
	}
	cout << endl;
	}
	tr->Commit();
	}
catch(IBPP::SQLException &e){
cerr << e.what() << endl;
tr->Rollback();
	}
	}


本例连接例一建立的数据库，然后新建一个名为 BlobTable 的表，里面只有 ID 和 RES 字段，而且还为 ID 字段建立了一个生成器和触发器，这样就可以 让 FireBird 自动为 ID 赋值了，具体内容可以看 FireBird 教程。与例一不同的是这里使用了 IBPP::Statement 的 ExecuteImmediate()方法，它立即执行其中的 SQL 语句，而 Execute()则有一个先准备、再执行的过程，对于只执行一次的 SQL， 可以使用 ExecuteImmediate()，如果执行次数较多，则建议使用 Execute()。
RES 字段是一个 Blob 型字段，从本例可以看出给 Blob 字段赋值的方法是：
1.用 IBPP::Statement 的 Prepare()方法准备 SQL 插入语句，其中的 Blob 字段使用问号？代替。
2.用工厂函数 IBPP::BlobFactory 生成一个 IBPP::Blob 实例，然后分别调用它的 Create(),Write()和 Close()输入数据，最后用 IBPP::Statement 的 Set()方法写入。
3.所有数据准备就绪后，执行 IBPP::Statement 的 Execute()方法。
如果只想往 Blob 中存放文本数据，也可以直接用 IBPP::Statement 的 Set()方法写入 std::string 类型。
从 Blob 字段取值的方法正好相反：
1.用 IBPP::Statement 的 Execute()方法或 ExecuteImmediate()方法执行 SELECT 语句。
2.使用 IBPP::Statement 的 Fetch()方法提取出当前行的数据。

3.使用 IBPP::Statement 的 Get()方法取出指定列的数据，如果是 Blob 类型，则使用 IBPP::Blob 实例作为输入。
4.最后分别调用 IBPP::Blob 的 Open(),Read()和 Close()方法取出数据（IBPP::Blob 也是一个智能指针类型，所以上例中没有显式地使用 Close()关闭）。