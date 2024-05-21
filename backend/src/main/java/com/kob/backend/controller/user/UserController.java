package com.kob.backend.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/user/all/")
    public List<User> getAll() {
        return userMapper.selectList(null);
    }
    @GetMapping("/user/{id}/")
    public List<User> getuser(@PathVariable int id){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("id",id);//le、lt、ge、gt
        queryWrapper.ge("id",1).le("id",2);
        //return userMapper.selectById(id);
        return userMapper.selectList(queryWrapper);
    }
    @GetMapping("user/add/{id}/{username}/{password}/")
    public String addUser(@PathVariable int id,@PathVariable String username,@PathVariable String password)
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(password.length() < 6) return "密码太短";
        password = passwordEncoder.encode(password);
        User user = new User(id,username,password);
        userMapper.insert(user);
        return "Add User successfully";
    }
    @GetMapping("user/delete/{id}/{username}/{password}/")
    public String deleteUser(@PathVariable int id)
    {
        userMapper.deleteById(id);
        return "Delete user successfully";
    }
}
