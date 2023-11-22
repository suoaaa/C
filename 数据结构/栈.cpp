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

//��ʼ��
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



//��ӡջ�е�Ԫ��
void print(lstack_type *stack){
	printf("��ǰջ�е�Ԫ��Ϊ��");
	node_type* s;
	for (int i=0;i<stack->max;i++){
		s=pop(stack);
		printf("%d",s->data);
	} 
	printf("\n");
}


int main(){
	int length;
	printf("������ջ���������:");
	scanf("%d",&length);
	lstack_type *stack;
	initStack(stack,length);//��ʼ����ջ0
	
	node_type *new_node;
	int new_data;
	printf("������Ҫ��ջ��Ԫ�أ�������0��������");
	scanf("%d",&new_data);
    //scanf("%d",&new_node);
	while(new_data!=0){
		new_node=(node_type*)malloc(sizeof(node_type*));
		new_node->data=new_data;
		new_node->next=NULL;
		push(stack,new_node);
		printf("������Ҫ��ջ��Ԫ�أ�������0��������");
		scanf("%d",&new_data);
	}
	
	printf("��ջ���Ԫ��Ϊ:");
	//for(int i=0;i<length;i++){
	//	pop(stack);
	//print(stack);
	
	//pop(stack);
	//pop(stack);
	
	//print(stack);
	
	//}	
	print(stack);
}
