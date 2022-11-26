#include <stdio.h>
#include <stdlib.h>
#include "acllib.h"
#include "Class.h"
using namespace std;

int Setup()
{
	initWindow("The Sprite game----made by wangke-2021080909008", DEFAULT, DEFAULT,1200,800);
	ACL_Image *imag=new ACL_Image;
	//ACL_Sound pSound;
	// loadSound("music1.mp3",&pSound);
	// playSound(pSound,0);

	return 0;
}

