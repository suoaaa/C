#include<stdio.h>
#include<stdlib.h>
#include<time.h>
//函数内符号的定义
#define ROW 3
#define COL 3

//函数内函数的声明
void clear_board(char board[ROW][COL], int row, int col);	//初始化棋盘
void display(char board[ROW][COL], int row, int col);		//打印数组内容（展示棋盘）
void playermove(char board[ROW][COL], int row, int col);	//玩家移动
void cummove(char board[ROW][COL], int row, int col);		//电脑移动
int check(char board[ROW][COL], int row, int col);			//检测是否结束游戏
void meun();												//打印菜单
void play();												//游戏

int main()
{
	int input = 0;
	do
	{
		meun();
		printf("\n请选择\n");
		scanf("%d", &input);
		switch (input)
		{
		case 1:printf("\n开始游戏\n\n");
			play();
			break;
		case 2: break;
		default:printf("\n请重新选择\n");
			break;

		}
	} while (input != 2);
}
void clear_board(char board[ROW][COL], int row, int col)//初始化棋盘的函数
{
	int i = 0, j = 0;
	for (i = 0; i < row; i++)
	{
		for (j = 0; j < col; j++)
		{
			board[i][j] = ' ';
		}
	}
}
void display(char board[ROW][COL], int row, int col)//打印棋盘
{
	int i = 0, j = 0;
	printf("-");
	for (j = 0; j < col; j++)
	{
		printf("----");
	}
	printf("\n|");
	for (j = 0; j < col; j++)
	{
		printf(" %c |",board[i][j]);
	}
	printf("\n-");
	for (j = 0; j < col; j++)
	{
		printf("----");
	}
	printf("\n");
	for (i = 1; i < row; i++)
	{	
		printf("|");
		for (j = 0; j < col; j++)
		{
			printf(" %c |", board[i][j]);
		}
		printf("\n-");
		for (j = 0; j < col; j++)
		{
			printf("----");
		}
		printf("\n");
	}
	printf("\n");
}
void playermove(char board[ROW][COL],int row,int col)
{
	int x = 0, y = 0, m = 0;
	printf("请输入地址：");
	do
	{
		scanf("%d %d", &x, &y);
		if (x > row || y > col||x<=0||y<=0)
		{
			printf("error\n不存在坐标\n请重新输入\n");
		}
		else
		{
			if (board[x - 1][y - 1] != ' ')
			{
				printf("该点已被占\n请重新输入:");
			}
			else
			{
				m = 1;
				board[x - 1][y - 1] = '#';
			}
		}
	} while (m == 0);
	display(board, row, col);
}
void cummove(char board[ROW][COL], int row, int col)
{
	int x = 0, y = 0;
	int a = 0;
	int b = 1;
	static int c = 1;		
	srand((unsigned int)time(NULL));
	if (c > row * col/2)
	{
		printf("游戏结束\n");
		return;
	}
	else
	{
		c++;
		if (row == 3 && col == 3)
		{
			if (board[1][1] == ' ')
			{
				if (board[0][0] == '#' && board[2][2] == ' ')
				{
					board[2][2] = '*';
					x = 2;
					y = 2;
				}
				else
				{
					if (board[2][2] == '#' && board[0][0] == ' ')
					{
						board[0][0] = '*';
						x = 0;
						y = 0;
					}
					else
					{
						if (board[2][0] == '#' && board[0][2] == ' ')
						{
							board[0][2] = '*';
							x = 0;
							y = 2;
						}
						else
						{
							if (board[0][2] == '#' && board[2][0] == ' ')
							{
								board[2][0] = '*';
								x = 2;
								y = 0;
							}
							else
							{
								board[1][1] = '*';
								x = 1; y = 1;
							}
						}
					}
				}
			}
			else
			{
				do {
					x = rand() % 3;
					y = rand() % 3;
					if (board[x][y] == ' ')
					{
						a = 1;
						board[x][y] = '*';
						
					}
					} while (a == 0);
			}
		}
		else
		{
			do {
				x = rand() % row;
				y = rand() % col;
				if (board[x][y] == ' ')
				{
					a = 1;
					board[x][y] = '*';

				}
			} while (a == 0);
		}
		printf("电脑走的棋子坐标为：%d %d\n", x + 1, y + 1);
		display(board, row, col);
	}
}
int check(char board[ROW][COL], int row, int col)
{
	return 0;
}
void meun()
{
	printf("\n*************************\n");
	printf("******** 1.play *********\n");
	printf("******** 2.exit *********\n");
}
void play()
{
	int row = 3, col = 3;
	char board[ROW][COL];//用来储存棋盘；
	clear_board(board, ROW, COL);
	display(board, row, col);
	while (1)
	{
		//玩家下棋
		playermove(board, row, col);
		cummove(board, row, col);
	}

}
