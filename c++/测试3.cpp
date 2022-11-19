#include"实现多边形类.cpp"
class circle:public ShapeFactory
{
public:
	circle(float r):ShapeFactory()
	{
		this->r=r;
	}
	~circle(){};
	float Circumstance(){return 2*3.14*r;};
private:
	float r;//三边长
}
ShapeFactory * ShapeFactory::Create(float r)
{
	
	ShapeFactory *p=new circlee(r);
	return p;
}
