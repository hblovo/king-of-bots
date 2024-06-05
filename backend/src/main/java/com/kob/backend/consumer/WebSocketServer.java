package com.kob.backend.consumer;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JwtAuthntication;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@ServerEndpoint("/websocket/{token}")
public class WebSocketServer {
    //线程安全，读多写少
    final private static CopyOnWriteArrayList<User> matchpool = new CopyOnWriteArrayList<>();
    static final public ConcurrentHashMap<Integer,WebSocketServer> users = new ConcurrentHashMap<>();
    private User user;

    private Session session = null;

    private static UserMapper userMapper;
    private Game game = null;
    public static RecordMapper recordMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }
    @Autowired
    public void setRecordMapper(RecordMapper recordMapper){
        WebSocketServer.recordMapper = recordMapper;
    }
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        this.session = session;
        WebSocketServer client1 = new WebSocketServer();
        WebSocketServer client2 = new WebSocketServer();
        Integer userId = JwtAuthntication.getUserId(token);
        this.user = userMapper.selectById(userId);
        if(this.user != null){
            users.put(userId,this);
        }else {
            this.session.close();
        }
        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("Disconnected");
        if(this.user != null){
            users.remove(this.user.getId());
            matchpool.remove(this.user);
        }
    }
    private void startGame(Integer aId,Integer bId){
        User a = userMapper.selectById(aId);
        User b = userMapper.selectById(bId);
        
    }
    private void startMatching(){
        System.out.println("start matching");
        matchpool.add(this.user);
        while(matchpool.size() >= 2){
            Iterator<User> it = matchpool.iterator();
            User a = it.next();
            User b = it.next();
            matchpool.remove(a);
            matchpool.remove(b);
            Game game = new Game(13,17,20,a.getId(),b.getId());
            game.createMap();
            game.start();
            users.get(a.getId()).game = game;
            users.get(b.getId()).game = game;
            JSONObject respGame = new JSONObject();
            respGame.put("a_id",game.getPlayerA().getId());
            respGame.put("a_sx",game.getPlayerA().getSx());
            respGame.put("a_sy",game.getPlayerA().getSy());
            respGame.put("b_id",game.getPlayerB().getId());
            respGame.put("b_sx",game.getPlayerB().getSx());
            respGame.put("b_sy",game.getPlayerB().getSy());
            respGame.put("map",game.getG());
            JSONObject respA = new JSONObject();
            respA.put("event","match-success");
            respA.put("opponent_username",b.getUsername());
            respA.put("opponent_photo",b.getPhoto());
            respA.put("game",respGame);
            //users: a map from id to WebSocketServer
            users.get(a.getId()).sendMessage(respA.toJSONString());

            JSONObject respB = new JSONObject();
            respB.put("event","match-success");
            respB.put("opponent_username",a.getUsername());
            respB.put("opponent_photo",a.getPhoto());
            respB.put("game",respGame);
            //users: a map from id to WebSocketServer
            users.get(b.getId()).sendMessage(respB.toJSONString());
        }
    }
    private void stopMatching(){
        System.out.println("stop matching");
        matchpool.remove(this.user);
    }
    private void move(int direction){
        if(game.getPlayerA().getId().equals(user.getId())){
            game.setNextStepA(direction);
        }else if(game.getPlayerB().getId().equals(user.getId())) {
            game.setNextStepB(direction);
        }
    }
    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
        System.out.println("Received: " + message);
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");

        //event:"start-matching","stop-matching"
        if("start-matching".equals(event)) {
            startMatching();
        }else if ("stop-matching".equals(event)){
            stopMatching();
        }else if("move".equals(event)){
            move(data.getInteger("direction"));
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        // 发送消息给Client
        synchronized (this.session){
            try{
                this.session.getBasicRemote().sendText(message);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
