gcc -c *.c
g++ -c *.cpp 
g++ *.o -lgdi32 -lole32 -loleaut32 -luuid -lwinmm -lmsimg32 -o _Begin_Game.exe
_Begin_Game.exe
del *.o
cmd