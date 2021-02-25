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
     * 测试Redis  根据UserID查询User对象
     * 本地测试成功! Redis缓存成功! 远程调用成功!
     * @param id
     * @return
     */
    @Override
    public User getUserById(Integer id) {

        // 设置Redis事务关闭
        redisTemplate.setEnableTransactionSupport( false );

        // 设置key信息
        String key = "user_"+id;
        ValueOperations<String,User> operations = redisTemplate.opsForValue();

        //缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        User u = operations.get(key);
        System.out.println("是否有缓存："+hasKey+"  缓存中的值是："+u);
        if(hasKey){
            User user = operations.get(key);
            LOGGER.info("UserImpl.updateUser() : 从缓存中获取了user >> " + user.toString());
            return user;
        }
        //从数据库中获取user数据
        User user = userMapper.getUserById(id);

        //插入缓存
        operations.set(key, user, 4, TimeUnit.HOURS);
        LOGGER.info("UserImpl.findUserById() :user插入缓存 >> " + user.toString());
        return user;
    }

    /**
     * 根据User对象 保存User信息
     * 添加保存不需要进行Redis缓存 直接添加即可
     * @param user
     * @return
     */
    @Override
    public int saveUser(User user) {

        // 先进行判断 如果传入的User对象为空该怎么办?
        if ( user == null ){
            LOGGER.info( "增加User对象时,传入的User对象为空!" + user );
            throw new RuntimeException();
        }
        // 不为空则进行添加
        int i = userMapper.saveUser(user);
        return i;
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
