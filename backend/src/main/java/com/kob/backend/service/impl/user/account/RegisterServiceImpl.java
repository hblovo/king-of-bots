package com.kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Map<String, String> register(String username, String password, String confirmed_password) {
        Map<String,String> map = new HashMap<>();
        if(username == null){
            map.put("error_message","用户名不能为空");
            return map;
        }
        //删除首尾的空白字符
        username = username.trim();
        if (username.isEmpty()) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if (password == null || password.length()==0){
            map.put("error_message", "密码不能为空");
            return map;
        }
        if (password != null && confirmed_password == null) {
            map.put("error_message", "请再次输入密码");
            return map;
        }

        if (username.length() > 100) {
            map.put("error_message", "用户名长度不能大于100");
            return map;
        }

        if (password.length() > 100 || confirmed_password.length() > 100) {
            map.put("error_message", "密码不能大于100");
            return map;
        }

        if (!password.equals(confirmed_password)) {
            map.put("error_message", "两次输入的密码不一致");
            return map;
        }
        //查询用户名是否重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            map.put("error_message", "用户名已存在");
            return map;
        }

        // 添加一个新用户
        String encodedPassword = passwordEncoder.encode(password);
        //输入自己的图片地址
        String photo = "https://cdn.acwing.com/media/user/profile/photo/209432_lg_48a4c0a111.jpg";
        User user = new User(null, username, encodedPassword, photo);
        userMapper.insert(user);

        map.put("error_message", "注册成功");
        return map;
    }
}
