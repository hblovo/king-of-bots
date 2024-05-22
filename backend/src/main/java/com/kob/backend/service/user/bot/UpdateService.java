package com.kob.backend.service.user.bot;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UpdateService {
    Map<String, String> updateBot(Map<String,String> data);
}
