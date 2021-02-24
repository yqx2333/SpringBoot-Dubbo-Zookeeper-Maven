package com.yqx.provider.mapper;

import com.yqx.common.domin.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// 在provider 服务提供者中创建UserMapper接口
// 建立与数据库的连接

@Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<User> {

    @Select( "SELECT * FROM user")
    List<User> findUsers();



}
