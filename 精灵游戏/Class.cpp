#include "Class.h"

void User::move(int key,int *config_code)
{
	switch (key)
	{
	case VK_UP:			y -= dx;	if (y < 0) y = 0;	break;
	case VK_DOWN:		y += dy;	if (y >(CWinhigh[config_code[1]] - pica)) y = (CWinhigh[config_code[1]] - pica);	break;
	case VK_LEFT:		x -= dx;	if (x < 0) x = 0;	break;
	case VK_RIGHT:		x += dx;	if (x >(CWinwidth[config_code[1]] - pica)) x = (CWinwidth[config_code[1]] - pica);	break;
	case VK_SPACE:		if(cd_skill<=0)	{cd_skill=CD_skill;	skill=CD_inskill;}	break;		//空格触发无敌技能		
	//以下为WSAD实现
	case 0x57:			y -= dx;	if (y < 0) y = 0;	break;
	case 0x53:			y += dy;	if (y >(CWinhigh[config_code[1]] - pica)) y = (CWinhigh[config_code[1]] - pica);	break;
	case 0x41:			x -= dx;	if (x < 0) x = 0;	break;
	case 0x44:			x += dx;	if (x >(CWinwidth[config_code[1]] - pica)) x = (CWinwidth[config_code[1]] - pica);	break;
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
	if(usr->skill<=0)
	{
		x_=(x+pica/5*3>=usr->x&&x<usr->x)||(x-pica/5*3<=usr->x&&x>usr->x);
		y_=(y+pica/5*3>=usr->y&&y<usr->y)||(y-pica/5*3<=usr->y&&y>usr->y);
	}
	if(x_&&y_)	usr->health=0;
	else	for(int i=0;i<n_remote;i++)
				{
					if(re[i]!=NULL)
					{x_=(x+(fire_a+pica)/2>=re[i]->getx()&&x<re[i]->getx())||(x-(fire_a+pica)/2<=re[i]->getx()&&x>re[i]->getx());
					y_=(y+(fire_a+pica)/2>=re[i]->gety()&&y<re[i]->gety())||(y-(fire_a+pica)/2<=re[i]->gety()&&y>re[i]->gety());
					if(x_&&y_&&re[i]->exist>0)	{health=0;re[i]->exist--;usr->score+=score;break;}}
				}
};
void enemy1::change(User *usr)
{
	int ddx=usr->x-x;
    int ddy=usr->y-y;
    double d=ddy*ddy+ddx*ddx;
    dx=V_enemy*ddx/sqrt(d);
    dy=int(sqrt(V_enemy*V_enemy-dx*dx));
    if(usr->y<y)   dy*=-1;
};
void enemy1::move(User *usr,remote **re)
{
	x+=dx;y+=dy;
	change(usr);
	collide(usr,re);
};

void enemy2::collide(User* usr,remote **re)
{
	bool x_=0;bool y_=0;
	if(usr->skill<=0)
	{
		x_=(x+pica/5*3>=usr->x&&x<usr->x)||(x-pica/5*3<=usr->x&&x>usr->x);
		y_=(y+pica/5*3>=usr->y&&y<usr->y)||(y-pica/5*3<=usr->y&&y>usr->y);
	}
	if(x_&&y_)	usr->health=0;
	else	for(int i=0;i<n_remote;i++)
				{
					if(re[i]!=NULL)
					{x_=(x+(fire_a+pica)/2>=re[i]->getx()&&x<re[i]->getx())||(x-(fire_a+pica)/2<=re[i]->getx()&&x>re[i]->getx());
					y_=(y+(fire_a+pica)/2>=re[i]->gety()&&y<re[i]->gety())||(y-(fire_a+pica)/2<=re[i]->gety()&&y>re[i]->gety());
					if(x_&&y_&&re[i]->exist>0)	{health=0;re[i]->exist--;usr->score+=score;break;}}
				}
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

void enemy3::collide(User* usr,remote **re)
{
	bool x_, y_;
	x_=(x+pica/5*3>=usr->x&&x<usr->x)||(x-pica/5*3<=usr->x&&x>usr->x);
	y_=(y+pica/5*3>=usr->y&&y<usr->y)||(y-pica/5*3<=usr->y&&y>usr->y);
	if(x_&&y_)	{usr->score+=score;health=0;}
	else	for(int i=0;i<n_remote;i++)
				{
					if(re[i]!=NULL)
					{x_=(x+(fire_a+pica)/2>=re[i]->getx()&&x<re[i]->getx())||(x-(fire_a+pica)/2<=re[i]->getx()&&x>re[i]->getx());
					y_=(y+(fire_a+pica)/2>=re[i]->gety()&&y<re[i]->gety())||(y-(fire_a+pica)/2<=re[i]->gety()&&y>re[i]->gety());
					if(x_&&y_&&re[i]->exist>0)	{health=0;re[i]->exist--;usr->score+=score;break;}}
				}
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
		bool x_=(x+pica/2>=usr->x&&x<usr->x)||(x-pica/2<=usr->x&&x>usr->x);
		bool y_=(y+arrow_high/2>=usr->y&&y<usr->y)||(y-arrow_high/2<=usr->y&&y>usr->y);
		if(x_&&y_)	{usr->health=0;exist=0;}
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