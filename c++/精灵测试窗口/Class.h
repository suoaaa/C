#ifndef Class_h
#define Class_h

#include "acllib1.h"
#include <time.h>
#include <math.h>
#include <stdlib.h>

#define V_user 6        //定义玩家移动速度
#define V_enemy 5       //定义敌对移动速度
#define V_remote 15     //定义远程攻击的移动速度
#define Winwidth 1600   //窗口宽
#define Winhigh 900     //窗口高
#define picwidth 60     //角色及敌人贴图宽
#define pichigh 60      //角色及敌人贴图高
#define arrow_width 48  //敌人2类射出弓箭贴图的宽
#define arrow_high 48   //敌人2类射出弓箭贴图的高
#define fire_width 30   //玩家发射火球贴图的宽
#define fire_high 30    //玩家发射火球贴图的高
#define n_enemy1 4      //敌人1的最大数量  
#define n_enemy2 2      //敌人2的最大数量
#define n_enemy3 7      //敌人3的最大数量
#define n_remote 5      //火球及箭的各自最大在场数量
#define CD 30           //设置玩家远程攻击cd为1.5s（0.05s为一个单位时间）
#define CD_arrow 90     //设置每个敌人2远程攻击cd为4.5s（0.05s为一个单位时间）
#define CD_skill 120    //设置角色技能cd为6s（0.05s为一个单位时间）
#define CD_inskill 40   //设置角色技能持续时间2s

class Base;         //角色（玩家及敌人）基础类
class User;         //玩家类
class enemy1;       //敌人1类（近战）
class enemy2;       //敌人2类（远程）相比敌人1多了远程攻击手段
class enemy3;       //敌人3类，无攻击手段，击杀或者被玩家抓住可获得得分
class remote;       //玩家和敌人2类远程攻击的基础类
class fire;         //玩家远程手段：火球
class arrow;        //敌人2类远程手段：弓箭

class Base          //角色（玩家及敌人）基础类
{
protected:
    int score;      //对于敌人：击杀获得分数  对于玩家：当前获得分数
    float x,y;        //当前位置
    float dx,dy;      //将要向下一个位置的差
public:
ACL_Image *img;     //图片指针
    bool health;    //判断是否死亡，死亡为假，存活为真
    remote *(rem[n_remote]);//对于玩家类为火球，敌人2类为弓箭
    Base(){};
    ~Base(){};
    int getscore(){return score;};  //展示分数
    virtual void collide(User *usr,remote **re)=0;       //碰撞（攻击判定以及获得分数）
                                  //需要注意只有敌人类以及敌人远程攻击arrow类有碰撞判定
    int getx(){return x;};
    int gety(){return y;};
};

class User:public Base//玩家类
{
public:
    User(ACL_Image *picture)
        {img=picture;score=0;cd_hit=0;cd_skill=0;x=Winwidth/2;y=Winhigh/2;skill=CD_inskill;health=1;dx=V_user;dy=V_user;for(int i=0;i<n_remote;i++)rem[i]=NULL;};
    ~User(){img=NULL;score=0;cd_hit=0;cd_skill=0;x=0;y=0;dx=0;dy=0;health=0;};
    //fire *(f[n_remote]);    //发射出的火球
    int skill;              //技能：暂时无敌2s，免疫弓箭手（敌人2）及近战（敌人1）的攻击,skill为剩余时间，为0表示未在技能状态    
    int cd_skill;           //人物角色技能cd    
    int cd_hit;             //人物远程攻击技能cd
    void hit(int desx,int desy,ACL_Image *fire_img);//攻击，远程
    void move(int key);     
    void collide(User *usr,remote **re){};
    //以下四个类需要玩家数据使用collide（碰撞）函数判断是否攻击到
    friend enemy1;
    friend enemy2;
    friend enemy3;
    friend arrow;
    friend fire;
};

