package com.yqx.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.yqx.common.domin.User;
import com.yqx.common.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController // 返回Json字符串
public class UserController {

    // 注入Service
    @Resource
    @Reference(version = "1.0.0")
    private UserService userService;

    /**
     * 测试远程调用 --伪数据
     * @return
     */
    @GetMapping( "/text" )
    public User userText(){
        System.out.println( "进入测试方法" );
        System.out.println( userService.hashCode() + "--------------" );
        return userService.findUser();
    }

    /**
     * 测试远程调用 --连接数据库调用
     * @param id
     * @return
     */
    @GetMapping( "/findUserById" )
    public User findUserById(@RequestParam("id")Integer id){
        User user = userService.findUserById(id);
        // 返回User数据
        return user;
    }

    @GetMapping( "/findUserAll" )
    public List<User> findUserAll(){
        List<User> userAll = userService.findUserAll();

        if (userAll==null){
            throw new RuntimeException();
        }

        return userAll;
    }


    // ------------------------------------------------------------------
    // ------------------------------------------------------------------
    // --------------------------Redis实现CRUD----------------------------
    // ------------------------------------------------------------------
    // ------------------------------------------------------------------

    /**
     * 跨服务测试Redis  根据UserID查询User对象
     * @param id
     * @return
     */
    @GetMapping( "/redis/getUserById" )
    public User getUserById( @RequestParam("id")Integer id ){
        // 根据ID查询User
        User user = userService.getUserById(id);
        if ( user == null ){
            throw new RuntimeException();
        }
        return user;
    }


}
