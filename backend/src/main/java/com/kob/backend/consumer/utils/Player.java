package com.kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;
    //起始坐标
    private Integer sx;
    private Integer sy;

    private List<Integer> steps;
    private boolean check_tail_increasing(int step){
        if(step <= 10) return true;
        return step % 3 == 1;
    }
    public List<Cell> getCells(){
        List<Cell> res = new ArrayList<>();
        int []dx = {-1,0,1,0};
        int []dy = {0,1,0,-1};
        int x = sx,y = sy;
        int step = 0;
        res.add(new Cell(x,y));
        for(int i = 0;i<steps.size();i++){
            int d = steps.get(i);
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x,y));
            step ++;
            if(!check_tail_increasing(++step)){
                //删掉蛇尾
                res.remove(0);
            }
        }
        return res;
    }

}
