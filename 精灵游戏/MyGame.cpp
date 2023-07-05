#include	"acllib.h"
#include 	"Class.h"
#include 	<iostream>

using namespace std;

User *usr;
enemy1 *(en1[7]);
enemy2 *(en2[5]);
enemy3 *(en3[10]);
ACL_Image *usr_image,*enemy1_image,*howtoplay;
ACL_Image *enemy2_image,*enemy3_image;
ACL_Image *fire_image,*arrow_image,*back_image;
ACL_Sound mymusic;

FILE *fpr,*fpw;

int config_code[3]={0,0,1};
int config_now[3];
int music[2]={0,1};
int winwidth[3]={960,1280,1600};
int winhigh[3]={540,720,900};
int n_ene1[3]={3,4,7};
int n_ene2[3]={1,2,5};
int n_ene3[3]={5,7,10};
int Init=0;
int n_error=0;
int music_bool=0;

static int initnum=0;									//��һ������Ҫ�Ĳ���
bool initgame();										//����ͼƬָ��
void game_menu();										//��ʼ�˵���������ʼ��Ϸ���Ѷ�ѡ�����淨���ܣ�
void Begingame();										//��Ϸ���漰ʵ��
void paintgame();										//��Ϸ�����ͼ
void config_menu();										//���ò˵�����
void config_change(int *config_code);   				//��������
void play_way();     									//�淨����

void timerEvent(int id);								//��ʱ��
void keyEvent(int key,int event);						//�����¼�
void mouseEvent(int x, int y, int button, int event);	//����¼�

int Setup()
{
	if(initgame())
	{
		initWindow("The Menu of Sprite game----made by wangke-2021080909008",DEFAULT/2,DEFAULT,winwidth[1],winhigh[1]);
		registerMouseEvent(mouseEvent);
		registerTimerEvent(timerEvent);
		registerKeyboardEvent(keyEvent);
		game_menu();
	}
	return 0;
}

