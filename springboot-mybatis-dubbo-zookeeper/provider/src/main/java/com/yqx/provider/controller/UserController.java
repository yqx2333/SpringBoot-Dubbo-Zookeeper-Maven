package com.yqx.provider.controller;

import com.yqx.common.domin.User;
import com.yqx.common.service.UserService;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping( "/user" )
@RestController // 返回Json字符串
public class UserController {

    // 注入
    @Autowired
    public UserService userService;


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


    // ------------------------------------------------------------------
    // ------------------------------------------------------------------
    // --------------------------Redis实现CRUD----------------------------
    // ------------------------------------------------------------------
    // ------------------------------------------------------------------

    /**
     * 测试Redis  根据UserID查询User对象
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

    /**
     * 根据传入的User信息 添加User对象到数据库中
     * @param user
     * @return
     */
    @GetMapping( "/redis/saveUser" )
    public Integer saveUser( User user ){
        int i = userService.saveUser(user);
        return i;
    }

    /**
     * 根据传入的User对象 修改User信息
     * 并判断该User对象是否存在于Redis缓存中
     * 如果存在 则将Redis缓存删除 下一次查询的时候先从数据库中查询
     * 如果不存在 则不做任何操作
     * @param user
     * @return
     */
    @RequestMapping( "/redis/updateUser" )
    public Integer updateUser( User user ){
        int updateNum = userService.updateUser(user);
        return updateNum;
    }

    /**
     * 根据传入的ID 删除对应的User对象
     * @param id
     * @return
     */
    @RequestMapping( "/redis/delete" )
    public Integer deleteUser(@RequestParam("id") Integer id ){
        // 进行删除操作
        int delete = userService.deleteUser(id);
        return delete;
    }


}
