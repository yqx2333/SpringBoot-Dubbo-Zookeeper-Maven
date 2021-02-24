package com.yqx.common.service;

import com.yqx.common.domin.User;

import java.util.List;

// 在Common工程中创建UserService接口
public interface UserService {

    /**
     * 返回User对象
     * TO  DO 目前是根据ID返回 还是直接用伪数据返回为止
     * @return
     * @Auther 于清晰
     */
    User findUser();

    /**
     * 查询所有User对象并返回
     * @return
     */
    List<User> findUserAll();

    /**
     * 查询所有User信息并返回
     * @return
     */
    List<User> findUsers();

    /**
     * 根据ID查询User对象
     * @return
     */
    User findUserById( Integer id );


}
