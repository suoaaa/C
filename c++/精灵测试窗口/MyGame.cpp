#include 	"Class.h"
#include 	<iostream>
#include	"acllib1.h"
using namespace std;

User *usr;
enemy1 *(en1[n_enemy1]);
enemy2 *(en2[n_enemy2]);
enemy3 *(en3[n_enemy3]);
ACL_Image *usr_image;
ACL_Image *enemy1_image;
ACL_Image *enemy2_image;
ACL_Image *enemy3_image;
ACL_Image *fire_image;
ACL_Image *arrow_image;
ACL_Image *back_image;
void initimage();					//加载图片指针

void timerEvent(int id);								//计时器
void keyEvent(int key,int event);						//键盘事件
void mouseEvent(int x, int y, int button, int event);	//鼠标事件
void paintgame();										//绘图

int Setup()
{
	srand(time(NULL));
	HWND g_1=initWindow("The Sprite game----made by wangke-2021080909008",0,0,Winwidth,Winhigh);
	HWND g_2=initWindow("2",0,0,Winwidth,Winhigh);
	if(IsWindow(g_1))	DestroyWindow(g_1);
	initimage();
	// beginPaint();
	// putImageTransparent(arrow_image,600,400,arrow_width,arrow_high,WHITE);
	// endPaint();
	// paintgame();
	registerTimerEvent(timerEvent);
	registerKeyboardEvent(keyEvent);
	registerMouseEvent(mouseEvent);
	startTimer(0, 50);
	startTimer(1,5000);
	return 0;
}
void initimage()
{
	srand(time(NULL));
	usr_image=new ACL_Image;
	enemy1_image=new ACL_Image;
	enemy2_image=new ACL_Image;
	enemy3_image=new ACL_Image;
	fire_image=new ACL_Image;
	arrow_image=new ACL_Image;
	back_image=new ACL_Image;
	loadImage("./源资源/_usr.bmp", usr_image);
	loadImage("./源资源/_enemy1.bmp", enemy1_image);
	loadImage("./源资源/_enemy2.bmp", enemy2_image);
	loadImage("./源资源/_enemy3.bmp", enemy3_image);
	loadImage("./源资源/_fire.bmp", fire_image);
	loadImage("./源资源/_arrow.bmp", arrow_image);
	loadImage("./源资源/_back.bmp",back_image);
	usr=new User(usr_image);
	for(int i=0;i<n_enemy1;i++)
		en1[i]=new enemy1(enemy1_image,usr);
	for(int i=0;i<n_enemy2;i++)	
	 	en2[i]=new enemy2(enemy2_image,usr);
	for(int i=0;i<n_enemy3;i++)
	 	en3[i]=new enemy3(enemy3_image);
}
void paintgame()
{
	if(usr->health)			//当玩家存活打印游戏界面
	{	
		beginPaint();
		clearDevice();
		putImageScale(back_image,0,0,Winwidth,Winhigh);
		putImageTransparent(usr->img,usr->getx(),usr->gety(),picwidth,pichigh,WHITE);
		for(int i=0;i<n_enemy1;i++)
			if(en1[i]!=NULL)	putImageTransparent(en1[i]->img,en1[i]->getx(),en1[i]->gety(),picwidth,pichigh,WHITE);
		for(int i=0;i<n_enemy3;i++)
			if(en3[i]!=NULL)	putImageTransparent(en3[i]->img,en3[i]->getx(),en3[i]->gety(),picwidth,pichigh,WHITE);
		for(int i=0;i<n_enemy2;i++)	
		{
			if(en2[i]!=NULL)
			{
				putImageTransparent(en2[i]->img,en2[i]->getx(),en2[i]->gety(),picwidth,pichigh,WHITE);
				for(int j=0;j<n_remote;j++)
					if(en2[i]->rem[j]!=NULL)	
						putImageTransparent(en2[i]->rem[j]->img,en2[i]->rem[j]->getx(),en2[i]->rem[j]->gety(),arrow_width,arrow_high,WHITE);}
		}
		for(int i=0;i<n_remote;i++)
			if(usr->rem[i]!=NULL)	if(usr->rem[i]->exist!=0)	
				putImageTransparent(usr->rem[i]->img,usr->rem[i]->getx(),usr->rem[i]->gety(),fire_width,fire_high,WHITE);
				else {delete usr->rem[i];usr->rem[i]=NULL;}		
				cancelTimer(0);
		endPaint();
	}
	else
	{

		cancelTimer(1);
	}
	beginPaint();
	setBrushColor(WHITE);
	setTextColor(RED);
	setTextBkColor(BLACK);
	setTextSize(20);
	char *txt=new char[10];
	sprintf(txt, "%d", usr->getscore());	
	paintText(50, 50, "你的得分目前为：   ");
	paintText(220, 50, txt);
	paintText(50, 50, "你的得分目前为：   ");
	paintText(220, 50, txt);
	//rectangle(0, 0, 500, 300);
	endPaint();
}
void timerEvent(int id)           	 //计时器
{	//计时器规定：
	//	id=0：所有单位移动并判断碰撞，减少攻击及技能cd，间隔0.03s
	//	id=1：补充被击败的敌对单位，间隔5s
	//	id=2：所有enemy2单位判断能否释放远程攻击，间隔0.5s
	//	id=3：
	switch (id)
	{
	case 0:														//进行移动
		for(int i=0;i<n_enemy1;i++)
			if(en1[i]!=NULL&&en1[i]->health)	
				{en1[i]->move(usr,usr->rem);
				if(en1[i]->health==0){delete en1[i];en1[i]=NULL;}}
		for(int i=0;i<n_enemy3;i++)
			if(en3[i]!=NULL&&en3[i]->health)
			{en3[i]->move(usr,usr->rem);
				if(en3[i]->health==0){delete en3[i];en3[i]=NULL;}}
		for(int i=0;i<n_enemy2;i++)	
		{
			if(en2[i]!=NULL&&en2[i]->health)
			{	en2[i]->move(usr,usr->rem,arrow_image);
				if(en2[i]->health!=0)
					for(int j=0;j<n_remote;j++)
				 		if(en2[i]->rem[j]!=NULL)	en2[i]->rem[j]->move(usr);
				if(en2[i]->health==0)
					{for(int j=0;j<n_remote;j++) if(en2[i]->rem[j]!=NULL)    {delete en2[i]->rem[i];en2[i]->rem[i]=NULL;}delete en2[i];en2[i]=NULL;}
			}
		}
		for(int i=0;i<n_remote;i++)
			if(usr->rem[i]!=NULL)	
			{
				if(usr->rem[i]->exist!=0)	
					usr->rem[i]->move(usr);
				else {delete usr->rem[i];usr->rem[i]=NULL;}
			}
		if(usr->cd_skill>=0)	usr->cd_skill--;
		if(usr->cd_hit>=0)	usr->cd_hit--;
		if(usr->skill>0)	usr->skill--;
		paintgame();
		break;
	case 1:
		for(int i=0;i<n_enemy1;i++)
			if(en1[i]==NULL)	en1[i]=new enemy1(enemy1_image,usr);
		for(int i=0;i<n_enemy3;i++)
			if(en3[i]==NULL)	en3[i]=new enemy3(enemy3_image);
		for(int i=0;i<n_enemy2;i++)	
			if(en2[i]==NULL)	en2[i]=new enemy2(enemy2_image,usr);
		break;
	default:
		break;
	}
}
void keyEvent(int key,int event)						//键盘事件
{
	if (event != KEY_DOWN)return;
	if(usr)	usr->move(key);
	for(int i=0;i<n_enemy1;i++)
		if(en1[i]!=NULL&&en1[i]->health)	
			{en1[i]->collide(usr,usr->rem);
			if(en1[i]->health==0){delete en1[i];en1[i]=NULL;}}
		for(int i=0;i<n_enemy3;i++)
			if(en3[i]!=NULL&&en3[i]->health)
			{en3[i]->collide(usr,usr->rem);
				if(en3[i]->health==0){delete en3[i];en3[i]=NULL;}}
		for(int i=0;i<n_enemy2;i++)
			if(en2[i]!=NULL&&en2[i]->health)
			{en2[i]->collide(usr,usr->rem);
				if(en2[i]->health==0){delete en2[i];en2[i]=NULL;}
				else for(int j=0;j<n_remote;j++)
					if(en2[i]->rem[j]!=NULL)	en2[i]->rem[j]->collide(usr);
			}
	paintgame();
}
void mouseEvent(int x, int y, int button, int event)	//鼠标事件
{
	if(button == LEFT_BUTTON && event == BUTTON_DOWN&&usr->cd_hit<=0)
	{
		int i=0;
		for(;i<n_remote;i++)
		{
			if(usr->rem[i]==NULL)
			{ 
				usr->rem[i]=new fire(fire_image,usr,x,y);
				usr->cd_hit=CD;
				break;
			}
		}
	beginPaint();
	initConsole ();
	printf("hello");
	endPaint();
	}
}