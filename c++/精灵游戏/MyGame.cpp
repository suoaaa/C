#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "acllib.h"


int Setup()
{
	initWindow("Test", DEFAULT, DEFAULT,800, 1200);
	startTimer(0, 50);
	initConsole(); 
	return 0;
}
