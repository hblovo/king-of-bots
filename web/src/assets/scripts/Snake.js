import { GameObject } from "./GameObject";
import  {Cell} from "./Cell";
export class Snake extends GameObject{
    constructor(info,gamemap){
        super();
        this.id = info.id;
        this.color = info.color;
        this.gamemap = gamemap;

        this.cells = [new Cell(info.r,info.c)];
        this.speed = 5;//每秒移动5个格子，每一帧移动时间为this.timedelta / 1000
        this.next_cell = null;//下一步的位置
        this.direction = -1;//-1表示无指令，0123表示上右下左
        //蛇的状态有三种，idle表示静止，move表示正在移动，die表示死亡
        this.status = "idle";
        this.dr = [-1,0,1,0];
        this.dc = [0,1,0,-1];
        this.step = 0;//回合数
        this.eps = 1e-2;
    }
    start(){

    }
    set_direction(d){
        this.direction = d;
    }
    next_step(){
        const d = this.direction;
        this.next_cell = new Cell(this.cells[0].r + this.dr[d],this.cells[0].c + this.dc[d]);
        this.direction = -1;
        this.status = "move";
        this.step ++;

        const k = this.cells.length;
        for(let i = k;i > 0;i--){
            this.cells[i] =JSON.parse(JSON.stringify(this.cells[i-1]));//深拷贝
        }
    }
    update_move(){
        //this.cells[0].x += this.speed * this.timedelta / 1000;
        const dx = this.next_cell.x - this.cells[0].x;
        const dy = this.next_cell.y - this.cells[0].y;
        const distance = Math.sqrt(dx * dx + dy * dy);



        if (distance < this.eps) {  // 走到目标点了
            this.cells[0] = this.next_cell;  // 添加一个新蛇头
            this.next_cell = null;
            this.status = "idle";  // 走完了，停下来

        } else {
            const move_distance = this.speed * this.timedelta / 1000;
            this.cells[0].x += move_distance * dx / distance;
            this.cells[0].y += move_distance * dy / distance;
        }
    }
    update(){
        if(this.status === "move")
            this.update_move();
        this.render();
    }
    render(){
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;

        ctx.fillStyle = this.color;
        ctx.beginPath();
        for(const cell of this.cells){
            ctx.beginPath();
            ctx.arc(cell.x * L,cell.y * L,L/2,0,2*Math.PI);
            ctx.fill();
        }
    }
}