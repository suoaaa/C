g++ -c MyGame.cpp


g++ acllib.o MyGame.o -lgdi32 -lole32 -loleaut32 -luuid -lwinmm -lmsimg32 -o mygame.exe

del MyGame.o

mygame.exe
cmd