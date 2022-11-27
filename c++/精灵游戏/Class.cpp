#include "Class.h"

void User::move(int key)
{
	switch (key)
	{
	case VK_UP:			y -= dx;	if (y < 0) y = 0;	break;
	case VK_DOWN:		y += dy;	if (y >(Winhigh - pichigh)) y = (Winhigh - pichigh);	break;
	case VK_LEFT:		x -= dx;	if (x < 0) x = 0;	break;
	case VK_RIGHT:		x += dx;	if (x >(Winwidth - picwidth)) x = (Winwidth - picwidth);	break;
	case VK_SPACE:		//空格触发无敌技能		
				cd_skill=8;	
				skill=true;	
	default:	break;
	}
};
void User::hit(int desx,int desy,ACL_Image *fire_img)//攻击，远程
{	
	if(cd_hit==0) 
	{
		int i=0;
		while(f[i]==NULL) i++;
		cd_hit=4;
		f[i]=new fire(fire_img,this,desx,desy);
	}
};

void enemy1::collide(User* usr,fire **f)
{
	bool x_=0;bool y_=0;
	if(usr->skill)
	{
		x_=(x+picwidth>=usr->getx()&&x<usr->getx())||(x-picwidth<=usr->getx()&&x>usr->getx());
		y_=(y+pichigh>=usr->gety()&&y<usr->gety())||(y-pichigh<=usr->gety()&&y>usr->gety());
	}
	if(x_&&y_)	usr->health=0;
	else	for(int i=0;i<n_remote;i++)
				{
					x_=(x+(fire_width+picwidth)/2>=usr->getx()&&x<usr->getx())||(x-(fire_width+picwidth)/2<=usr->getx()&&x>usr->getx());
					y_=(y+(fire_high+pichigh)/2>=usr->gety()&&y<usr->gety())||(y-(fire_high+pichigh)/2<=usr->gety()&&y>usr->gety());
					if(x_&&y_)	{health=0;f[i]->exist=0;usr->score+=score;break;}
				}
}; 
void enemy1::change(User *usr)
{
	cd_change=CD;
	int ddx=usr->getx()-x;
    int ddy=usr->gety()-y;
    double d=ddy*ddy+ddx*ddx;
    dx=V_enemy*ddx/sqrt(d);
    dy=int(sqrt(V_enemy*V_enemy-dx*dx));
};
void enemy1::move(User *usr)
{
	x+=dx;y+=dy;
	if(x>=Winwidth||x<=0)	{dx=-dx;}
	if(y>=Winhigh||y<=0)	{dy=-dy;}
	cd_change--;
	if(cd_change==0)	change(usr);
};

void enemy2::collide(User* usr,fire **f)
{
	bool x_=0;bool y_=0;
	if(usr->skill)
	{
		x_=(x+picwidth>=usr->getx()&&x<usr->getx())||(x-picwidth<=usr->getx()&&x>usr->getx());
		y_=(y+pichigh>=usr->gety()&&y<usr->gety())||(y-pichigh<=usr->gety()&&y>usr->gety());
	}
	if(x_&&y_)	usr->health=0;
	else	for(int i=0;i<n_remote;i++)
				{
					x_=(x+(fire_width+picwidth)/2>=usr->getx()&&x<usr->getx())||(x-(fire_width+picwidth)/2<=usr->getx()&&x>usr->getx());
					y_=(y+(fire_high+pichigh)/2>=usr->gety()&&y<usr->gety())||(y-(fire_high+pichigh)/2<=usr->gety()&&y>usr->gety());
					if(x_&&y_)	{health=0;usr->score+=score;f[i]->exist=0;break;}
				}
};
void enemy2::hit(ACL_Image *img,User *usr)
{
	if(cd_hit<=0)
		{
			cd_hit=CD;
			for(int i=0;i<n_remote;i++)
			if(arr[i]!=NULL)
			{
				arr[i]=new arrow(img,x,y,usr);
				break;
			}
		}
};
void enemy2::move(User *usr,ACL_Image *img)
{
	cd_hit--;
	x+=dx;y+=dy;
	if(x>=Winwidth||x<=0)	{x-=dx;dx=-dx;}
	if(y>=Winhigh||y<=0)	{y-=dy;dy=-dy;}
	if(cd_hit==0) hit(img,usr);
};

void enemy3::collide(User* usr,fire **f)
{
	bool x_=(x+picwidth>=usr->getx()&&x<usr->getx())||(x-picwidth<=usr->getx()&&x>usr->getx());
	bool y_=(y+pichigh>=usr->gety()&&y<usr->gety())||(y-pichigh<=usr->gety()&&y>usr->gety());
	if(x_&&y_)	{health=0;usr->score+=score;}
	else	for(int i=0;i<n_remote;i++)
				{
					x_=(x+(fire_width+picwidth)/2>=usr->getx()&&x<usr->getx())||(x-(fire_width+picwidth)/2<=usr->getx()&&x>usr->getx());
					y_=(y+(fire_high+pichigh)/2>=usr->gety()&&y<usr->gety())||(y-(fire_high+pichigh)/2<=usr->gety()&&y>usr->gety());
					if(x_&&y_)	{health=0;f[i]->exist=0;usr->score+=score;break;}
				}
};
void enemy3::move(User* usr,fire **f)
{
	x+=dx;y+=dy;
	if(x>=Winwidth||x<=0)	{dx=-dx;}
	if(y>=Winhigh||y<=0)	{dy=-dy;}
	collide(usr,f);
};

void arrow::collide(User* usr)
{
	bool x_=(x+(arrow_width+picwidth)/2>=usr->getx()&&x<usr->getx())||(x-(arrow_width+picwidth)/2<=usr->getx()&&x>usr->getx());
	bool y_=(y+(arrow_high+pichigh)/2>=usr->gety()&&y<usr->gety())||(y-(arrow_high+pichigh)/2<=usr->gety()&&y>usr->gety());
	if(x&&y)	{usr->health=0;exist=0;}	
};
void arrow::move(User* usr)
{
	x+=dx;y+=dy;
	if(x>=Winwidth||x<=0||y>=Winhigh||y<=0)	{x=0;y=0;}
	else collide(usr);
};

void fire::move()
{
	x+=dx;y+=dy;
	if(x>=Winwidth||x<=0||y>=Winhigh||y<=0)	{x=0;y=0;}
};