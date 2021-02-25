package com.yqx.provider.service.impl;

import com.yqx.common.domin.User;
import com.yqx.common.service.UserService;
import com.yqx.provider.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

// 在provider 服务提供者中创建UserServiceImpl接口
// 进行业务逻辑判断
// @Service(version = "1.0.0")       // Dubbo家用Service
// @Component      // Spring家用Component

public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    /**
     * 查询User信息 返回伪数据
     * @return
     */
    @Override
    public User findUser() {
        return new User(1,"于清晰",23,"男");
    }

    /**
     * 查询所有User信息
     * @return
     */
    @Override
    public List<User> findUserAll() {
        return userMapper.findUserAll();
    }

    /**
     * 根据UserID查询单个User信息
     * 主要测试TK包下的Mapper是否能够使用
     * @return
     */
    @Override
    public List<User> findUsers() {
        return userMapper.selectAll();
    }

    /**
     * 根据UserID查询User对象
     * @return
     */
    @Override
    public User findUserById( Integer id ) {
        // 使用selectByPrimaryKey时候 必须在实体类中添加@Id注解 表明主键
        User user = userMapper.selectByPrimaryKey( id );
        // 非空判断
        if ( user == null ){
            throw new RuntimeException();
        }
        // 不为空返回
        return user;
    }

}
