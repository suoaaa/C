运行环境:
1,软件支持:用户需安装sdk1.4.2,Microsoft office.
2,硬件支持:



环境变量设置:
win9x:  假设sdk1.4.2的安装路径为: c:\j2sdk1.4.2 ,
        用记事本编辑 path=c:\j2sdk1.4.2\bin,
                     set CLASSPATH=.;c:\j2sdk1.4.2\lib\tools.jar 
        另存为1.bat,双激运行.以此类推.

winxp:  右激"我的电脑"-->>高级-->>环境变量,按以上所说的路径编辑path,classpath


数据源ODBC设置：
1,控制面板-> 管理工具->数据源ODBC
2,点激用户DSN选项卡中添加,选择Microsoft Access Driver(*mdb)，设置数据源名:foodmenu,并点激数据库的选择按钮,在数据库名中找到源文件目录下的my.mdb文件，点激确定完成设置.