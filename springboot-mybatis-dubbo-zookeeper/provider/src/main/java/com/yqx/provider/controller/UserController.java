package com.yqx.provider.controller;

import com.yqx.common.domin.User;
import com.yqx.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping( "/user" )
@RestController // 返回Json字符串
public class UserController {

    // 注入
    @Autowired
    public UserService userService;

    // 测试方法 测试成功
    @GetMapping( "/text" )
    public User text(){
        User user = userService.findUser();
        return user;
    }

    /**
     * 连接数据库 查询User表中所有的信息
     * 测试成功!
     * @return
     */
    @RequestMapping( "/findUserAll" )
    public List<User> findUserAll(){
        return userService.findUserAll();
    }

    /**
     * 根据参数id查询User信息
     * 主要测试tk包下的mapper是否能够正常使用
     * @return
     */
    @GetMapping( "/findUsers" )
    public List<User> findUsers(){
        List<User> users = userService.findUsers();
        System.out.println( "tk包下的mapper测试通过!" );
        if ( users == null ){
            throw new RuntimeException();
        }
        return users;
    }

    @GetMapping( "/findUserById" )
    public User findUserById(@RequestParam("id")Integer id){
        User user = userService.findUserById(id);
        // 返回User数据
        return user;
    }


}
