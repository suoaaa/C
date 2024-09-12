//硬件有 16 个 pin,4 个端口，
//每相邻的4个 pin 的值决定对应端口的模式，即 pin0~3对应 port0,pin4~7对应 port1, pin8~11 对应 port2, pin 12~15 对应 port3。
//已知函数 uint16_tReadRin()可以一次读到 16个 pin 的值，写一段函数通过读取这些pin 的值，得到各个端口的模式。
//要从 16 个 pin 的值中计算每个端口的模式，你可以使用位操作来提取每个端口的模式。假设 ReadRin() 函数返回一个 16 位的无符号整数，其中每 4 位对应一个端口的模式。你可以通过以下代码来实现：
//要从 16 个 pin 的值中计算每个端口的模式，你可以使用位操作来提取每个端口的模式。假设 ReadRin() 函数返回一个 16 位的无符号整数，其中每 4 位对应一个端口的模式。你可以通过以下代码来实现：
#include <stdio.h>
#include <stdint.h>

#define PORT_SIZE 4
#define NUM_PORTS 4

uint16_t ReadRin(); // 假设这个函数已经定义并实现

void get_port_modes(uint8_t port_modes[NUM_PORTS]) {
    uint16_t pin_values = ReadRin();

    // 提取每个端口的模式
    for (int i = 0; i < NUM_PORTS; i++) {
        port_modes[i] = (pin_values >> (i * PORT_SIZE)) & 0x0F;
    }
}

int main() {
    uint8_t port_modes[NUM_PORTS];

    // 获取端口模式
    get_port_modes(port_modes);

    // 输出端口模式
    for (int i = 0; i < NUM_PORTS; i++) {
        printf("Port %d mode: %u\n", i, port_modes[i]);
    }

    return 0;
}
