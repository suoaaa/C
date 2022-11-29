gcc -c acllib1.c
g++ -c *.cpp 
g++ *.o -lgdi32 -lole32 -loleaut32 -luuid -lwinmm -lmsimg32 -o _Begin_Game.exe
_Begin_Game.exe
del Class.o
del The Sprite game.o
del acllib1.o
cmd