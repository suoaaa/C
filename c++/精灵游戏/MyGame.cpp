#include <stdio.h>
#include <stdlib.h>
#include "acllib.h"
#include "Class.h"
using namespace std;
ACL_Image *usr_image=new ACL_Image;
ACL_Image *enemy1_image=new ACL_Image;
ACL_Image *enemy2_image=new ACL_Image;
ACL_Image *enemy3_image=new ACL_Image;
ACL_Image *fire_image=new ACL_Image;
ACL_Image *arrow_image=new ACL_Image;
void initimage();//º”‘ÿÕº∆¨÷∏’Î
int Setup()
{
	initWindow("The Sprite game----made by wangke-2021080909008", DEFAULT, DEFAULT,1200,800);
	initimage();
	ACL_Sound pSound;
	loadSound("_mymusic1.mp3",&pSound);
	playSound(pSound,0);
	return 0;
}
void initimage()
{
	loadImage("_usr.bmp", usr_image);
	loadImage("_enemy1.bmp", enemy1_image);
	loadImage("_enemy2.bmp", enemy2_image);
	loadImage("_enemy3.bmp", enemy3_image);
	loadImage("_fire.bmp", fire_image);
	loadImage("_arrow.bmp", arrow_image);
}