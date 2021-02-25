package com.yqx.provider.mapper;

import com.yqx.common.domin.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// 在provider 服务提供者中创建UserMapper接口
// 建立与数据库的连接

@Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<User> {

    /**
     * 查询所有User信息
     * column为数据库字段名，porperty为实体类属性名 id为是否为主键
     * @return
     */
    /*@Results(id = "userMap",value = {
            @Result(column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "age",property = "age"),
            @Result(column = "sex",property = "sex")
    })
    @Select( "SELECT * FROM user" )*/
    List<User> findUserAll();

}
