package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserUtil;
import com.kob.backend.service.user.bot.RemoveService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveServiceImpl implements RemoveService {
    private final BotMapper botMapper;

    public RemoveServiceImpl(BotMapper botMapper) {
        this.botMapper = botMapper;
    }

    @Override
    public Map<String, String> removeBot(Map<String, String> data) {
        User user = UserUtil.getUser();
        int id = user.getId();
        int bot_id = Integer.parseInt(data.get("bot_id"));
        Bot bot = botMapper.selectById(bot_id);
        Map<String,String> map = new HashMap<>();
        if(bot == null){
            map.put("error_message","Bot不存在或已被删除");
            return map;
        }
        if(!bot.getUserId().equals(user.getId())){
            map.put("error_message","该bot不属于你,无法删除");
            return map;
        }
        botMapper.deleteById(bot_id);
        map.put("error_message","Success");
        return map;
    }
}
