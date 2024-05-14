const GAME_OBJECTS = [];

export class GameObject {
    constructor(){
        GAME_OBJECTS.push(this);
        this.has_called_start = false;
        this.timedelta = 0;
    }
    //只执行一次
    start(){

    }
    //每一帧执行一次
    update(){
        
    }
    on_destory(){
        //在调用destroy之前执行
    }
    destroy(){
        this.on_destory();
        for(let i in GAME_OBJECTS){
            const obj = GAME_OBJECTS[i];
            if(obj == this){
                GAME_OBJECTS.splice(i);
                break;
            }
        }
    }
}
let last_timestamp;//上次执行的时刻
const step = timestamp =>{
    for(let obj of GAME_OBJECTS){
        if(!obj.has_called_start){
            obj.start();
            obj.has_called_start = true;
        }
        else {
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }
    last_timestamp = timestamp;
    //在每一帧都执行step函数
    requestAnimationFrame(step);
}
requestAnimationFrame(step)