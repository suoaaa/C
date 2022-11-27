#ifndef Class_h
#define Class_h

#include "acllib.h"
#include <time.h>
#include <math.h>

#define V_user 50       //定义玩家移动速度为50
#define V_enemy 40      //定义敌对移动速度为40
#define V_remote 60     //定义远程攻击的移动速度为60
#define Winwidth 1200   //窗口宽
#define Winhigh 800     //窗口高
#define picwidth 50     //角色及敌人贴图宽
#define pichigh 50      //角色及敌人贴图高
#define arrow_width 32  //敌人2类射出弓箭贴图的宽
#define arrow_high 18   //敌人2类射出弓箭贴图的高
#define fire_width 20   //玩家发射火球贴图的宽
#define fire_high 20    //玩家发射火球贴图的高
#define n_enemy1 5      //敌人1的最大数量  
#define n_enemy2 3      //敌人2的最大数量
#define n_enemy3 6      //敌人3的最大数量
#define n_remote 5      //火球及箭的各自最大在场数量
#define CD 5            //设置远程攻击cd

void timerEvent(int id);            //计时器
void keyEvent(int key, int event);  //键盘输入
void mouseEvent(int key);           //鼠标输入

class Base;         //角色（玩家及敌人）基础类
class User;         //玩家类
class enemy1;       //敌人1类（近战）
class enemy2;       //敌人2类（远程）相比敌人1多了远程攻击手段
class enemy3;       //敌人3类，无攻击手段，击杀或者被玩家抓住可获得得分
class remote;       //玩家和敌人2类远程攻击的基础类
class fire;         //玩家远程手段：火球
class arrow;        //敌人2类远程手段：弓箭

class Base              //角色（玩家及敌人）基础类
{
protected:
    int score;      //对于敌人：击杀获得分数  对于玩家：当前获得分数
    ACL_Image *img; //图片指针
    int x,y;        //当前位置
    int dx,dy;      //将要向下一个位置的差
public:
    bool health;    //判断是否死亡，死亡为假，存活为真
    Base(){};
    ~Base(){};
    int getscore(){return score;};  //展示分数
    virtual void move()=0;          //移动函数
    virtual void collide()=0;       //碰撞（攻击判定以及获得分数）
                                    //需要注意只有敌人类以及敌人远程攻击arrow类有碰撞判定
    int getx(){return x;};
    int gety(){return y;};
};

class User:public Base//玩家类
{
protected:
    int cd_hit;             //人物远程攻击技能cd
    int cd_skill;           //人物角色技能cd
public:
    User(ACL_Image *picture)
        {img=picture;score=0;cd_hit=0;cd_skill=0;x=Winwidth/2;y=Winhigh/2;skill=0;health=1;dx=V_user;dy=V_user;for(int i=0;i<n_remote;i++)f[i]=NULL;};
    ~User(){img=NULL;score=0;cd_hit=0;cd_skill=0;x=0;y=0;dx=0;dy=0;health=0;};
    fire *(f[n_remote]);    //发射出的火球
    bool skill;             //技能：暂时无敌2s，免疫弓箭手（敌人2）及近战（敌人1）的攻击,skill为真则发动技能，为假未发动技能
    void hit(int desx,int desy,ACL_Image *fire_p);//攻击，远程
    void move(int key);     
    void collide(){};       
    //以下四个类需要玩家数据使用collide（碰撞）函数判断是否攻击到
    friend enemy1;
    friend enemy2;
    friend enemy3;
    friend arrow;
    friend fire;
};

