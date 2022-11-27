#include 	"Class.h"
#include 	<iostream>
#include	"acllib.h"
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
void initimage();					//加载图片指针

void timerEvent(int id);								//计时器
void keyEvent(int key,int event);						//键盘事件
void mouseEvent(int x, int y, int button, int event);	//鼠标事件
void paint();											//绘图
void move();											//所有单位移动一次
void lost();											//失败后使用
int Setup()
{
	srand(time(NULL));
	initWindow("The Sprite game----made by wangke-2021080909008",0,0,1200,800);
	initimage();
	// ACL_Sound pSound;
	// loadSound("_mymusic1.mp3",&pSound);
	// playSound(pSound,0);
	// paint();
	registerTimerEvent(timerEvent);
	registerKeyboardEvent(keyEvent);
	registerMouseEvent(mouseEvent);
	startTimer(0, 30);
	startTimer(1, 3000);
	startTimer(2, 500);
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
	loadImage("_usr.bmp", usr_image);
	loadImage("_enemy1.bmp", enemy1_image);
	loadImage("_enemy2.bmp", enemy2_image);
	loadImage("_enemy3.bmp", enemy3_image);
	loadImage("_fire.bmp", fire_image);
	loadImage("_arrow.bmp", arrow_image);
	usr=new User(usr_image);
	for(int i=0;i<n_enemy1;i++)
		en1[i]=new enemy1(enemy1_image,usr);
	for(int i=0;i<n_enemy2;i++)	
	 	en2[i]=new enemy2(enemy2_image,usr);
	for(int i=0;i<n_enemy3;i++)
	 	en3[i]=new enemy3(enemy3_image);
}
void paint()
{
	beginPaint();
	if(usr->health)
	{
		putImageScale(usr->img,usr->getx(),usr->gety(),picwidth,pichigh);
		for(int i=0;i<n_enemy1;i++)
			if(en1[i]!=NULL)	putImageScale(en1[i]->img,en1[i]->getx(),en1[i]->gety(),picwidth,pichigh);
		for(int i=0;i<n_enemy3;i++)
			if(en3[i]!=NULL)	putImageScale(en3[i]->img,en3[i]->getx(),en3[i]->gety(),picwidth,pichigh);
		for(int i=0;i<n_enemy2;i++)	
		{
			if(en2[i]!=NULL)	putImageScale(en2[i]->img,en2[i]->getx(),en2[i]->gety(),picwidth,pichigh);
			for(int j=0;j<n_remote;j++)
				if(en2[i]->rem[j]!=NULL)	putImageScale(en2[i]->rem[j]->img,en2[i]->rem[j]->getx(),en2[i]->rem[j]->gety(),arrow_width,arrow_high);
		}
		for(int i=0;i<n_remote;i++)
			if(usr->rem[i]!=NULL)	putImageScale(usr->rem[i]->img,usr->rem[i]->getx(),usr->rem[i]->gety(),fire_width,fire_high);
	}
	endPaint();
}
void timerEvent(int id)           	 //计时器
{	//计时器规定：
	//	id=0：所有单位移动并判断碰撞，减少攻击及技能cd，间隔0.03s
	//	id=1：补充被击败的敌对单位，间隔3s
	//	id=2：所有enemy2单位判断能否释放远程攻击，间隔0.5s
	//	id=3：
	switch (id)
	{
	case 0:
		for(int i=0;i<n_enemy1;i++)
			if(en1[i]!=NULL)	en1[i]->collide(usr,usr->rem);
		for(int i=0;i<n_enemy3;i++)
			if(en3[i]!=NULL)	en3[i]->collide(usr,usr->rem);
		for(int i=0;i<n_enemy2;i++)	
		{
			if(en2[i]!=NULL)	en2[i]->collide(usr,usr->rem);
			for(int j=0;j<n_remote;j++)
				if(en2[i]->rem[j]!=NULL)	en2[i]->rem[j]->collide(usr);
		}
		if(usr->health)	paint();
			else lost();
		break;
	case 1:
		for(int i=0;i<n_enemy1;i++)
			if(en1[i]==NULL)	en1[i]->collide(usr,usr->rem);
		for(int i=0;i<n_enemy3;i++)
			if(en3[i]!=NULL)	en3[i]->collide(usr,usr->rem);
		for(int i=0;i<n_enemy2;i++)	
		{
			if(en2[i]!=NULL)	en2[i]->collide(usr,usr->rem);
			for(int j=0;j<n_remote;j++)
				if(en2[i]->rem[j]!=NULL)	en2[i]->rem[j]->collide(usr);
		}
		break;
	case 2:
		/* code */
		break;
	case 3:
		/* code */
		break;
	case 4:
		/* code */
		break;
	case 5:
		/* code */
		break;
	default:
		break;
	}
}
void keyEvent(int key, int event) 	 //键盘输入
{

}
void mouseEvent(int key)	         //鼠标输入
{

}
void move()							//所有单位移动一次
{

}
void lost()							//失败后使用
{

}