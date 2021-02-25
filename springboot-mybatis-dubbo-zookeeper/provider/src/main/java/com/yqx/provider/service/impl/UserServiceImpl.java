package com.yqx.provider.service.impl;

import com.yqx.common.domin.User;
import com.yqx.common.service.UserService;
import com.yqx.provider.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

// 在provider 服务提供者中创建UserServiceImpl接口
// 进行业务逻辑判断
// @Service(version = "1.0.0")       // Dubbo家用Service
// @Component      // Spring家用Component

public class UserServiceImpl implements UserService {

    @Resource
    private RedisTemplate<String,User> redisTemplate;



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




    // ------------------------------------------------------------------
    // ------------------------------------------------------------------
    // --------------------------Redis实现CRUD----------------------------
    // ------------------------------------------------------------------
    // ------------------------------------------------------------------

    // Redis核心:
    // 如果缓存存在 则从Reids中读取信息
    // 如果缓存不存在 则从DB中获取信息 再插入缓存

    /**
     * 测试Redis  根据UserID查询User对象    本地测试成功!
     * @param id
     * @return
     */
    @Override
    public User getUserById(Integer id) {

        // 设置Redis事务关闭
        redisTemplate.setEnableTransactionSupport( false );

        //从缓存中获取城市信息
        String key = "user_"+id;
        ValueOperations<String,User> operations = redisTemplate.opsForValue();


        //缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        User u = operations.get(key);
        System.out.println("是否有缓存："+hasKey+"  缓存中的值是："+u);

        // 数据是否存在于缓存中
        if(hasKey){
            // 如果存在 记录日志 直接返回User对象
            LOGGER.info("UserImpl.updateUser() : 从缓存中获取了user >> " + u.toString());
            return u;
        }

        // 如果不存在 则去数据库中查询数据
        //从数据库中获取user数据
        User user = userMapper.getUserById(id);

        //插入缓存
        operations.set(key, user, 4, TimeUnit.HOURS);
        LOGGER.info("UserImpl.findUserById() :user插入缓存 >> " + user.toString());
        return user;
    }










    @Override
    public int saveUser(User user) {
        return 0;
    }

    @Override
    public int deleteUser(Integer id) {
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }





}
