#ifndef Class_h
#define Class_h

#include "acllib.h"
#include <time.h>
#include <math.h>
#include <stdlib.h>

#define V_user      6       //��������ƶ��ٶȣ�Ĭ��6��
#define V_enemy     5       //����ж��ƶ��ٶȣ�Ĭ��5��
#define V_remote    15      //����Զ�̹������ƶ��ٶȣ�Ĭ��15��
#define pica        65      //��ɫ��������ͼ�����
#define fire_a      30      //��ҷ��������ͼ�ĸ����
#define arrow_width 48      //����2�����������ͼ�Ŀ�
#define arrow_high  48      //����2�����������ͼ�ĸ�
#define n_remote    5       //���򼰼��ĸ�������ڳ�������Ĭ��5��
#define CD          30      //�������Զ�̹���cdΪ1.5s��0.05sΪһ����λʱ�䣩
#define CD_arrow    90      //����ÿ������2Զ�̹���cdΪ4.5s��0.05sΪһ����λʱ�䣩
#define CD_skill    120     //���ý�ɫ����cdΪ6s��0.05sΪһ����λʱ�䣩
#define CD_inskill  40      //���ý�ɫ���ܳ���ʱ��2s


class Base;         //��ɫ����Ҽ����ˣ�������
class User;         //�����
class enemy1;       //����1�ࣨ��ս��
class enemy2;       //����2�ࣨԶ�̣���ȵ���1����Զ�̹����ֶ�
class enemy3;       //����3�࣬�޹����ֶΣ���ɱ���߱����ץס�ɻ�õ÷�
class remote;       //��Һ͵���2��Զ�̹����Ļ�����
class fire;         //���Զ���ֶΣ�����
class arrow;        //����2��Զ���ֶΣ�����

static int CWinwidth[3]={960,1280,1600};
static int CWinhigh[3]={540,720,900};

class Base          //��ɫ����Ҽ����ˣ�������
{
protected:
    int score;      //���ڵ��ˣ���ɱ��÷���  ������ң���ǰ��÷���
    float x,y;      //��ǰλ��
    float dx,dy;    //��Ҫ����һ��λ�õĲ�
public:
ACL_Image *img;     //ͼƬָ��
    bool health;    //�ж��Ƿ�����������Ϊ�٣����Ϊ��
    remote *(rem[n_remote]);//���������Ϊ���򣬵���2��Ϊ����
    Base(){};
    ~Base(){};
    int getscore(){return score;};  //չʾ����
    virtual void collide(User *usr,remote **re)=0;       //��ײ�������ж��Լ���÷�����
                                  //��Ҫע��ֻ�е������Լ�����Զ�̹���arrow������ײ�ж�
    int getx(){return x;};
    int gety(){return y;};
};

class User:public Base//�����
{
public:
    User(ACL_Image *picture,int *config_code)
        {img=picture;score=0;cd_hit=0;cd_skill=0;x=CWinwidth[config_code[1]]/2;y=CWinhigh[config_code[1]]/2;skill=CD_inskill;health=1;dx=V_user;dy=V_user;for(int i=0;i<n_remote;i++)rem[i]=NULL;};
    ~User(){img=NULL;score=0;cd_hit=0;cd_skill=0;x=0;y=0;dx=0;dy=0;health=0;};
    int skill;              //���ܣ���ʱ�޵�2s�����߹����֣�����2������ս������1���Ĺ���,skillΪʣ��ʱ�䣬Ϊ0��ʾδ�ڼ���״̬    
    int cd_skill;           //�����ɫ����cd    
    int cd_hit;             //����Զ�̹�������cd
    void hit(int desx,int desy,ACL_Image *fire_img);//������Զ��
    void move(int key,int *config_code);     
    void collide(User *usr,remote **re){};
    //�����ĸ�����Ҫ�������ʹ��collide����ײ�������ж��Ƿ񹥻���
    friend enemy1;
    friend enemy2;
    friend enemy3;
    friend arrow;
    friend fire;
};

class enemy1:public Base//����1�ࣨ��ս��
{
public:
    enemy1(ACL_Image *picture,User *usr,int *config_code)
    {   
        health=1;
        img=picture;score=2;
        x=rand()%(CWinwidth[config_code[1]]-2*pica)+pica;
        y=rand()%(CWinhigh[config_code[1]]-2*pica)+pica;
        while(x<usr->getx()+2*pica&&x>usr->getx()-2*pica)
            x=rand()%(CWinwidth[config_code[1]]-2*pica)+pica;
        while(y<usr->gety()+2*pica&&y>usr->gety()-2*pica)
            y=rand()%(CWinhigh[config_code[1]]-2*pica)+pica;
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
    void change(User *usr); //�ı䷽����׷����ҵ�ǰλ��
};
class enemy2:public Base    //����2�ࣨԶ�̣���ȵ���1����Զ�̹����ֶ�
{
protected:
    int cd_hit;             //����cd
public:
    enemy2(ACL_Image *picture,User *usr,int *config_code)
    {   
        health=1;
        for(int i=0;i<n_remote;i++)
            rem[i]=NULL;
        img=picture;score=3;
        cd_hit=90;
        x=rand()%(CWinwidth[config_code[1]]-2*pica)+pica;
        y=rand()%(CWinhigh[config_code[1]]-2*pica)+pica;
        while(x<usr->getx()+3*pica&&x>usr->getx()-3*pica)
            x=rand()%(CWinwidth[config_code[1]]-2*pica)+pica;
        while(y<usr->gety()+3*pica&&y>usr->gety()-3*pica)
            y=rand()%(CWinhigh[config_code[1]]-2*pica)+pica;
        dx=rand()%(V_enemy-1)+1;
        dy=int(sqrt(V_enemy*V_enemy-dx*dx));
    };
    ~enemy2(){img=NULL;score=0;x=0;y=0;dx=0;dy=0;cd_hit=0;health=0;};
    void hit(ACL_Image *img,User *usr);
    void collide(User* user,remote **re);
    void move(User *usr,remote **re,ACL_Image *arrimg,int *config_code);
    friend fire;
};

class enemy3:public Base    //����3�࣬�޹����ֶΣ���ɱ���߱����ץס�ɻ�õ÷�
{
public:
    enemy3(ACL_Image *picture,int *config_code)
    {
        health=1;
        img=picture;score=4;
        x=rand()%(CWinwidth[config_code[1]]-2*pica)+pica;
        y=rand()%(CWinhigh[config_code[1]]-2*pica)+pica;
        dx=rand()%(V_enemy-1)+1;
        dy=int(sqrt(V_enemy*V_enemy-dx*dx));
    };
    ~enemy3(){img=NULL;score=0;x=0;y=0;dx=0;dy=0;health=0;};
    void collide(User* usr,remote **re);
    void move(User* usr,remote **re,int *config_code);
};

class remote//����Լ�enemy2��Զ�̣���Զ�̹�����
{
protected:
    float x,y;//������ǰ����λ��
    float dx,dy;//������һλ���뵱ǰλ��֮��
public:
    ACL_Image *img;//Զ�̹���ģ��
    int exist;//�ж��Ƿ���ڣ�����Ϊ�棨����Ŀ����߷ɳ�������Ϊ�����ڣ�(��ҵĻ���ɻ�����������)
    remote(){};
    ~remote(){img=NULL;};
    int getx(){return x;};
    int gety(){return y;};
    virtual void collide(User* usr)=0;
    virtual void move(User *usr,int *config_code)=0;
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
    void move(User* usr,int *config_code);
    
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
    void move(User *usr,int *config_code);
    void collide(User* usr){};
};

#endif