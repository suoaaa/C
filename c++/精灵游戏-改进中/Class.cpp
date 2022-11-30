#include "Class.h"

void Base::collide(Base *usr,remote **re)//碰撞（攻击判定以及获得分数）,需要注意只有敌人类以及敌人远程攻击arrow类有碰撞判定
{
    double x_=x-usr->getx(),y_=usr->gety();
	double l=x_*x_+y_*y_;
	double s=pica*pica;
	if(s>=l)	{usr->score+=score;health=0;}
	else	for(int i=0;i<n_remote;i++)
			{
				if(re[i]!=NULL)
				{	x_=x-re[i]->getx();y_=y-re[i]->gety();
					l=x_*x_;
					s=(pica+fire_a)*(pica+fire_a)/2;
				if(s>=l)	{health=0;re[i]->exist--;usr->score+=score;break;}}
			}
 };
void User::move(int key,int *config_code)
{
	switch (key)
	{
	case VK_UP:			y -= dx;	if (y < 0) y = 0;	break;
	case VK_DOWN:		y += dy;	if (y >(CWinhigh[config_code[1]] - pica)) y = (CWinhigh[config_code[1]] - pica);	break;
	case VK_LEFT:		x -= dx;	if (x < 0) x = 0;	break;
	case VK_RIGHT:		x += dx;	if (x >(CWinwidth[config_code[1]] - pica)) x = (CWinwidth[config_code[1]] - pica);	break;
	case VK_SPACE:		if(!cd_skill)	cd_skill=CD_skill;	skill=CD_inskill;	break;		//空格触发无敌技能		
	default:	break;
	}
};
void User::hit(int desx,int desy,ACL_Image *fire_img)//攻击，远程
{	
	if(cd_hit<=0)
	{
		int i=0;
		while(rem[i]==NULL) i++;
		cd_hit=4;
		rem[i]=new fire(fire_img,this,desx,desy);
	}
};

void enemy1::change(User *usr)
{
	int ddx=usr->getx()-x;
    int ddy=usr->gety()-y;
    double d=ddy*ddy+ddx*ddx;
    dx=V_enemy*ddx/sqrt(d);
    dy=int(sqrt(V_enemy*V_enemy-dx*dx));
    if(usr->gety()<y)   dy*=-1;
};
void enemy1::move(User *usr,remote **re)
{
	x+=dx;y+=dy;
	change(usr);
	collide(usr,re);
};

void enemy2::hit(ACL_Image *arrimg,User *usr)
{
	cd_hit=CD_arrow;
	int i=0;
	while(i<n_remote&&rem[i]!=NULL)	{i++;}
	if(i<n_remote)	{if(rem[i]==NULL)	rem[i]=new arrow(arrimg,x,y,usr);}
};
void enemy2::move(User *usr,remote **re,ACL_Image *arrimg,int *config_code)
{
	cd_hit--;
	x+=dx;y+=dy;
	if(x>=CWinwidth[config_code[1]]-pica||x<=0)	{x-=dx;dx=-dx;}
	if(y>=CWinhigh[config_code[1]]-pica||y<=0)	{y-=dy;dy=-dy;}
	collide(usr,re);
	if(cd_hit<=0&&health>0) hit(arrimg,usr);
};
void enemy3::move(User* usr,remote **re,int *config_code)
{
	x+=dx;y+=dy;
	if(x>=CWinwidth[config_code[1]]-pica||x<=0)	{dx=-dx;}
	if(y>=CWinhigh[config_code[1]]-pica||y<=0)	{dy=-dy;}
	collide(usr,re);
};

void arrow::collide(User* usr)
{
	if(usr->skill<=0)
	{
		double ddx=x-usr->getx(),ddy=y-usr->gety();
		double l=ddx*ddx+ddy*ddy;
		double s=(pica+arrow_high)*(pica+arrow_high)/8+(pica+arrow_width)*(pica+arrow_width)/4;
		if(s>=l)	{usr->health=0;exist=0;}
	}
};
void arrow::move(User* usr,int *config_code)
{
	x+=dx;y+=dy;
	if(x>=CWinwidth[config_code[1]]-arrow_width||x<=0||y>=CWinhigh[config_code[1]]-arrow_high||y<=0)	exist=0;
	else collide(usr);
};

void fire::move(User* usr,int *config_code)
{
	x+=dx;y+=dy;
	if(x>=CWinwidth[config_code[1]]-fire_a||x<=0||y>=CWinhigh[config_code[1]]-fire_a||y<=0)	exist--;
};