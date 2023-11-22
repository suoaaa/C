#include <stdio.h>
#include <stdlib.h>
#define true 1
#define false 0

//define 
typedef struct node_type{
	int data;
	struct node_type *next;
}node_type;
typedef struct lstack_type{
	node_type *top;
	int length,max;
}lstack_type;

//初始化
void initStack(lstack_type *stack,int length) {
	stack->top=NULL;
	stack->length=0;
	stack->max=length;
}

//push
void push(lstack_type* stack,node_type* new_node){
	new_node->next=stack->top ;
	stack->top =new_node;
	stack->length ++;
}

//pop
node_type* pop(lstack_type* stack){
	node_type* out;
	out=stack->top ;
	stack->top =stack->top->next;
	stack->length--;
	return out;
}



//打印栈中的元素
void print(lstack_type *stack){
	printf("当前栈中的元素为：");
	node_type* s;
	for (int i=0;i<stack->max;i++){
		s=pop(stack);
		printf("%d",s->data);
	} 
	printf("\n");
}


int main(){
	int length;
	printf("请输入栈的最大容量:");
	scanf("%d",&length);
	lstack_type *stack;
	initStack(stack,length);//初始化链栈0
	
	node_type *new_node;
	int new_data;
	printf("请输入要进栈的元素（输入以0结束）：");
	scanf("%d",&new_data);
    //scanf("%d",&new_node);
	while(new_data!=0){
		new_node=(node_type*)malloc(sizeof(node_type*));
		new_node->data=new_data;
		new_node->next=NULL;
		push(stack,new_node);
		printf("请输入要进栈的元素（输入以0结束）：");
		scanf("%d",&new_data);
	}
	
	printf("进栈后的元素为:");
	//for(int i=0;i<length;i++){
	//	pop(stack);
	//print(stack);
	
	//pop(stack);
	//pop(stack);
	
	//print(stack);
	
	//}	
	print(stack);
}
