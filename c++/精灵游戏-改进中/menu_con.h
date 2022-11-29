#ifndef MENU_CON_H
#define MENU_CON_H

#include "acllib.h"
#include "Class.h"
#include "config.h"

void game_menu(ACL_Image *img)         //开始菜单
{
    initWindow("The Menu of Sprite game----made by wangke-2021080909008",0,0,Winwidth,Winhigh);
    //registerKeyboardEvent(keyEvent);
    beginPaint();
    putImageScale(img,0,0,Winwidth,Winhigh);

    setPenColor(RED);    setPenWidth(10);    setBrushColor(WHITE);      //设置主标题框格式
    rectangle(Winwidth/16*5, Winhigh/9, Winwidth/16*11, Winhigh/18*5);	

    setPenColor(BLUE);    setPenWidth(5);    setBrushColor(WHITE);      //设置三个副标题框格式
    rectangle(Winwidth/32*13, Winhigh/18*7, Winwidth/32*19, Winhigh/18*9);
    rectangle(Winwidth/32*13, Winhigh/18*10, Winwidth/32*19, Winhigh/18*12);
    rectangle(Winwidth/32*13, Winhigh/18*13, Winwidth/32*19, Winhigh/18*15);

    setTextSize(Winwidth/16/10*7);    setTextBkColor(YELLOW);           //设置主标题文字格式
    paintText(Winwidth/32*13,Winhigh/6, "精灵游戏");

    setTextSize(Winwidth/16/10*5);    setTextBkColor(GREEN);            //设置三个副标题文字格式
    paintText(Winwidth/32*14,Winhigh/36*15, "开始游戏");
    paintText(Winwidth/32*14,Winhigh/36*21, "更改设置");
    paintText(Winwidth/32*14,Winhigh/36*27, "玩法说明");

    endPaint();
}
void config_change()//更改设置
{}
void config_menu() //设置菜单
{}
void config_Set()  //设置设置
{}
void play_way()     //玩法介绍
{}
#endif