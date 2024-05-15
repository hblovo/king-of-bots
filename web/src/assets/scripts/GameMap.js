import { GameObject } from "./GameObject";
export class GameMap extends GameObject{
    constructor(ctx,parent){
        super();

        this.ctx = ctx;
        this.parent = parent;
        
        this.L = 0;
    }
    start(){
        this.L = 0;
    }
    update(){
        this.render();
    }
    render(){

    }
}