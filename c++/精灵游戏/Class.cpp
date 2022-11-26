#include "Class.h"
//User

    // int cd_hit;//人物远程攻击技能cd
    // int cd_skill;//人物角色技能cd
    // void User::hit();//攻击，远程
void User::move(int key)
{
	switch (key)
	{
	case VK_UP:			y -= dx;	if (y < 0) y = 0;	break;
	case VK_DOWN:		y += dy;	if (y >(Winhigh - pichigh)) y = (Winhigh - pichigh);	break;
	case VK_LEFT:		x -= dx;	if (x < 0) x = 0;	break;
	case VK_RIGHT:		x += dx;	if (x >(Winwidth - picwidth)) x = (Winwidth - picwidth);	break;
	case VK_SPACE://空格触发无敌技能		
				cd_skill=8;	
				skill=true;	
				startTimer(1,1000);	
				// int timerid2 = startTimer(2,3000);
	case VK_LBUTTON:	
		break;
	}
};
void User::collide(){};
void User::hit(){};//攻击，远程

void enemy1::collide(){}; 
void enemy1::move(){};

void enemy2::collide(){};
void enemy2::move(){};

void enemy3::collide(){};
void enemy3::move(){};

void arrow::collide(){};
void arrow::move(){};

void fire::collide(){};
void fire::move(){};
