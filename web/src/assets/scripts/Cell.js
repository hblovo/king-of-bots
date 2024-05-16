// 新建 Cell.js 存储一堆格子的序列 –> 存储蛇。
export class Cell {
    constructor(r, c) {
        this.r = r;
        this.c = c;
        // 转换为 canvas 的坐标
        this.x = c + 0.5;  
        this.y = r + 0.5;
    }
}