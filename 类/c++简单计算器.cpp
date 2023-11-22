/*编写一个计算类，例如CNumber，它必须公有继承CNumberFactory类，并实现CNumberFactory类中的各个方法。
它必须有一个变量保存一个int值，通过SetValue(number)函数将该变量的值设置为number
通过GetValue函数获取该变量的值
通过Add(number)函数将该变量的值与number相加，并将结果保存到该变量中
通过Sub(number)函数将该变量的值与number相减，并将结果保存到该变量中。
必须实现CNumberFactory的Create函数，它返回一个该计算类的实例的地址，它的实现如下：*/
#include<iostream>
using namespace std;
#pragma once
class CNumberFactory
{
public:
	virtual void Add(int number) {};
	virtual void Sub(int number) {};
	virtual int GetValue() {return -1;};
	virtual void SetValue(int number) {};
	CNumberFactory *Create();
};
class CNumber:public CNumberFactory
{
public:
    void Add(int number)    {this->number+=number;};
    void Sub(int number)    {this->number-=number;};
	int GetValue() {return number;};
	void SetValue(int number) {this->number=number;};
private: int number;
};
CNumberFactory *CNumberFactory::Create()
{
	return new CNumber();
}