class enemy1:public Base//敌人1类（近战）
{
public:
    enemy1(ACL_Image *picture,User *usr)
    {   
        //srand(time(NULL));
        health=1;
        img=picture;score=2;
        x=rand()%(Winwidth-2*picwidth)+picwidth;
        y=rand()%(Winhigh-2*pichigh)+pichigh;
        while(x<usr->getx()+2*picwidth&&x>usr->getx()-2*picwidth)
            x=rand()%(Winwidth-2*picwidth)+picwidth;
        while(y<usr->gety()+2*pichigh&&y>usr->gety()-2*pichigh)
            y=rand()%(Winhigh-2*pichigh)+pichigh;
        int ddx=usr->getx()-x;
        int ddy=usr->gety()-y;
        double d=ddy*ddy+ddx*ddx;
        dx=V_enemy*ddx/sqrt(d);
        dy=int(sqrt(V_enemy*V_enemy-dx*dx));
        if(usr->gety()<y)   dy*=-1;
    };
    enemy1(){};
    ~enemy1(){img=NULL;score=0;x=0;y=0;dx=0;dy=0;health=0;};
    void move(User *usr,remote **re);
    void collide(User* user,remote **re); 
    void change(User *usr); //改变方向来追踪玩家当前位置
};
class enemy2:public Base    //敌人2类（远程）相比敌人1多了远程攻击手段
{
protected:
    int cd_hit;             //攻击cd
public:
    //arrow *(arr[n_remote]); //发出的弓箭
    enemy2(ACL_Image *picture,User *usr)
    {   
    	//srand(time(NULL));
        health=1;
        for(int i=0;i<n_remote;i++)
            rem[i]=NULL;
        img=picture;score=3;
        cd_hit=90;
        x=rand()%(Winwidth-2*picwidth)+picwidth;
        y=rand()%(Winhigh-2*pichigh)+pichigh;
        while(x<usr->getx()+4*picwidth&&x>usr->getx()-4*picwidth)
            x=rand()%(Winwidth-2*picwidth)+picwidth;
        while(y<usr->gety()+4*pichigh&&y>usr->gety()-4*pichigh)
            y=rand()%(Winhigh-2*pichigh)+pichigh;
        dx=rand()%(V_enemy-1)+1;
        dy=int(sqrt(V_enemy*V_enemy-dx*dx));
    };
    ~enemy2(){img=NULL;score=0;x=0;y=0;dx=0;dy=0;cd_hit=0;health=0;};
    void hit(ACL_Image *img,User *usr);
    void collide(User* user,remote **re);
    void move(User *usr,remote **re,ACL_Image *arrimg);
    friend fire;
};

class enemy3:public Base    //敌人3类，无攻击手段，击杀或者被玩家抓住可获得得分
{
public:
    enemy3(ACL_Image *picture)
    {
    	//srand(time(NULL));
        health=1;
        img=picture;score=4;
        x=rand()%(Winwidth-2*picwidth)+picwidth;
        y=rand()%(Winhigh-2*pichigh)+pichigh;
        dx=rand()%(V_enemy-1)+1;
        dy=int(sqrt(V_enemy*V_enemy-dx*dx));
    };
    ~enemy3(){img=NULL;score=0;x=0;y=0;dx=0;dy=0;health=0;};
    void collide(User* usr,remote **re);
    void move(User* usr,remote **re);
};

class remote//玩家以及enemy2（远程）的远程攻击类
{
protected:
    float x,y;//攻击当前所在位置
    float dx,dy;//攻击下一位置与当前位置之差
public:
    ACL_Image *img;//远程攻击模型
    int exist;//判断是否存在，存在为真（击中目标或者飞出窗口视为不存在）(玩家的火球可击败两名敌人)
    remote(){};
    ~remote(){img=NULL;};
    int getx(){return x;};
    int gety(){return y;};
    virtual void collide(User* usr)=0;
    virtual void move(User *usr)=0;
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
        dx=V_remote*ddx/sqrt(d);
        dy=int(sqrt(V_remote*V_remote-dx*dx));
        if(usr->gety()<y)   dy*=-1;
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
    fire(){}
    fire(ACL_Image *picture,User *usr,int desx,int desy)
    {
        exist=2;
        img=picture;
        x=usr->getx();
        y=usr->gety();
        int ddx=desx-x;
        int ddy=desy-y;
        double d=ddy*ddy+ddx*ddx;
        dx=V_remote*ddx/sqrt(d);
        dy=int(sqrt(V_remote*V_remote-dx*dx));
        if(ddy<0)   dy*=-1;
    }
    ~fire()
    {
        x=0;y=0;dx=0;dy=0;
    }
    void move(User *usr);
    void collide(User* usr){};
};

#endif