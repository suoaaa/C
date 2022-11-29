#ifndef Class_h
#define Class_h

#include "acllib1.h"
#include <time.h>
#include <math.h>
#include <stdlib.h>

#define V_user 6        //��������ƶ��ٶ�
#define V_enemy 5       //����ж��ƶ��ٶ�
#define V_remote 15     //����Զ�̹������ƶ��ٶ�
#define Winwidth 1600   //���ڿ�
#define Winhigh 900     //���ڸ�
#define picwidth 60     //��ɫ��������ͼ��
#define pichigh 60      //��ɫ��������ͼ��
#define arrow_width 48  //����2�����������ͼ�Ŀ�
#define arrow_high 48   //����2�����������ͼ�ĸ�
#define fire_width 30   //��ҷ��������ͼ�Ŀ�
#define fire_high 30    //��ҷ��������ͼ�ĸ�
#define n_enemy1 4      //����1���������  
#define n_enemy2 2      //����2���������
#define n_enemy3 7      //����3���������
#define n_remote 5      //���򼰼��ĸ�������ڳ�����
#define CD 30           //�������Զ�̹���cdΪ1.5s��0.05sΪһ����λʱ�䣩
#define CD_arrow 90     //����ÿ������2Զ�̹���cdΪ4.5s��0.05sΪһ����λʱ�䣩
#define CD_skill 120    //���ý�ɫ����cdΪ6s��0.05sΪһ����λʱ�䣩
#define CD_inskill 40   //���ý�ɫ���ܳ���ʱ��2s

class Base;         //��ɫ����Ҽ����ˣ�������
class User;         //�����
class enemy1;       //����1�ࣨ��ս��
class enemy2;       //����2�ࣨԶ�̣���ȵ���1����Զ�̹����ֶ�
class enemy3;       //����3�࣬�޹����ֶΣ���ɱ���߱����ץס�ɻ�õ÷�
class remote;       //��Һ͵���2��Զ�̹����Ļ�����
class fire;         //���Զ���ֶΣ�����
class arrow;        //����2��Զ���ֶΣ�����

class Base          //��ɫ����Ҽ����ˣ�������
{
protected:
    int score;      //���ڵ��ˣ���ɱ��÷���  ������ң���ǰ��÷���
    float x,y;        //��ǰλ��
    float dx,dy;      //��Ҫ����һ��λ�õĲ�
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
    User(ACL_Image *picture)
        {img=picture;score=0;cd_hit=0;cd_skill=0;x=Winwidth/2;y=Winhigh/2;skill=CD_inskill;health=1;dx=V_user;dy=V_user;for(int i=0;i<n_remote;i++)rem[i]=NULL;};
    ~User(){img=NULL;score=0;cd_hit=0;cd_skill=0;x=0;y=0;dx=0;dy=0;health=0;};
    //fire *(f[n_remote]);    //������Ļ���
    int skill;              //���ܣ���ʱ�޵�2s�����߹����֣�����2������ս������1���Ĺ���,skillΪʣ��ʱ�䣬Ϊ0��ʾδ�ڼ���״̬    
    int cd_skill;           //�����ɫ����cd    
    int cd_hit;             //����Զ�̹�������cd
    void hit(int desx,int desy,ACL_Image *fire_img);//������Զ��
    void move(int key);     
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
    void change(User *usr); //�ı䷽����׷����ҵ�ǰλ��
};
class enemy2:public Base    //����2�ࣨԶ�̣���ȵ���1����Զ�̹����ֶ�
{
protected:
    int cd_hit;             //����cd
public:
    //arrow *(arr[n_remote]); //�����Ĺ���
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

class enemy3:public Base    //����3�࣬�޹����ֶΣ���ɱ���߱����ץס�ɻ�õ÷�
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