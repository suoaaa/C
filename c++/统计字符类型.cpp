/*编写函数，使其能统计主调函数通过实参传过来的字符串（非控制台输入），对其中的字母、数字、空格分别计数。
（要求在count函数中通过实参(char *str)传入字符串及输出统计结果）（参考函数原型：void count(char* str)）
输入输出格式要求：
	字母,数字,空格
例如：
str为12asddCSDA  sds23244354
输出为11,10,2*/
#include<iostream>
using namespace std;
void count(char* str)
{
    int latter=0,num=0,space=0;
    for(int i=0;str[i]!='\0';i++)
    {
        if(str[i]==' ')
        {
            space++;
        }
        else{
            if(str[i]>='0'&&str[i]<='9') num++;
        	else
            {
                if((str[i]>='a'&&str[i]<='z')||str[i]>='A'&&str[i]<='Z')
                latter++;
            } 
            
            
            }
    }
    cout<<latter<<','<<num<<','<<space;
}