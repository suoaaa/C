g++ -c *.cpp 
g++ *.o -lgdi32 -lole32 -loleaut32 -luuid -lwinmm -lmsimg32 -o run.exe

run
del Class.o
del MyGame.o
del run.exe
cmd