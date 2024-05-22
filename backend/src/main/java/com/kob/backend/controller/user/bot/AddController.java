package com.kob.backend.controller.user.bot;

import com.kob.backend.service.user.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//Controller
//负责请求转发，接受页面传递过来的参数，根据参数的不同，是调用不同的Service层方法进行操作，操作完成后将返回结果传递给页面。
@CrossOrigin
@RestController
public class AddController {
    @Autowired
    private AddService addService;
    //添加Bot

    @PostMapping("/user/bot/add/")
    public Map<String,String> addBot(@RequestParam Map<String,String>data){
        return addService.add(data);
    }
}
