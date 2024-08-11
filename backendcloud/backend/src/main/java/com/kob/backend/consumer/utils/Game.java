package com.kob.backend.consumer.utils;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.Record;
import lombok.Getter;
import org.springframework.security.core.parameters.P;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread{
    private Integer rows;
    private Integer cols;
    private Integer inner_walls_count;
    @Getter
    private int[][] g;
    final private static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    @Getter
    final private Player playerA,playerB;
    private Integer nextStepA = null;
    private Integer nextStepB = null;
    private ReentrantLock lock = new ReentrantLock();
    private String status = "playing";//or finished
    private String loser = "";//all:平局 A:A输了 B:B输了
    private final static String addBotUrl = "http://127.0.0.1:3002/bot/add/";
    public Game(Integer rows, Integer cols, Integer inner_walls_count, Integer idA, Integer idB, Bot botA, Bot botB){
        this.rows = rows;
        this.cols = cols;
        this.inner_walls_count = inner_walls_count;
        this.g = new int[rows][cols];

        Integer botIdA = -1,botIdB = -1;
        String botCodeA ="",botCodeB = "";
        if(botA != null){
            botIdA = botA.getId();
            botCodeA = botA.getContent();
        }
        if(botB != null){
            botIdB = botB.getId();
            botCodeB = botB.getContent();
        }
        playerA = new Player(idA,botIdA,botCodeA,rows-2,1,new ArrayList<>());
        playerB = new Player(idB,botIdB,botCodeB,1,cols-2,new ArrayList<>());

    }
    public void setNextStepA(Integer nextStepA){
        lock.lock();
        try{
            this.nextStepA = nextStepA;
        }finally {
            lock.unlock();
        }
    }
    public void setNextStepB(Integer nextStepB){
        lock.lock();
        try{
            this.nextStepB = nextStepB;
        }finally {
            lock.unlock();
        }
    }
    private boolean check_connectivity(int sx, int sy, int tx, int ty) {
        if (sx == tx && sy == ty) return true;
        g[sx][sy] = 1;

        for (int i = 0; i < 4; i ++ ) {
            int x = sx + dx[i], y = sy + dy[i];
            if (x >= 0 && x < this.rows && y >= 0 && y < this.cols && g[x][y] == 0) {
                if (check_connectivity(x, y, tx, ty)) {
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }

        g[sx][sy] = 0;
        return false;
    }

    public boolean draw(){
        for(int i = 0;i<this.rows;i++){
            for(int j = 0;j<this.cols;j++){
                g[i][j] = 0;
            }
        }
        for(int r = 0;r < this.rows;r++){
            g[r][0] = g[r][this.cols-1] = 1;
        }
        for(int c = 0;c < this.cols;c++){
            g[0][c] = g[this.rows-1][c] = 1;
        }
        Random random = new Random();
        for (int i = 0; i < this.inner_walls_count / 2; i ++ ) {
            for (int j = 0; j < 1000; j ++ ) {
                int r = random.nextInt(this.rows);
                int c = random.nextInt(this.cols);

                if (g[r][c] == 1 || g[this.rows - 1 - r][this.cols - 1 - c] == 1)
                    continue;
                if (r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2)
                    continue;

                g[r][c] = g[this.rows - 1 - r][this.cols - 1 - c] = 1;
                break;
            }
        }
        return check_connectivity(this.rows - 2, 1, 1, this.cols - 2);
    }
    public void createMap(){
        for(int i = 0;i<1000;i++){
            if(draw())
                break;
        }
    }
    private String getInput(Player player){
        //地图信息#
        //蛇头坐标 my_sx#
        //蛇头坐标 my_sy#
        //我的操作

        //your_sx#
        //your_sy#
        //对方的操作

        Player me,you;
        if(player.getId().equals(playerA.getId())) {
            me = playerA;
            you = playerB;
        }
        else {
            me = playerB;
            you = playerA;
        }

        return getMapString() + "#" +
                me.getSx() + "#" + me.getSy() + "#" +
                "(" + me.getStepsString() + ")" + "#" +
                you.getSx() + "#" + you.getSy() + "#" +
                "(" + you.getStepsString() + ")";

    }
    //如果是AI操作就需要向微服务传代码来执行
    private void sendBotCode(Player player){
        if(player.getBot_id().equals(-1)) return;
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id",player.getId().toString());
        data.add("bot_code",player.getBotCode());
        data.add("input",getInput(player));
        WebSocketServer.restTemplate.postForObject(addBotUrl,data,String.class);
    }
    //等待玩家的下一步操作
    private boolean nextStep(){
        try{
            Thread.sleep(200);
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }
        sendBotCode(playerA);
        sendBotCode(playerB);
        for(int i = 0;i<80;i++){
            try {
                Thread.sleep(100);
                lock.lock();
                try{
                    if(nextStepA != null && nextStepB != null){
                        playerA.getSteps().add(nextStepA);
                        playerB.getSteps().add(nextStepB);
                        return true;
                    }
                }finally {
                    lock.unlock();
                }
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        return false;
    }
    private boolean check_valid(List<Cell> cellsA,List<Cell> cellsB){
        int n = cellsA.size();
        Cell cell = cellsA.get(n-1);
        if(g[cell.x][cell.y] == 1) return false;

        for(int i = 0;i < n - 1;i++){
            if(Objects.equals(cellsA.get(i).x, cell.x) && Objects.equals(cellsA.get(i).y, cell.y)) return false;
        }
        for(int i = 0;i < n - 1;i++){
            if(Objects.equals(cellsB.get(i).x, cell.x) && Objects.equals(cellsB.get(i).y, cell.y)) return false;
        }
        return true;
    }
    private void judge(){//判断两名玩家输入是否合法
        List<Cell> cellsA = playerA.getCells();
        List<Cell> cellsB = playerB.getCells();
        boolean validA = check_valid(cellsA,cellsB);
        boolean validB = check_valid(cellsB,cellsA);
        if(!validA || !validB) {
            status = "finished";
            if(!validA && !validB) {
                loser = "all";
            }
            if(!validA) loser = "A";
            else if(!validB) loser = "B";
        }
    }
    private void sendAllMessage(String message){
        if (WebSocketServer.users.get(playerA.getId()) == null || WebSocketServer.users.get(playerB.getId()) == null) {
            return;
        }
        WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        WebSocketServer.users.get(playerB.getId()).sendMessage(message);
    }
    private void sendToDataBase(){

    }
    private void sendResult(){
        JSONObject resp = new JSONObject();
        resp.put("event","result");
        resp.put("loser",loser);
        sendAllMessage(resp.toJSONString());
        saveRecord();
    }
    private void sendMove(){
        lock.lock();
        try{
            JSONObject resp = new JSONObject();
            resp.put("event","move");
            resp.put("a_direction",nextStepA);
            resp.put("b_direction",nextStepB);
            sendAllMessage(resp.toJSONString());
            nextStepA = nextStepB = null;

        }finally {
            lock.unlock();
        }
    }
    private String getMapString(){
        StringBuilder res = new StringBuilder();
        for(int i=0;i<rows;i++){
            for(int j = 0;j<cols;j++){
                if(g[i][j] == 1) res.append(1);
                else res.append(0);
            }
        }
        return res.toString();
    }
    private void saveRecord(){
        Record record = new Record(
                null,
                playerA.getId(),
                playerA.getSx(),
                playerA.getSy(),
                playerB.getId(),
                playerB.getSx(),
                playerB.getSy(),
                playerA.getStepsString(),
                playerB.getStepsString(),
                getMapString(),
                loser,
                new Date()
        );
        WebSocketServer.recordMapper.insert(record);
    }
    @Override
    public void run() {
        for(int i = 0;i<1000;i++){
            if(nextStep()){
                judge();
                if(status.equals("playing")){
                    sendMove();
                }else{
                    sendResult();
                    break;
                }
            }else{
                status = "finished";
                lock.lock();
                try{
                    if(nextStepA == null && nextStepB == null) {
                        loser = "all";
                    } else if (nextStepA == null) {
                        loser = "A";
                    } else if (nextStepB == null) {
                        loser = "B";
                    }
                }finally {
                    lock.unlock();
                }
                sendResult();
                break;
            }
        }
        super.run();
    }
}
