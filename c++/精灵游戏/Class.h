#ifndef Class_h
#define Class_h

#include "acllib.h"
#include <time.h>
#include <math.h>

#define V_user 50       //定义玩家移动速度为50
#define V_enemy 40      //定义敌对移动速度为40
#define V_remote 60     //定义远程攻击速度为60
#define Winwidth 1200   //窗口宽
#define Winhigh 800     //窗口高
#define picwidth 50     //贴图宽
#define pichigh 50      //贴图高

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
    int score;//对于敌人：击杀获得分数  对于玩家：当前获得分数
    ACL_Image *img;//图片指针
    int x,y;//当前位置
    int dx,dy;//将要向下一个位置的差
public:
    Base(){};
    ~Base(){};
    int getscore(){return score;};//展示分数
    virtual void move()=0;//移动函数
    virtual void collide()=0;//碰撞（近战攻击以及获得分数）
    int getx(){return x;};
    int gety(){return y;};
};

class User:public Base//玩家类
{
protected:
    int cd_hit;//人物远程攻击技能cd
    int cd_skill;//人物角色技能cd
    bool skill;//技能：暂时无敌2s，免疫弓箭手（敌人2）及近战（敌人1）的攻击,skill为真则发动技能，为假未发动技能
public:
    User(ACL_Image *picture){img=picture;score=0;cd_hit=0;cd_skill=0;x=600;y=400;dx=V_user;dy=V_user;};
    ~User(){img=NULL;score=0;cd_hit=0;cd_skill=0;x=0;y=0;dx=0;dy=0;};
    void hit();//攻击，远程
    void move(int key);
    void collide();
    //以下三个类需要玩家数据使用collide（碰撞）函数判断是否攻击到
    friend enemy1;
    friend enemy2;
    friend arrow;
    friend fire;
};

class enemy1:public Base//敌人1类（近战）
{
public:
    enemy1(ACL_Image *picture,User *usr)
    {   
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
    ~enemy1(){img=NULL;score=0;x=0;y=0;dx=0;dy=0;};
    void collide(); 
    void move();
};
class enemy2:public Base//敌人2类（远程）相比敌人1多了远程攻击手段
{
protected:
    int cd_hit;
public:
   enemy2(ACL_Image *picture,User *usr)
    {   
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
    ~enemy2(){img=NULL;score=0;x=0;y=0;dx=0;dy=0;cd_hit=0;};
    void collide();
    void move();
    friend fire;
};

class enemy3:public Base
{
public:
    enemy3(ACL_Image *picture,User *usr)
    {   
        srand((unsigned)time(NULL));
        img=picture;score=4;
        x=rand()%Winwidth;
        y=rand()%Winhigh;
        dx=rand()%(V_enemy-1)+1;
        dy=int(sqrt(V_enemy*V_enemy-dx*dx));
    };
    ~enemy3(){img=NULL;score=0;x=0;y=0;dx=0;dy=0;};
    void collide();
    void move();
};

class remote//玩家以及enemy2（远程）的远程攻击类
{
protected:
    int x,y;//攻击当前所在位置
    int dx,dy;//攻击下一位置与当前位置之差
    ACL_Image *img;//远程攻击模型
public:
    remote(){};
    ~remote(){};
    virtual void collide()=0;//碰撞，即攻击判定
    virtual void move()=0;
};
class arrow:public remote
{
    arrow(ACL_Image *picture,enemy2 *enemy,User *usr)
    {
        img=picture;
        x=enemy->getx();
        y=enemy->gety();
        int ddx=usr->getx()-x;
        int ddy=usr->gety()-y;
        double d=ddy*ddy+ddx*ddx;
        dx=V_enemy*ddx/sqrt(d);
        dy=int(sqrt(V_enemy*V_enemy-dx*dx));
    }
    ~arrow()
    {
        x=0;y=0;dx=0;dy=0;img=0;
    }
    void collide();
    void move();
};

class fire:public remote
{
public:
    int desx;int desy;//攻击方位
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
    void collide();
    void move();
};


#endif