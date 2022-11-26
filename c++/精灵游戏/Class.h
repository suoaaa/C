#ifndef Class_h
#define Class_h

#include "acllib.h"
#include <time.h>
#include <math.h>

#define V_user 50       //��������ƶ��ٶ�Ϊ50
#define V_enemy 40      //����ж��ƶ��ٶ�Ϊ40
#define V_remote 60     //����Զ�̹����ٶ�Ϊ60
#define Winwidth 1200   //���ڿ�
#define Winhigh 800     //���ڸ�
#define picwidth 50     //��ͼ��
#define pichigh 50      //��ͼ��

void timerEvent(int id);            //��ʱ��
void keyEvent(int key, int event);  //��������
void mouseEvent(int key);           //�������

class Base;         //��ɫ����Ҽ����ˣ�������
class User;         //�����
class enemy1;       //����1�ࣨ��ս��
class enemy2;       //����2�ࣨԶ�̣���ȵ���1����Զ�̹����ֶ�
class enemy3;       //����3�࣬�޹����ֶΣ���ɱ���߱����ץס�ɻ�õ÷�
class remote;       //��Һ͵���2��Զ�̹����Ļ�����
class fire;         //���Զ���ֶΣ�����
class arrow;        //����2��Զ���ֶΣ�����

class Base              //��ɫ����Ҽ����ˣ�������
{
protected:
    int score;//���ڵ��ˣ���ɱ��÷���  ������ң���ǰ��÷���
    ACL_Image *img;//ͼƬָ��
    int x,y;//��ǰλ��
    int dx,dy;//��Ҫ����һ��λ�õĲ�
public:
    Base(){};
    ~Base(){};
    int getscore(){return score;};//չʾ����
    virtual void move()=0;//�ƶ�����
    virtual void collide()=0;//��ײ����ս�����Լ���÷�����
    int getx(){return x;};
    int gety(){return y;};
};

class User:public Base//�����
{
protected:
    int cd_hit;//����Զ�̹�������cd
    int cd_skill;//�����ɫ����cd
    bool skill;//���ܣ���ʱ�޵�2s�����߹����֣�����2������ս������1���Ĺ���,skillΪ���򷢶����ܣ�Ϊ��δ��������
public:
    User(ACL_Image *picture){img=picture;score=0;cd_hit=0;cd_skill=0;x=600;y=400;dx=V_user;dy=V_user;};
    ~User(){img=NULL;score=0;cd_hit=0;cd_skill=0;x=0;y=0;dx=0;dy=0;};
    void hit();//������Զ��
    void move(int key);
    void collide();
    //������������Ҫ�������ʹ��collide����ײ�������ж��Ƿ񹥻���
    friend enemy1;
    friend enemy2;
    friend arrow;
    friend fire;
};

class enemy1:public Base//����1�ࣨ��ս��
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
class enemy2:public Base//����2�ࣨԶ�̣���ȵ���1����Զ�̹����ֶ�
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

class remote//����Լ�enemy2��Զ�̣���Զ�̹�����
{
protected:
    int x,y;//������ǰ����λ��
    int dx,dy;//������һλ���뵱ǰλ��֮��
    ACL_Image *img;//Զ�̹���ģ��
public:
    remote(){};
    ~remote(){};
    virtual void collide()=0;//��ײ���������ж�
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
    int desx;int desy;//������λ
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