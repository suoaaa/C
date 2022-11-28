gcc -c acllib.c
g++ -c *.cpp 
g++ *.o -lgdi32 -lole32 -loleaut32 -luuid -lwinmm -lmsimg32 -o Begin_Game.exe
Begin_Game.exe
del Class.o
del The Sprite game.o
del acllib.o
cmd