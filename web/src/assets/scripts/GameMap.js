import { GameObject } from "./GameObject";
export class GameMap extends GameObject{
    constructor(ctx,parent){
        super();

        this.ctx = ctx;
        this.parent = parent;

        this.rows = 13;
        this.cols = 13;
    }
    start(){
        
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