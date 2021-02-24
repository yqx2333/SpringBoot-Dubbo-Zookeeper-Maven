package com.yqx.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yqx.common.domin.User;
import com.yqx.common.service.UserService;
import com.yqx.provider.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

// 在provider 服务提供者中创建UserServiceImpl接口
// 进行业务逻辑判断
@Service        // Dubbo家用Service
@Component      // Spring家用Component
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
     * 根据UserID 查询User对象并返回
     * @return
     */
    @Override
    public User findUserById(Integer id) {
        // 去数据库中查询数据
        User user = userMapper.selectByPrimaryKey(id);
        // 返回User对象
        return user;
    }

    /**
     * 查询所有User对象并返回
     * @return
     */
    @Override
    public List<User> findUserAll() {
        return userMapper.selectAll();
    }
}
