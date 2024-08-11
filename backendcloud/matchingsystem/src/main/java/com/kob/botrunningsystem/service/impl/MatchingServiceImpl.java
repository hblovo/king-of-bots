package com.kob.botrunningsystem.service.impl;

import com.kob.botrunningsystem.service.MatchingService;
import com.kob.botrunningsystem.service.impl.utils.MatchingPool;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {
    public final static MatchingPool matchingPool = new MatchingPool();

    @Override
    public String addPlayer(Integer userId, Integer rating,Integer bot_id) {
        System.out.println("add player: " + userId + " " + rating);
        matchingPool.addPlayer(userId, rating,bot_id);
        return "add player success";
    }

    @Override
    public String removePlayer(Integer userId) {
        System.out.println("remove player: " + userId);
        matchingPool.removePlayer(userId);
        return "remove player success";
    }
}