class enemy1:public Base//敌人1类（近战）
{
protected:
    int cd_change;      //改变方向的cd,默认是2s改变一次（cd_change=4）
public:
    enemy1(ACL_Image *picture,User *usr)
    {   
        health=1;
        cd_change=4;
        srand((unsigned)time(NULL));
        img=picture;score=2;
        x=rand()%Winwidth ;
        y=rand()%Winhigh;
        while(x<usr->getx()+2*picwidth||x>usr->getx()-2*picwidth)
            x=rand()%Winwidth;
        while(y<usr->gety()+2*pichigh||y>usr->gety()-2*pichigh)
            y=rand()%Winhigh;
        int ddx=usr->getx()-x;
        int ddy=usr->gety()-y;
        double d=ddy*ddy+ddx*ddx;
        dx=V_enemy*ddx/sqrt(d);
        dy=int(sqrt(V_enemy*V_enemy-dx*dx));
    };
    ~enemy1(){img=NULL;score=0;x=0;y=0;dx=0;dy=0;health=0;};
    void collide(User* user,fire **f); 
    void move(User *usr);
    void change(User *usr); //改变方向来追踪玩家当前位置
};
class enemy2:public Base    //敌人2类（远程）相比敌人1多了远程攻击手段
{
protected:
    int cd_hit;             //攻击cd
public:
    arrow *(arr[n_remote]); //发出的弓箭
    enemy2(ACL_Image *picture,User *usr)
    {   
        health=1;
        srand((unsigned)time(NULL));
        img=picture;score=3;
        cd_hit=0;
        x=rand()%Winwidth ;
        y=rand()%Winhigh;
        while(x<usr->getx()+3*picwidth||x>usr->getx()-3*picwidth)
            x=rand()%Winwidth;
        while(y<usr->gety()+3*pichigh||y>usr->gety()-3*pichigh)
            y=rand()%Winhigh;
        dx=rand()%(V_enemy-1)+1;
        dy=int(sqrt(V_enemy*V_enemy-dx*dx));
    };
    ~enemy2(){img=NULL;score=0;x=0;y=0;dx=0;dy=0;cd_hit=0;health=0;};
    void collide(User* user,fire **f);
    void move(User *usr,ACL_Image *img);
    void hit(ACL_Image *img,User *usr);
    friend fire;
};

class enemy3:public Base    //敌人3类，无攻击手段，击杀或者被玩家抓住可获得得分
{
public:
    enemy3(ACL_Image *picture,User *usr)
    {   
        health=1;
        srand((unsigned)time(NULL));
        img=picture;score=4;
        x=rand()%Winwidth;
        y=rand()%Winhigh;
        dx=rand()%(V_enemy-1)+1;
        dy=int(sqrt(V_enemy*V_enemy-dx*dx));
    };
    ~enemy3(){img=NULL;score=0;x=0;y=0;dx=0;dy=0;health=0;};
    void collide(User* usr,fire **f);
    void move(User* usr,fire **f);
};

class remote//玩家以及enemy2（远程）的远程攻击类
{
protected:
    int x,y;//攻击当前所在位置
    int dx,dy;//攻击下一位置与当前位置之差
    ACL_Image *img;//远程攻击模型
public:
    bool exist;//判断是否存在，存在为真（击中目标或者飞出窗口视为不存在）
    remote(){};
    ~remote(){};
};

class arrow:public remote
{
public:
    arrow(ACL_Image *picture,int xx,int yy,User *usr)
    {
        exist=1;
        img=picture;
        x=xx;
        y=yy;
        int ddx=usr->getx()-x;
        int ddy=usr->gety()-y;
        double d=ddy*ddy+ddx*ddx;
        dx=V_enemy*ddx/sqrt(d);
        dy=int(sqrt(V_enemy*V_enemy-dx*dx));
    }
    ~arrow()
    {
        x=0;y=0;dx=0;dy=0;img=0;exist=0;
    }
    void collide(User* usr);
    void move(User* usr);
    
};

class fire:public remote
{
public:
    int desx;int desy;//攻击方位
    fire(){}
    fire(ACL_Image *picture,User *usr,int desx,int desy)
    {
        img=picture;
        x=usr->getx();
        y=usr->gety();
        int ddx=desx-x;
        int ddy=desy-y;
        double d=ddy*ddy+ddx*ddx;
        dx=V_remote*ddx/sqrt(d);
        dy=int(sqrt(V_remote*V_remote-dx*dx));
    }
    ~fire()
    {
        x=0;y=0;dx=0;dy=0;
    }
    void collide(){};
    void move();
};

#endif