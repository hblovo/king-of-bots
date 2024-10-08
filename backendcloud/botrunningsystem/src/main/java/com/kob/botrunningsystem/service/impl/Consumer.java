package com.kob.botrunningsystem.service.impl;

import com.kob.botrunningsystem.utils.BotInterface;
import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.awt.image.ImageWatched;

import java.sql.SQLOutput;
import java.util.UUID;

@Component
public class Consumer extends Thread{
    private Bot bot;
    private static RestTemplate restTemplate;
    private final static String receiveBotMoveUrl = "http://127.0.0.1:3000/pk/receive/bot/move/";
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        Consumer.restTemplate = restTemplate;
    }
    public void startTimeout(long timeout,Bot bot){
        this.bot = bot;
        this.start();

        try {
            this.join(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            this.interrupt();
        }

    }
    private String addUid(String code,String uid){//在code中的Bot类名后加uid
        int k = code.indexOf(" implements com.kob.botrunningsystem.utils.BotInterface");
        return code.substring(0,k) + uid + code.substring(k);
    }

    @Override
    public void run() {
        //joor,动态编译代码
        //加uuid让每一个类的类名不一样
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0,8);
        BotInterface botInterface = Reflect.compile(
                "com.kob.botrunningsystem.utils.Bot" + uid,
                addUid(bot.getBotCode(),uid)
        ).create().get();

        Integer direction = botInterface.nextMove(bot.getInput());
        MultiValueMap<String ,String> data = new LinkedMultiValueMap<>();
        data.add("user_id", String.valueOf(bot.getUserId()));
        data.add("direction",direction.toString());
        System.out.println("move-direction: " + bot.getUserId() + " " + direction);
        restTemplate.postForObject(receiveBotMoveUrl,data,String.class);
    }
}
