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

    // ------------------------------------------------------------------
    // ------------------------------------------------------------------
    // --------------------------Redis实现CRUD----------------------------
    // ------------------------------------------------------------------
    // ------------------------------------------------------------------

    /**
     * 根据用户Id 查询单个User对象
     * @param id
     * @return
     */
    User getUserById( Integer id );

    /**
     * 根据传入的User对象 添加User信息到数据库
     * @param user
     * @return
     */
    int saveUser( User user );

    /**
     * 根据传入的UserId 删除单个User对象
     * @param id
     * @return
     */
    int deleteUser( Integer id );

    /**
     * 根据传入的User对象 修改User信息
     * @param user
     * @return
     */
    int updateUser( User user );


}
