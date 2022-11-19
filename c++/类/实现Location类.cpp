/*完成运算符 +和 - 的重载，
+运算符的重载实现两个Location对象 的对应坐标相加，例如：
Location L1(1,1),L2(2,2);
L1 =L1+L2//L1为（3,3）

-运算符的重载实现两个Location对象的对应坐标相减，例如：
Location L1(1,1),L2(2,2);
L1 =L1-L2//L1为（-1,-1）*/
#include <iostream>
using namespace std;
class Location{
public:
	Location(int xx,int yy){
		x =xx;
		y =yy;
	}
Location &operator +(Location &offset);
Location &operator -(Location &offset);

int getX(){return x;}
int getY(){return y;}
private:
	int x;
	int y;
};
Location & Location::operator+(Location &offset)
{
    Location *a=new Location(offset.x+x,y+offset.y);
    return *a;
}
Location & Location::operator-(Location &offset)
{
    Location *a=new Location(x-offset.x,y-offset.y);
    return *a;
}
