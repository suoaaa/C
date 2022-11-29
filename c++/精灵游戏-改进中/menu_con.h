#ifndef MENU_CON_H
#define MENU_CON_H

#include "acllib.h"
#include "Class.h"
#include "config.h"

void game_menu(ACL_Image *img)         //��ʼ�˵�
{
    initWindow("The Menu of Sprite game----made by wangke-2021080909008",0,0,Winwidth,Winhigh);
    //registerKeyboardEvent(keyEvent);
    beginPaint();
    putImageScale(img,0,0,Winwidth,Winhigh);

    setPenColor(RED);    setPenWidth(10);    setBrushColor(WHITE);      //������������ʽ
    rectangle(Winwidth/16*5, Winhigh/9, Winwidth/16*11, Winhigh/18*5);	

    setPenColor(BLUE);    setPenWidth(5);    setBrushColor(WHITE);      //����������������ʽ
    rectangle(Winwidth/32*13, Winhigh/18*7, Winwidth/32*19, Winhigh/18*9);
    rectangle(Winwidth/32*13, Winhigh/18*10, Winwidth/32*19, Winhigh/18*12);
    rectangle(Winwidth/32*13, Winhigh/18*13, Winwidth/32*19, Winhigh/18*15);

    setTextSize(Winwidth/16/10*7);    setTextBkColor(YELLOW);           //�������������ָ�ʽ
    paintText(Winwidth/32*13,Winhigh/6, "������Ϸ");

    setTextSize(Winwidth/16/10*5);    setTextBkColor(GREEN);            //�����������������ָ�ʽ
    paintText(Winwidth/32*14,Winhigh/36*15, "��ʼ��Ϸ");
    paintText(Winwidth/32*14,Winhigh/36*21, "��������");
    paintText(Winwidth/32*14,Winhigh/36*27, "�淨˵��");

    endPaint();
}
void config_change()//��������
{}
void config_menu() //���ò˵�
{}
void config_Set()  //��������
{}
void play_way()     //�淨����
{}
#endif