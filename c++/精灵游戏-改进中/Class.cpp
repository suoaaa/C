#include "Class.h"

void User::move(int key)
{
	switch (key)
	{
	case VK_UP:			y -= dx;	if (y < 0) y = 0;	break;
	case VK_DOWN:		y += dy;	if (y >(Winhigh - pica)) y = (Winhigh - pica);	break;
	case VK_LEFT:		x -= dx;	if (x < 0) x = 0;	break;
	case VK_RIGHT:		x += dx;	if (x >(Winwidth - pica)) x = (Winwidth - pica);	break;
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

void enemy1::collide(User* usr,remote **re)
{
	bool x_=0;bool y_=0;
	if(!usr->skill)
	{
		x_=(x+pica/2>=usr->getx()&&x<usr->getx())||(x-pica/2<=usr->getx()&&x>usr->getx());
		y_=(y+pica/2>=usr->gety()&&y<usr->gety())||(y-pica/2<=usr->gety()&&y>usr->gety());
	}
	if(x_&&y_)	usr->health=0;
	else	for(int i=0;i<n_remote;i++)
				{
					if(re[i]!=NULL)
					{x_=(x+(fire_a+pica)/2>=re[i]->getx()&&x<re[i]->getx())||(x-(fire_a+pica)/2<=re[i]->getx()&&x>re[i]->getx());
					y_=(y+(fire_a+pica)/2>=re[i]->gety()&&y<re[i]->gety())||(y-(fire_a+pica)/2<=re[i]->gety()&&y>re[i]->gety());
					if(x_&&y_)	{health=0;re[i]->exist--;usr->score+=score;break;}}
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
	if(x>=Winwidth||x<=0)	{dx=-dx;}
	if(y>=Winhigh||y<=0)	{dy=-dy;}
	change(usr);
	collide(usr,re);
};

void enemy2::collide(User* usr,remote **re)
{
	bool x_=0;bool y_=0;
	if(!usr->skill)
	{
		x_=(x+pica/2>=usr->getx()&&x<usr->getx())||(x-pica/2<=usr->getx()&&x>usr->getx());
		y_=(y+pica/2>=usr->gety()&&y<usr->gety())||(y-pica/2<=usr->gety()&&y>usr->gety());
	}
	if(x_&&y_)	usr->health=0;
	else	for(int i=0;i<n_remote;i++)
				{
					if(re[i]!=NULL)
					{x_=(x+(fire_a+pica)/2>=re[i]->getx()&&x<re[i]->getx())||(x-(fire_a+pica)/2<=re[i]->getx()&&x>re[i]->getx());
					y_=(y+(fire_a+pica)/2>=re[i]->gety()&&y<re[i]->gety())||(y-(fire_a+pica)/2<=re[i]->gety()&&y>re[i]->gety());
					if(x_&&y_)	{health=0;re[i]->exist--;usr->score+=score;break;}}
				}
};
void enemy2::hit(ACL_Image *arrimg,User *usr)
{
	cd_hit=CD_arrow;
	int i=0;
	while(i<n_remote&&rem[i]!=NULL)	{i++;}
	if(i<n_remote)	{if(rem[i]==NULL)	rem[i]=new arrow(arrimg,x,y,usr);}
};
void enemy2::move(User *usr,remote **re,ACL_Image *arrimg)
{
	cd_hit--;
	x+=dx;y+=dy;
	if(x>=Winwidth-pica||x<=0)	{x-=dx;dx=-dx;}
	if(y>=Winhigh-pica||y<=0)	{y-=dy;dy=-dy;}
	collide(usr,re);
	if(cd_hit<=0&&health>0) hit(arrimg,usr);
};

void enemy3::collide(User* usr,remote **re)
{
	bool x_=(x+pica/2>=usr->getx()&&x<usr->getx())||(x-pica/2<=usr->getx()&&x>usr->getx());
	bool y_=(y+pica/2>=usr->gety()&&y<usr->gety())||(y-pica/2<=usr->gety()&&y>usr->gety());
	if(x_&&y_)	{usr->score+=score;health=0;}
	else	for(int i=0;i<n_remote;i++)
				{
					if(re[i]!=NULL)
					{x_=(x+(fire_a+pica)/2>=re[i]->getx()&&x<re[i]->getx())||(x-(fire_a+pica)/2<=re[i]->getx()&&x>re[i]->getx());
					y_=(y+(fire_a+pica)/2>=re[i]->gety()&&y<re[i]->gety())||(y-(fire_a+pica)/2<=re[i]->gety()&&y>re[i]->gety());
					if(x_&&y_)	{health=0;re[i]->exist--;usr->score+=score;break;}}
				}
};
void enemy3::move(User* usr,remote **re)
{
	x+=dx;y+=dy;
	if(x>=Winwidth-pica||x<=0)	{dx=-dx;}
	if(y>=Winhigh-pica||y<=0)	{dy=-dy;}
	collide(usr,re);
};

void arrow::collide(User* usr)
{
	if(usr->skill<=0)
	{
		bool x_=(x+pica/2>=usr->getx()&&x<usr->getx())||(x-pica/2<=usr->getx()&&x>usr->getx());
		bool y_=(y+pica/2>=usr->gety()&&y<usr->gety())||(y-pica/2<=usr->gety()&&y>usr->gety());
		if(x_&&y_)	{usr->health=0;exist=0;}
	}
};
void arrow::move(User* usr)
{
	x+=dx;y+=dy;
	if(x>=Winwidth-arrow_width||x<=0||y>=Winhigh-arrow_high||y<=0)	exist=0;
	else collide(usr);
};

void fire::move(User* usr)
{
	x+=dx;y+=dy;
	if(x>=Winwidth-fire_a||x<=0||y>=Winhigh-fire_a||y<=0)	exist--;
};