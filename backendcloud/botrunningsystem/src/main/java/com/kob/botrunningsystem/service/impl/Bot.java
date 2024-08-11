package com.kob.botrunningsystem.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bot{
    Integer userId;
    String botCode;
    String input;//地图局i面
}