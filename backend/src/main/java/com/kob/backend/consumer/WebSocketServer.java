package com.kob.backend.consumer;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JwtAuthntication;
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
    private static ConcurrentHashMap<Integer,WebSocketServer> users = new ConcurrentHashMap<>();
    private User user;

    private Session session = null;

    private static UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
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

    private void startMatching(){
        System.out.println("start matching");
        matchpool.add(this.user);
        while(matchpool.size() >= 2){
            Iterator<User> it = matchpool.iterator();
            User a = it.next();
            User b = it.next();
            matchpool.remove(a);
            matchpool.remove(b);
            Game game = new Game(13,17,20);
            game.createMap();
            JSONObject respA = new JSONObject();
            respA.put("event","match-success");
            respA.put("opponent_username",b.getUsername());
            respA.put("opponent_photo",b.getPhoto());
            respA.put("gamemap",game.getG());
            //users: a map from id to WebSocketServer
            users.get(a.getId()).sendMessage(respA.toJSONString());

            JSONObject respB = new JSONObject();
            respB.put("event","match-success");
            respB.put("opponent_username",a.getUsername());
            respB.put("opponent_photo",a.getPhoto());
            respB.put("gamemap",game.getG());
            //users: a map from id to WebSocketServer
            users.get(b.getId()).sendMessage(respB.toJSONString());
        }
    }
    private void stopMatching(){
        System.out.println("stop matching");
        matchpool.remove(this.user);
    }
    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");

        //event:"start-matching","stop-matching"
        if("start-matching".equals(event)) {
            startMatching();
        }else if ("stop-matching".equals(event)){
            stopMatching();
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
