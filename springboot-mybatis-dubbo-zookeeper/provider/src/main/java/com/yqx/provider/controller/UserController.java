package com.yqx.provider.controller;

import com.yqx.common.domin.User;
import com.yqx.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 返回Json字符串
public class UserController {

    // 注入
    @Autowired
    public UserService userService;

    // 测试方法 测试成功
    @GetMapping( "/user/text" )
    public User text(){
        User user = userService.findUser();
        return user;
    }


    @RequestMapping( "/user/findUserAll" )
    public List<User> findUserAll(){
        return userService.findUserAll();
    }


}