bool initgame()
{
	static int n=0; static char ch[10];//ʵ����n��chû�ã�ֻ����������config�ļ��С����ࡱ�����ݣ�config�ļ�Ϊ���׿�������Ĭ�������ļ����и���˵��
    if(initnum==0)
    {   initnum=1;
        fpr=fopen("./Դ��Դ/config.txt","r");
        if(!fpr)    
            {   initWindow("The config of Sprite game----made by wangke-2021080909008",DEFAULT/2,DEFAULT/2,480,270);
                beginPaint(); setTextSize(20);paintText(140,110, "��ȡ��Ϸ�����ļ�ʧ��");endPaint();return 0;}
        else
        {
            fscanf(fpr,"%s %d",ch,&config_now[0]);
            fscanf(fpr,"%s %d",ch,&config_now[1]);
            fscanf(fpr,"%s %d",ch,&n);
            fscanf(fpr,"%s %d",ch,&config_now[2]);
            fclose(fpr);
        }
	    srand(time(NULL));
	    usr_image=new ACL_Image;    enemy1_image=new ACL_Image; enemy2_image=new ACL_Image;
	    enemy3_image=new ACL_Image; fire_image=new ACL_Image;   arrow_image=new ACL_Image;
	    back_image=new ACL_Image;   loadImage("./Դ��Դ/_usr.bmp", usr_image);  howtoplay=new ACL_Image;
        loadImage("./Դ��Դ/_enemy1.bmp", enemy1_image);    loadImage("./Դ��Դ/_enemy2.bmp", enemy2_image);
	    loadImage("./Դ��Դ/_enemy3.bmp", enemy3_image);    loadImage("./Դ��Դ/_fire.bmp", fire_image);
	    loadImage("./Դ��Դ/_arrow.bmp", arrow_image);      loadImage("./Դ��Դ/_back.bmp",back_image);
		loadImage("./Դ��Դ/_playway.bmp",howtoplay);		loadSound("./Դ��Դ/_mymusic.mp3",&mymusic);
    }else{
		fpr=fopen("./Դ��Դ/config.txt","r");
        if(!fpr)    
            {   initWindow("The Menu of Sprite game----made by wangke-2021080909008",DEFAULT/2,DEFAULT/2,480,270);
                beginPaint(); setTextSize(20);paintText(140,110, "��ȡ��Ϸ�����ļ�ʧ��");endPaint();return 0;}
        else
        {
            fscanf(fpr,"%s %d",ch,&config_now[0]);
            fscanf(fpr,"%s %d",ch,&config_now[1]);
            fscanf(fpr,"%s %d",ch,&n);
            fscanf(fpr,"%s %d",ch,&config_now[2]);
            fclose(fpr);
        }
	}
    switch (config_now[0])																						//�ж����ڵ��������
    {	case 0: config_code[0]=0; break;		case 1: config_code[0]=1; break;	}
    switch (config_now[1])
    {	case 960:   config_code[1]=0; break;	case 1280:  config_code[1]=1; break;	case 1600:  config_code[1]=2; break;}
    switch (config_now[2])
    {	case 3: config_code[2]=0; break;		case 4: config_code[2]=1; break;		case 7: 	config_code[2]=2; break;}
	if(!usr)	usr=new User(usr_image,config_code);															//�����£���ʼ����
		else {delete usr;usr=new User(usr_image,config_code);}
	for(int i=0;i<7;i++)
		if(!en1[i])	en1[i]=new enemy1(enemy1_image,usr,config_code);
			else {delete en1[i];en1[i]=new enemy1(enemy1_image,usr,config_code);}
	for(int i=0;i<5;i++)	
		if(!en2[i])	en2[i]=new enemy2(enemy2_image,usr,config_code);
			else {delete en2[i];en2[i]=new enemy2(enemy2_image,usr,config_code);}
	for(int i=0;i<10;i++)
		if(!en3[i])	en3[i]=new enemy3(enemy3_image,config_code);
			else {delete en3[i];en3[i]=new enemy3(enemy3_image,config_code);}
	return 1;
}
void game_menu()        //��ʼ�˵�
{
    beginPaint();
    clearDevice();
    putImageScale(back_image,DEFAULT/2,DEFAULT/2,winwidth[1],winhigh[1]);

    setPenColor(RED);    setPenWidth(10);    setBrushColor(YELLOW);      //������������ʽ
    rectangle(winwidth[1]/16*5, winhigh[1]/9, winwidth[1]/16*11, winhigh[1]/18*5);	

    setPenColor(BLUE);    setPenWidth(5);    setBrushColor(GREEN);      //����������������ʽ
    rectangle(winwidth[1]/32*13, winhigh[1]/18*7, winwidth[1]/32*19, winhigh[1]/18*9);
    rectangle(winwidth[1]/32*13, winhigh[1]/18*10, winwidth[1]/32*19, winhigh[1]/18*12);
    rectangle(winwidth[1]/32*13, winhigh[1]/18*13, winwidth[1]/32*19, winhigh[1]/18*15);

    setTextColor(BLACK);	setTextSize(winwidth[1]/16/10*7);    setTextBkColor(EMPTY);           //�������������ָ�ʽ
    paintText(winwidth[1]/32*13,winhigh[1]/6, "������Ϸ");

    setTextSize(winwidth[1]/16/10*5);    setTextBkColor(EMPTY);            //�����������������ָ�ʽ
    paintText(winwidth[1]/32*14,winhigh[1]/36*15, "��ʼ��Ϸ");
    paintText(winwidth[1]/32*14,winhigh[1]/36*21, "��������");
    paintText(winwidth[1]/32*14,winhigh[1]/36*27, "�淨˵��");
    
    endPaint();
}
void Begingame()		//��ʼ��Ϸ
{
	initWindow("The Sprite Game----made by wangke-2021080909008",DEFAULT/2,DEFAULT,winwidth[config_code[1]],winhigh[config_code[1]]);
	if(!music[config_code[0]]&&music_bool==1){music_bool=0;	stopSound (mymusic);}
	startTimer(0,50);
	startTimer(1,5000);
}
void paintgame()		//��Ϸ�������
{	
	if(usr->health)			//����Ҵ���ӡ��Ϸ����
	{	
		beginPaint();
		clearDevice();
		putImageScale(back_image,0,0,winwidth[config_code[1]],winhigh[config_code[1]]);
		putImageTransparent(usr->img,usr->getx(),usr->gety(),pica,pica,WHITE);
		for(int i=0;i<n_ene1[config_code[2]];i++)
			if(en1[i]!=NULL)	putImageTransparent(en1[i]->img,en1[i]->getx(),en1[i]->gety(),pica,pica,WHITE);
		for(int i=0;i<n_ene3[config_code[2]];i++)
			if(en3[i]!=NULL)	putImageTransparent(en3[i]->img,en3[i]->getx(),en3[i]->gety(),pica,pica,WHITE);
		for(int i=0;i<n_ene2[config_code[2]];i++)	
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
		if(usr->skill>0)
		{	setBrushColor(EMPTY);setPenColor(RED);setPenWidth(5);
			ellipse(usr->getx(),usr->gety(),pica+usr->getx(),pica+usr->gety());	}
		setBrushColor(WHITE);
		setTextColor(RED);
		setTextBkColor(BLACK);
		setTextSize(20);

		char *Txtofscore=new char[10];
		sprintf(Txtofscore, "%d", usr->getscore());	
		paintText(50, 50, "��ĵ÷�ĿǰΪ��   ");
		paintText(220, 50, Txtofscore);
		setPenColor(GREEN);    setPenWidth(0);    setBrushColor(RGB(238,18,137));      
		rectangle(winwidth[config_code[1]]/32.0*27, winhigh[config_code[1]]/50.0*1.5, winwidth[config_code[1]]/48.0*46, winhigh[config_code[1]]/25.0*2);	
		setTextSize(winwidth[config_code[1]]/16/10*3);    setTextBkColor(EMPTY); setTextColor(GREEN);
		paintText(winwidth[config_code[1]]/16*13.5,winhigh[config_code[1]]/25, "�������˵�");
		endPaint();
	}
	else
	{
		if(n_error==0)//��֤ÿ����Ϸֻ�ᵯ��һ��ʧ�ܴ���
		{	n_error++;	cancelTimer(0);
			cancelTimer(1);
 			initWindow("The Sprite game",DEFAULT/2,DEFAULT,480,270);
        	beginPaint(); setTextSize(20);setTextBkColor(EMPTY); setTextColor(RED);paintText(170,90, "��Ϸʧ��");
			char *txtofscore=new char[10];sprintf(txtofscore,"%d",usr->getscore());
			setPenColor(RED);	setPenWidth(3);	setBrushColor(YELLOW);
			rectangle(90,160,210,200);			rectangle(270,160,385,200);
			paintText(160,120, "��ĵ÷�Ϊ��");	paintText(280,120, txtofscore);
			paintText(100,170, "�������˵�");	paintText(280,170, "����һ��");
			endPaint();
			Init=4;
		}
	}
}
void config_menu()        //��ʾ��ǰ����
{
    beginPaint();
    clearDevice();
    putImageScale(back_image,DEFAULT,DEFAULT,winwidth[1],winhigh[1]);

    setPenColor(RGB(128,0,0));    setPenWidth(10);    setBrushColor(RGB(255,165,0));      //������������ʽ
    rectangle(winwidth[1]/16*5, winhigh[1]/9, winwidth[1]/16*11, winhigh[1]/18*5);	

    setPenColor(RGB(0,255,127));    setPenWidth(5);    setBrushColor(YELLOW);       //����������������ʽ
    rectangle(winwidth[1]/32*8,    winhigh[1]/18*7, winwidth[1]/32*14, winhigh[1]/18*9);           //��һ��
    rectangle(winwidth[1]/32*19,   winhigh[1]/18*7, winwidth[1]/32*21, winhigh[1]/18*9);
    rectangle(winwidth[1]/32*22,   winhigh[1]/18*7, winwidth[1]/32*24, winhigh[1]/18*9);

    rectangle(winwidth[1]/32*8,    winhigh[1]/18*10, winwidth[1]/32*14, winhigh[1]/18*12);         //�ڶ���
    rectangle(winwidth[1]/32*17.5, winhigh[1]/18*10, winwidth[1]/32*19.5, winhigh[1]/18*12);
    rectangle(winwidth[1]/32*20.5, winhigh[1]/18*10, winwidth[1]/32*22.5, winhigh[1]/18*12);
    rectangle(winwidth[1]/32*23.5, winhigh[1]/18*10, winwidth[1]/32*25.5, winhigh[1]/18*12);

    rectangle(winwidth[1]/32*8,    winhigh[1]/18*13, winwidth[1]/32*14, winhigh[1]/18*15);         //������
    rectangle(winwidth[1]/32*17.5, winhigh[1]/18*13, winwidth[1]/32*19.5, winhigh[1]/18*15);
    rectangle(winwidth[1]/32*20.5, winhigh[1]/18*13, winwidth[1]/32*22.5, winhigh[1]/18*15);
    rectangle(winwidth[1]/32*23.5, winhigh[1]/18*13, winwidth[1]/32*25.5, winhigh[1]/18*15);

    setTextColor(BLACK);	setTextSize(winwidth[1]/16/10*7);    setTextBkColor(EMPTY); //�������������ָ�ʽ
    paintText(winwidth[1]/32*13,winhigh[1]/6, "��Ϸ����");

    setTextSize(winwidth[1]/16/10*5);    setTextBkColor(EMPTY);            //�����������������ָ�ʽ
    paintText(  winwidth[1]/32*9,  winhigh[1]/36*15, "��������");                 //��һ��
    paintText(  winwidth[1]/32*19.5,winhigh[1]/36*15, "��");                 
    paintText(  winwidth[1]/32*22.5,winhigh[1]/36*15, "��");
    paintText(  winwidth[1]/32*9,  winhigh[1]/36*21, "���ڴ�С");                 //�ڶ���
    paintText(  winwidth[1]/32*18, winhigh[1]/36*21, "С");
    paintText(  winwidth[1]/32*21, winhigh[1]/36*21, "��");
    paintText(  winwidth[1]/32*24, winhigh[1]/36*21, "��");
    paintText(  winwidth[1]/32*9,  winhigh[1]/36*27, "�Ѷ�ѡ��");                 //������
    paintText(  winwidth[1]/32*18, winhigh[1]/36*27, "��");
    paintText(  winwidth[1]/32*21, winhigh[1]/36*27, "��");
    paintText(  winwidth[1]/32*24, winhigh[1]/36*27, "��");

    setPenColor(RGB(255,0,255));    setPenWidth(5);    setBrushColor(EMPTY);
    switch (config_now[0])
    {
    case 0:     ellipse( winwidth[1]/32*19, winhigh[1]/18*7, winwidth[1]/32*21, winhigh[1]/18*9);       config_code[0]=0; break;
    case 1:     ellipse( winwidth[1]/32*22, winhigh[1]/18*7, winwidth[1]/32*24, winhigh[1]/18*9);       config_code[0]=1; break;
    }
    switch (config_now[1])
    {
    case 960:   ellipse(winwidth[1]/32.0*17.5, winhigh[1]/18.0*10, winwidth[1]/32.0*19.5, winhigh[1]/18*12);  config_code[1]=0; break;
    case 1280:  ellipse(winwidth[1]/32.0*20.5, winhigh[1]/18.0*10, winwidth[1]/32.0*22.5, winhigh[1]/18*12);  config_code[1]=1; break;
    case 1600:  ellipse(winwidth[1]/32.0*23.5, winhigh[1]/18.0*10, winwidth[1]/32.0*25.5, winhigh[1]/18*12);  config_code[1]=2; break;
    }
    switch (config_now[2])
    {
    case 3:     ellipse(winwidth[1]/32.0*17.5, winhigh[1]/18.0*13, winwidth[1]/32.0*19.5, winhigh[1]/18.0*15);  config_code[2]=0; break;
    case 4:     ellipse(winwidth[1]/32.0*20.5, winhigh[1]/18.0*13, winwidth[1]/32.0*22.5, winhigh[1]/18.0*15);  config_code[2]=1; break;
    case 7:     ellipse(winwidth[1]/32.0*23.5, winhigh[1]/18.0*13, winwidth[1]/32.0*25.5, winhigh[1]/18.0*15);  config_code[2]=2; break;
    }
	setPenColor(GREEN);    setPenWidth(0);    setBrushColor(RGB(238,18,137));      
	rectangle(winwidth[1]/32.0*27, winhigh[1]/50.0*1.5, winwidth[1]/48.0*46, winhigh[1]/25.0*2);	
	setTextSize(winwidth[1]/16/10*3);    setTextBkColor(EMPTY); setTextColor(GREEN);
	paintText(winwidth[1]/16*13.5,winhigh[1]/25, "�������˵�");
    endPaint();
}
void config_change(int *config_code)   //ʵ�ָ�������
{
	fpw=fopen("./Դ��Դ/config.txt","w");
	if(!fpw)    
        {   initWindow("error",DEFAULT/2,DEFAULT/2,480,270);
            beginPaint(); setTextSize(20);paintText(140,110, "������Ϸ�����ļ�ʧ��");endPaint();return ;}
        else
        {
            fprintf(fpr,"MUSIC %d\n",config_code[0]);
            fprintf(fpr,"Winwidth %d\n",winwidth[config_code[1]]);
            fprintf(fpr,"Winhigh %d\n",winhigh[config_code[1]]);
			fprintf(fpr,"n_enemy1 %d\n",n_ene1[config_code[2]]);
			fprintf(fpr,"n_enemy2 %d\n",n_ene2[config_code[2]]);
			fprintf(fpr,"n_enemy3 %d\n",n_ene3[config_code[2]]);
        }
		fclose(fpw);
}
void play_way()     //�淨����
{
	beginPaint();
	clearDevice();
	putImageScale(back_image,DEFAULT/2,DEFAULT/2,winwidth[1],winhigh[1]);
	putImageScale(howtoplay,DEFAULT/2,DEFAULT,winwidth[1],winhigh[1]);
	setPenColor(GREEN);    setPenWidth(0);    setBrushColor(RGB(238,18,137));      
	rectangle(winwidth[1]/32.0*27, winhigh[1]*3/100, winwidth[1]/24.0*23, winhigh[1]/12.5);	
	setTextSize(winwidth[1]*3/160);    setTextBkColor(EMPTY); setTextColor(GREEN);
	paintText(winwidth[1]*27/32,winhigh[1]/25, "�������˵�");
	endPaint();
}
void mouseEvent(int x, int y, int button, int event)	//����¼�
{
	switch (Init)
	{
	case 0:
		if(button == LEFT_BUTTON && event == BUTTON_DOWN&&x>=winwidth[1]/32*13&&x<=winwidth[1]/32*19)
		{
			initConsole();
			if(y>=winhigh[1]/18*7&&y<=winhigh[1]/18*9)		{Init=1;	if(music[config_code[0]]&&music_bool==0){music_bool=1;	playSound(mymusic,10);}	Begingame();}
			if(y>=winhigh[1]/18*10&&y<=winhigh[1]/18*12)	{Init=2;	config_menu();	}
			if(y>=winhigh[1]/18*13&&y<=winhigh[1]/18*15)	{Init=3;	play_way();		}
		}
		break;
	case 1:if(button == LEFT_BUTTON && event == BUTTON_DOWN&&usr!=NULL)
			{
				if(x>=winwidth[config_code[1]]/32.0*27&&y>=winhigh[config_code[1]]*3/100&&x<=winwidth[config_code[1]]/24.0*23&&y<=winhigh[config_code[1]]/12.5)
				{	
					Init=0;		cancelTimer(0);	cancelTimer(1);
					if(initgame())	
					{
						initWindow("The Menu of Sprite game----made by wangke-2021080909008",DEFAULT/2,DEFAULT,winwidth[1],winhigh[1]);
						game_menu();	
					}
				}	else if(usr->cd_hit<=0)
					{	int i=0;
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
			};break;
	case 2:	if(button == LEFT_BUTTON && event == BUTTON_DOWN)
			{
				int changed=0;
				if(x>=winwidth[1]/32*19&&y>=winhigh[1]/18*7&&x<=winwidth[1]/32*21&&y<=winhigh[1]/18*9)
					{	config_code[0]=0;	config_now[0]=0;	changed=1;	}
				if(x>=winwidth[1]/32*22&&y>=winhigh[1]/18*7&&x<=winwidth[1]/32*24&&y<= winhigh[1]/18*9)
					{	config_code[0]=1;	config_now[0]=1;	changed=1;	}
				if(x>=winwidth[1]/32*17.5&&y>=winhigh[1]/18*10&&x<= winwidth[1]/32*19.5&&y<=winhigh[1]/18*12)
					{	config_code[1]=0;	config_now[1]=winwidth[0];	changed=1;	}
				if(x>=winwidth[1]/32*20.5&&y>=winhigh[1]/18*10&&x<=winwidth[1]/32*22.5&&y<= winhigh[1]/18*12)
					{	config_code[1]=1;	config_now[1]=winwidth[1];	changed=1;	}
				if(x>=winwidth[1]/32*23.5&&y>=winhigh[1]/18*10&&x<=winwidth[1]/32*25.5&&y<=winhigh[1]/18*12)
					{	config_code[1]=2;	config_now[1]=winwidth[2];	changed=1;	}
				if(x>=winwidth[1]/32*17.5&&y>=winhigh[1]/18*13&&x<=winwidth[1]/32*19.5&&y<=winhigh[1]/18*15)
					{	config_code[2]=0;	config_now[2]=n_ene1[0];	changed=1;	}
				if(x>=winwidth[1]/32*20.5&&y>=winhigh[1]/18*13&&x<=winwidth[1]/32*22.5&&y<=winhigh[1]/18*15)
					{	config_code[2]=1;	config_now[2]=n_ene1[1];	changed=1;	}
				if(x>=winwidth[1]/32*23.5&&y>=winhigh[1]/18*13&&x<=winwidth[1]/32*25.5&&y<=winhigh[1]/18*15)
					{	config_code[2]=2;	config_now[2]=n_ene1[2];	changed=1;	}
				if(changed==1){	config_change(config_code);		config_menu();	}
				if(x>=winwidth[1]/32.0*27&&y>=winhigh[1]*3/100&&x<=winwidth[1]/24.0*23&&y<=winhigh[1]/12.5)
				{	
					Init=0;	if(initgame())	{game_menu();}
				}
			}break;
	case 3:if(button == LEFT_BUTTON && event == BUTTON_DOWN&&x>=winwidth[1]/32.0*27&&y>=winhigh[1]*3/100&&x<=winwidth[1]/24.0*23&&y<=winhigh[1]/12.5)
				{game_menu();Init=0;break;}
	case 4:if(button == LEFT_BUTTON && event == BUTTON_DOWN)
		{
			if(x>=90&&y>=160&&x<=210&&y<=200)
			{	Init=0;
				initWindow("The Menu of Sprite game----made by wangke-2021080909008",DEFAULT/2,DEFAULT,winwidth[1],winhigh[1]);
				game_menu();
			}	
			if(x>=270&&y>=160&&x<=385&&y<=200) 
			{	Init=1;	n_error=0;if(initgame())	Begingame();	}
	 	}
	}
}

void timerEvent(int id)           	 //��ʱ��
{	//	id=0�����е�λ�ƶ����ж���ײ�����ٹ���������cd�����0.05s
	//	id=1�����䱻���ܵĵжԵ�λ�����5s
	switch (id)
	{
	case 0:														//�����ƶ�
		for(int i=0;i<n_ene1[config_code[2]];i++)
			if(en1[i]!=NULL&&en1[i]->health)	
				{en1[i]->move(usr,usr->rem );
				if(en1[i]->health==0){delete en1[i];en1[i]=NULL;}}
		for(int i=0;i<n_ene3[config_code[2]];i++)
			if(en3[i]!=NULL&&en3[i]->health)
			{en3[i]->move(usr,usr->rem,config_code );
				if(en3[i]->health==0){delete en3[i];en3[i]=NULL;}}
		for(int i=0;i<n_ene2[config_code[2]];i++)	
		{
			if(en2[i]!=NULL&&en2[i]->health)
			{	en2[i]->move(usr,usr->rem,arrow_image,config_code );
				if(en2[i]->health!=0)
					for(int j=0;j<n_remote;j++)
				 		if(en2[i]->rem[j]!=NULL)	en2[i]->rem[j]->move(usr,config_code);
				if(en2[i]->health==0)
					{for(int j=0;j<n_remote;j++) if(en2[i]->rem[j]!=NULL)    {delete en2[i]->rem[i];en2[i]->rem[i]=NULL;}delete en2[i];en2[i]=NULL;}
			}
		}
		for(int i=0;i<n_remote;i++)
			if(usr->rem[i]!=NULL)	
			{
				if(usr->rem[i]->exist!=0)	
					usr->rem[i]->move(usr,config_code);
				else {delete usr->rem[i];usr->rem[i]=NULL;}
			}
		if(usr->cd_skill>=0)	usr->cd_skill--;
		if(usr->cd_hit>=0)	usr->cd_hit--;
		if(usr->skill>=0)	usr->skill--;
		paintgame();
		break;
	case 1:
		for(int i=0;i<n_ene1[config_code[2]];i++)
			if(en1[i]==NULL)	en1[i]=new enemy1(enemy1_image,usr,config_code);
		for(int i=0;i<n_ene3[config_code[2]];i++)
			if(en3[i]==NULL)	en3[i]=new enemy3(enemy3_image,config_code);
		for(int i=0;i<n_ene2[config_code[2]];i++)	
			if(en2[i]==NULL)	en2[i]=new enemy2(enemy2_image,usr,config_code);
		break;
	}
}
void keyEvent(int key,int event)						//�����¼�
{
	switch (Init)
	{
	case 1:	if (event != KEY_DOWN)return;
			if(usr)	usr->move(key,config_code);
			for(int i=0;i<n_ene1[config_code[2]];i++)
				if(en1[i]!=NULL&&en1[i]->health)	{en1[i]->collide(usr,usr->rem );
			if(en1[i]->health==0){delete en1[i];en1[i]=NULL;}}
			for(int i=0;i<n_ene3[config_code[2]];i++)
				if(en3[i]!=NULL&&en3[i]->health)	{en3[i]->collide(usr,usr->rem );
			if(en3[i]->health==0){delete en3[i];en3[i]=NULL;}}
			for(int i=0;i<n_ene2[config_code[2]];i++)
				if(en2[i]!=NULL&&en2[i]->health)	
				{
					en2[i]->collide(usr,usr->rem );
					if(en2[i]->health==0)			{delete en2[i];en2[i]=NULL;}
					else for(int j=0;j<n_remote;j++)
						if(en2[i]->rem[j]!=NULL)	en2[i]->rem[j]->collide(usr);
				}
			paintgame();break;
	default:break;
	}
}