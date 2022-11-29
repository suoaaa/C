#include 	"Class.h"
#include 	<iostream>
#include	"acllib.h"
#include 	"menu_con.h"
using namespace std;

int Init=0;
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
void initimage();					//����ͼƬָ��

void Begingame();										//��Ϸ���漰ʵ��
void timerEvent(int id);								//��ʱ��
void keyEvent(int key,int event);						//�����¼�
void mouseEvent(int x, int y, int button, int event);	//����¼�
void paintgame();										//��ͼ

int Setup()
{
	initimage();
	game_menu(back_image);
	registerMouseEvent(mouseEvent);
	registerTimerEvent(timerEvent);
	registerKeyboardEvent(keyEvent);
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
	loadImage("./Դ��Դ/_usr.bmp", usr_image);
	loadImage("./Դ��Դ/_enemy1.bmp", enemy1_image);
	loadImage("./Դ��Դ/_enemy2.bmp", enemy2_image);
	loadImage("./Դ��Դ/_enemy3.bmp", enemy3_image);
	loadImage("./Դ��Դ/_fire.bmp", fire_image);
	loadImage("./Դ��Դ/_arrow.bmp", arrow_image);
	loadImage("./Դ��Դ/_back.bmp",back_image);
	usr=new User(usr_image);
	for(int i=0;i<n_enemy1;i++)
		en1[i]=new enemy1(enemy1_image,usr);
	for(int i=0;i<n_enemy2;i++)	
	 	en2[i]=new enemy2(enemy2_image,usr);
	for(int i=0;i<n_enemy3;i++)
	 	en3[i]=new enemy3(enemy3_image);
}
void mouseEvent(int x, int y, int button, int event)	//����¼�
{
	if(Init==0)
	{

		if(button == LEFT_BUTTON && event == BUTTON_DOWN&&x>=Winwidth/32*13&&x<=Winwidth/32*19)
		{
			if(y>=Winhigh/18*7&&y<=Winhigh/18*9)	{Init=1;	Begingame();	}
			if(y>=Winhigh/18*10&&y<=Winhigh/18*12)	{Init=1;	config_change();}
			if(y>=Winhigh/18*13&&y<=Winhigh/18*15)	{Init=1;	play_way();		}

		}

	}else
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
	}
}
void paintgame()
{
	if(usr->health)			//����Ҵ���ӡ��Ϸ����
	{	
		beginPaint();
		clearDevice();
		putImageScale(back_image,0,0,Winwidth,Winhigh);
		putImageTransparent(usr->img,usr->getx(),usr->gety(),pica,pica,WHITE);
		for(int i=0;i<n_enemy1;i++)
			if(en1[i]!=NULL)	putImageTransparent(en1[i]->img,en1[i]->getx(),en1[i]->gety(),pica,pica,WHITE);
		for(int i=0;i<n_enemy3;i++)
			if(en3[i]!=NULL)	putImageTransparent(en3[i]->img,en3[i]->getx(),en3[i]->gety(),pica,pica,WHITE);
		for(int i=0;i<n_enemy2;i++)	
		{
			if(en2[i]!=NULL)
			{
				putImageTransparent(en2[i]->img,en2[i]->getx(),en2[i]->gety(),pica,pica,WHITE);
				for(int j=0;j<n_remote;j++)
					if(en2[i]->rem[j]!=NULL)	
						putImageTransparent(en2[i]->rem[j]->img,en2[i]->rem[j]->getx(),en2[i]->rem[j]->gety(),arrow_width,arrow_high,WHITE);}
		}
		for(int i=0;i<n_remote;i++)
			if(usr->rem[i]!=NULL)	if(usr->rem[i]->exist!=0)	
				putImageTransparent(usr->rem[i]->img,usr->rem[i]->getx(),usr->rem[i]->gety(),fire_a,fire_a,WHITE);
				else {delete usr->rem[i];usr->rem[i]=NULL;}		
		
		endPaint();
	}
	else
	{

	}
	beginPaint();
	setBrushColor(WHITE);
	setTextColor(RED);
	setTextBkColor(BLACK);
	setTextSize(20);
	char *Txtofscore=new char[10];
	sprintf(Txtofscore, "%d", usr->getscore());	
	paintText(50, 50, "��ĵ÷�ĿǰΪ��   ");
	paintText(220, 50, Txtofscore);
	endPaint();
}
void timerEvent(int id)           	 //��ʱ��
{	//	id=0�����е�λ�ƶ����ж���ײ�����ٹ���������cd�����0.03s
	//	id=1�����䱻���ܵĵжԵ�λ�����5s
	//	id=2������enemy2��λ�ж��ܷ��ͷ�Զ�̹��������0.5s
	//	id=3: �ر�id=3�ļ�ʱ��ͬʱ����������ʱ��
	switch (id)
	{
	case 0:														//�����ƶ�
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
	case 3:	
		startTimer(0, 50);
		startTimer(1,5000);
		cancelTimer(3);
		break;
	}
}
void keyEvent(int key,int event)						//�����¼�
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
void Begingame()
{
	initWindow("The Sprite Game----made by wangke-2021080909008",0,0,Winwidth,Winhigh);
	if(MUSIC)
	{

	}
	
	startTimer(0, 50);
	startTimer(1,5000);
}