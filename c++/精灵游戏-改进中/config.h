#ifndef CONFIG_H
#define CONFIG_H

#define MUSIC       1       //设置是否开启背景音乐（默认关）
#define Winwidth    1280    //窗口宽（默认1280）
#define Winhigh     720     //窗口高（默认720）
#define n_enemy1    4       //敌人1的最大数量（默认4）
#define n_enemy2    2       //敌人2的最大数量（默认2）
#define n_enemy3    7       //敌人3的最大数量（默认7）

#define V_user      6       //定义玩家移动速度（默认6）
#define V_enemy     5       //定义敌对移动速度（默认5）
#define V_remote    15      //定义远程攻击的移动速度（默认15）
#define pica        65      //角色及敌人贴图高与宽
#define fire_a      30      //玩家发射火球贴图的高与宽
#define arrow_width 48      //敌人2类射出弓箭贴图的宽
#define arrow_high  48      //敌人2类射出弓箭贴图的高
#define n_remote    5       //火球及箭的各自最大在场数量（默认5）
#define CD          30      //设置玩家远程攻击cd为1.5s（0.05s为一个单位时间）
#define CD_arrow    90      //设置每个敌人2远程攻击cd为4.5s（0.05s为一个单位时间）
#define CD_skill    120     //设置角色技能cd为6s（0.05s为一个单位时间）
#define CD_inskill  40      //设置角色技能持续时间2s

#endif