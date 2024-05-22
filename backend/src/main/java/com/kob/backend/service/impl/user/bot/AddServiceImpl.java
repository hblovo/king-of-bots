package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.impl.utils.UserUtil;
import com.kob.backend.service.user.bot.AddService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class AddServiceImpl implements AddService {
    //添加到数据库
    @Autowired private BotMapper botMapper;
    @Override
    public Map<String, String> addBot(Map<String, String> data) {

        User user = UserUtil.getUser();

        String title = data.get("title");
        String content = data.get("content");
        String description = data.get("description");
        Map<String,String> map = new HashMap<>();
        if(title == null || title.isEmpty()){
            map.put("error_message","标题不能为空");
            return map;
        }
        if(title.length() > 100){
            map.put("error_message","标题长度不能大于100");
            return map;
        }
        if(description == null || description.isEmpty()){
            description = "这个用户很懒，什么都没有留下";
        }
        if(description.length() > 300){
            map.put("error_message","Bot的描述长度不能大于300");
            return map;
        }
        if(content == null || content.isEmpty()){
            map.put("error_message","Bot代码不能为空");
            return map;
        }
        if(content.length() > 10000){
            map.put("error_message","Bot代码长度不能大于10000");
            return map;
        }
        Date now = new Date();
        Bot bot = new Bot(null,user.getId(),title,description,content,1500,now,now);
        botMapper.insert(bot);
        map.put("error_message","success");
        return map;
    }
}
