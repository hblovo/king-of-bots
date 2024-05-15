import { GameObject } from "./GameObject";
import { Wall } from "./Wall";
export class GameMap extends GameObject{
    constructor(ctx,parent){
        super();

        this.ctx = ctx;
        this.parent = parent;

        this.rows = 13;
        this.cols = 13;

        this.inner_walls = 20;
        this.walls = [];
    }
    check_connectivity(g, sx, sy, tx, ty) {
        if (sx == tx && sy == ty) return true;
        //  标记当前点已经走过，不用恢复现场
        g[sx][sy] = true;
        //   上下左右四个方向
        let dx = [-1, 0, 1, 0],dy = [0, 1, 0, -1];
        for (let i = 0; i < 4; i++) {
          let x = sx + dx[i],y = sy + dy[i];
          // 递归检测相邻点。因为四周墙，不用做越界检查
          if (!g[x][y] && this.check_connectivity(g, x, y, tx, ty)) {
            return true;
          }
        }
        return false;
      }
    create_walls(){
        const g = [];
        for(let r = 0;r < this.rows;r ++ ){
            g[r] = [];
            for(let c = 0;c < this.cols;c ++){
                g[r][c] = false;
            }
        }
        //给四周加上障碍
        for(let r = 0;r < this.rows;r++){
            g[r][0]=g[r][this.cols-1] = true;
        }
        for(let c = 0;c < this.cols;c++){
            g[0][c]=g[this.rows-1][c] = true;
        }
        //创建随机障碍物

        for(let i = 0;i<this.inner_walls;i++){
            for(let j = 0;j < 1000;j++){
                let r = parseInt(Math.random() * this.rows);//[0,1）-> [0,rows)
                let c = parseInt(Math.random() * this.cols);
                if(g[r][c] || g[c][r]){
                    continue;
                }
                if(r == this.rows - 2 && c == 1  || r == 1 && c == this.cols - 2) continue;
                g[r][c] = g[c][r] = true;
                break;
            }
        }

        //create a new graph
        const copy_g = JSON.parse(JSON.stringify(g));
        if(!this.check_connectivity(copy_g,this.rows-2,1,1,this.cols-2)){
            return false;
        }
        for(let r = 0;r < this.rows;r++){
            for(let c = 0;c < this.cols;c++){
                if(g[r][c]){
                    this.walls.push(new Wall(r,c,this));
                }
            }
        }
        return true;
    }
    start(){
        for(let i = 1;i<=1000;i++){
            if(this.create_walls()){
                break;
            }
        }   
    }
    update_size(){
        this.L = Math.min(this.parent.clientWidth / this.cols,this.parent.clientHeight / this.rows) ;
        this.ctx.canvas.width = this.L * this.cols ;
        this.ctx.canvas.height = this.L * this.rows ;
    }
    update(){
        this.update_size();
        this.render();
    }
    render(){
        const color_even = '#AAD751';
        const color_odd = '#A2D149';
        for(let r = 0;r < this.rows;r++){
            for(let c = 0;c < this.cols;c++){
                this.ctx.fillStyle = (r + c) % 2 == 0 ? color_even : color_odd;
                this.ctx.fillRect(c * this.L,r * this.L,this.L,this.L);
            }
        }
    }
}